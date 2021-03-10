package br.com.everis.delivery.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.everis.delivery.model.dto.ItemSacolaDto;
import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.ItemSacola;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.form.ItemSacolaForm;
import br.com.everis.delivery.model.service.CardapioService;
import br.com.everis.delivery.model.service.ClienteService;
import br.com.everis.delivery.model.service.SacolaService;

@RestController
@RequestMapping("/clientes/{idCliente}/sacola")
public class SacolaController {

	@Autowired
	SacolaService sacolaService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	CardapioService cardapioService;

	@GetMapping
	public ResponseEntity<Page<ItemSacolaDto>> listarItens(@PathVariable Long idCliente,
			@PageableDefault(page = 0, size = 10) Pageable paginacao) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		if (cliente.isPresent()) {
			Page<ItemSacola> sacola = sacolaService.listarItens(idCliente, paginacao);
			return ResponseEntity.ok(ItemSacolaDto.converter(sacola));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ItemSacolaDto> adicionarItem(@PathVariable Long idCliente,
			@RequestBody @Valid ItemSacolaForm form) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<ItemCardapio> itemCardapio = cardapioService.detalharItem(form.getItemCardapioId());
		if (cliente.isPresent() && itemCardapio.isPresent()) {
			ItemSacola itemSacola = form.converter(cliente.get(), itemCardapio.get());
			if(sacolaTemItemDeOutroRestaurante(cliente.get(), itemSacola)) {
				return ResponseEntity.badRequest().build();
			} else {
				sacolaService.adicionarItem(cliente.get(), itemSacola);
				ItemSacolaDto itemSacolaDto = new ItemSacolaDto(itemSacola);
				return ResponseEntity.status(HttpStatus.CREATED).body(itemSacolaDto);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{idItem}")
	public ResponseEntity<ItemSacolaDto> detalharItem(@PathVariable Long idCliente, @PathVariable Long idItem) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<ItemSacola> itemSacola = sacolaService.detalharItem(idItem);
		if (isOk(cliente, itemSacola)) {
			ItemSacolaDto itemSacolaDto = new ItemSacolaDto(itemSacola.get());
			return ResponseEntity.ok(itemSacolaDto);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{idItem}")
	@Transactional
	public ResponseEntity<ItemSacolaDto> atualizarItem(@PathVariable Long idCliente, @PathVariable Long idItem,
			@RequestBody @Valid ItemSacolaForm form) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<ItemSacola> itemSacola = sacolaService.detalharItem(idItem);
		if (isOk(cliente, itemSacola)) {
			Optional<ItemCardapio> itemCardapio = cardapioService.detalharItem(form.getItemCardapioId());
			ItemSacola atualizacoes = form.converter(cliente.get(), itemCardapio.get());
			sacolaService.atualizarItem(itemSacola.get(), atualizacoes);
			ItemSacolaDto itemSacolaDto = new ItemSacolaDto(itemSacola.get());
			return ResponseEntity.ok(itemSacolaDto);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{idItem}")
	@Transactional
	public ResponseEntity<ItemSacolaDto> removerItem(@PathVariable Long idCliente, @PathVariable Long idItem) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<ItemSacola> itemSacola = sacolaService.detalharItem(idItem);
		if (isOk(cliente, itemSacola)) {
			sacolaService.removerItem(cliente.get(), itemSacola.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<ItemSacolaDto> esvaziarSacola(@PathVariable Long idCliente) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		if(cliente.isPresent()) {
			sacolaService.envaziar(cliente.get().getSacola());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	private boolean sacolaTemItemDeOutroRestaurante(Cliente cliente, ItemSacola itemSacola) {
		if(cliente.getSacola().isEmpty()) {
			return false;
			}
		Restaurante restaurante = cliente.getSacola().get(0).getItemCardapio().getRestaurante();
		if(restaurante == itemSacola.getItemCardapio().getRestaurante()) {
			return false;
		}	
		return true;
	}

	private boolean isOk(Optional<Cliente> cliente, Optional<ItemSacola> itemSacola) {
		if (cliente.isPresent() 
				&& itemSacola.isPresent() 
				&& itemSacola.get().getCliente() == cliente.get()) {
			return true;
		}
		return false;
	}
}
