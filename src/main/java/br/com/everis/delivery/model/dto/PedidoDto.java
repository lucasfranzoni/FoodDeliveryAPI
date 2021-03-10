package br.com.everis.delivery.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.FormaDePagamento;
import br.com.everis.delivery.model.entity.Pedido;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.entity.Status;

public class PedidoDto {
	
	private Long id;
	private ClienteDto clienteDto;
	private RestauranteDto restauranteDto;
	private List<ItemPedidoDto> itensDto;
	private Double valorTotal;
	private Status status;
	private FormaDePagamento formaDePagamento;

	public PedidoDto(Pedido pedido) {
		id = pedido.getId();
		clienteDto = criarClienteDto(pedido.getCliente());
		restauranteDto = criarRestauranteDto(pedido.getRestaurante());
		itensDto = pedido.getItens().stream().map(ItemPedidoDto::new).collect(Collectors.toList());
		valorTotal = pedido.getValorTotal().doubleValue();
		status = pedido.getStatus();
		formaDePagamento = pedido.getFormaDePagamento();
	}
	
	private ClienteDto criarClienteDto(Cliente cliente) {
		if(cliente == null) {
			return null;
		}
		return new ClienteDto(cliente);
	}
	
	private RestauranteDto criarRestauranteDto(Restaurante restaurante) {
		if(restaurante == null) {
			return null;
		}
		return new RestauranteDto(restaurante);
	}
	
	public static Page<PedidoDto> converter(Page<Pedido> pedidos) {
		return pedidos.map(PedidoDto::new);
	}

	public Long getId() {
		return id;
	}

	public ClienteDto getCliente() {
		return clienteDto;
	}

	public RestauranteDto getRestaurante() {
		return restauranteDto;
	}

	public List<ItemPedidoDto> getItens() {
		return itensDto;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public Status getStatus() {
		return status;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

}
