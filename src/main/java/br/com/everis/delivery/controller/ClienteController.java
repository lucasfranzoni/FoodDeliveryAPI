package br.com.everis.delivery.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.everis.delivery.model.dto.ClienteDto;
import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.form.ClienteForm;
import br.com.everis.delivery.model.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping
	public Page<ClienteDto> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		Page<Cliente> clientes = clienteService.listarTodos(paginacao);
		return ClienteDto.converterPaginaClientes(clientes);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteForm form) {
		Cliente cliente = form.converter();
		clienteService.cadastrar(cliente);
		ClienteDto clienteDto = new ClienteDto(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> detalhar(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.detalhar(id);
		if(cliente.isPresent()) {
			ClienteDto clienteDto = new ClienteDto(cliente.get());
			return ResponseEntity.ok(clienteDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ClienteDto> atualizar(@RequestBody @Valid ClienteForm form, @PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.detalhar(id);
		if(cliente.isPresent()) {
			Cliente atualizacoes = form.converter();
			clienteService.atualizar(cliente.get(), atualizacoes);
			ClienteDto clienteDto = new ClienteDto(cliente.get());
			return ResponseEntity.ok(clienteDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<ClienteDto> deletar(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.detalhar(id);
		if(cliente.isPresent()) {
			clienteService.deletar(cliente.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
