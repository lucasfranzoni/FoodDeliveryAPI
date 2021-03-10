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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.everis.delivery.model.dto.PedidoDto;
import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.Pedido;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.form.FormaDePagamentoForm;
import br.com.everis.delivery.model.service.ClienteService;
import br.com.everis.delivery.model.service.PedidoService;
import br.com.everis.delivery.model.service.RestauranteService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	RestauranteService restauranteService;

	@GetMapping
	public Page<PedidoDto> listar(@PageableDefault(page = 0, size = 10) Pageable paginacao) {
		Page<Pedido> pedidos = pedidoService.listarTodos(paginacao);
		return PedidoDto.converter(pedidos);
	}

	@GetMapping("/clientes/{idCliente}")
	public ResponseEntity<Page<PedidoDto>> listarPorCliente(@PageableDefault(page = 0, size = 10) Pageable paginacao,
			@PathVariable Long idCliente) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		if (cliente.isPresent()) {
			Page<Pedido> pedidos = pedidoService.listarPorCliente(idCliente, paginacao);
			return ResponseEntity.ok(PedidoDto.converter(pedidos));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/restaurantes/{idRestaurante}")
	public ResponseEntity<Page<PedidoDto>> listarPorRestaurante(
			@PageableDefault(page = 0, size = 10) Pageable paginacao, @PathVariable Long idRestaurante) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		if (restaurante.isPresent()) {
			Page<Pedido> pedidos = pedidoService.listarPorRestaurante(idRestaurante, paginacao);
			return ResponseEntity.ok(PedidoDto.converter(pedidos));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/clientes/{idCliente}/restaurantes/{idRestaurante}")
	public ResponseEntity<Page<PedidoDto>> listarPorClienteERestaurante(
			@PageableDefault(page = 0, size = 10) Pageable paginacao, @PathVariable Long idCliente,
			@PathVariable Long idRestaurante) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		if (cliente.isPresent() && restaurante.isPresent()) {
			Page<Pedido> pedidos = pedidoService.listarPorClienteERestaurante(idCliente, idRestaurante, paginacao);
			return ResponseEntity.ok(PedidoDto.converter(pedidos));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/clientes/{idCliente}/{idPedido}")
	public ResponseEntity<PedidoDto> detalharPedido(@PathVariable Long idCliente, @PathVariable Long idPedido) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<Pedido> pedido = pedidoService.detalhar(idPedido);
		if (isOk(cliente, pedido)) {
			PedidoDto pedidoDto = new PedidoDto(pedido.get());
			return ResponseEntity.ok(pedidoDto);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/clientes/{idCliente}")
	@Transactional
	public ResponseEntity<PedidoDto> realizarPedido(@PathVariable Long idCliente,
			@RequestBody @Valid FormaDePagamentoForm form) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		if (cliente.isPresent() && !cliente.get().getSacola().isEmpty()) {
			Pedido pedido = pedidoService.realizarPedido(cliente.get(), form.getFormaDePagamento());
			PedidoDto pedidoDto = new PedidoDto(pedido);
			return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDto);
		}
		return ResponseEntity.notFound().build();

	}

	@PutMapping("/clientes/{idCliente}/{idPedido}/avancar")
	@Transactional
	public ResponseEntity<PedidoDto> avancarStatus(@PathVariable Long idCliente, @PathVariable Long idPedido) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<Pedido> pedido = pedidoService.detalhar(idPedido);
		if (isOk(cliente, pedido)) {
			boolean podeAlterarStatus = pedidoService.avancarStatus(pedido.get());
			if(podeAlterarStatus) {
				PedidoDto pedidoDto = new PedidoDto(pedido.get());
				return ResponseEntity.ok(pedidoDto);
			}
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/clientes/{idCliente}/{idPedido}/cancelar")
	@Transactional
	public ResponseEntity<PedidoDto> cancelar(@PathVariable Long idCliente, @PathVariable Long idPedido) {
		Optional<Cliente> cliente = clienteService.detalhar(idCliente);
		Optional<Pedido> pedido = pedidoService.detalhar(idPedido);
		if (isOk(cliente, pedido)) {
			boolean podeCancelar = pedidoService.cancelar(pedido.get());
			if(podeCancelar) {
				PedidoDto pedidoDto = new PedidoDto(pedido.get());
				return ResponseEntity.ok(pedidoDto);
			}
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.notFound().build();
	}

	private boolean isOk(Optional<Cliente> cliente, Optional<Pedido> pedido) {
		if (cliente.isPresent() 
				&& pedido.isPresent() 
				&& pedido.get().getCliente() == cliente.get()) {
			return true;
		}
		return false;
	}
}
