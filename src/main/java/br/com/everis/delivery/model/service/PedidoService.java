package br.com.everis.delivery.model.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.FormaDePagamento;
import br.com.everis.delivery.model.entity.ItemPedido;
import br.com.everis.delivery.model.entity.ItemSacola;
import br.com.everis.delivery.model.entity.Pedido;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.repository.ItemPedidoRepository;
import br.com.everis.delivery.model.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	@Autowired
	SacolaService sacolaService;

	public void deletarPorCliente(Cliente cliente) {
		Page<Pedido> pedidos = pedidoRepository.findByClienteId(cliente.getId(), null);
		for (Pedido pedido : pedidos) {
			if (pedido.getRestaurante() == null) {
				itemPedidoRepository.deleteAll(pedido.getItens());
				pedidoRepository.delete(pedido);
			} else {
				pedido.deletarCliente();
			}
		}
	}

	public void deletarPorRestaurante(Restaurante restaurante) {
		Page<Pedido> pedidos = pedidoRepository.findByRestauranteId(restaurante.getId(), null);
		for(Pedido pedido : pedidos) {
			if(pedido.getCliente() == null) {
				itemPedidoRepository.deleteAll(pedido.getItens());
				pedidoRepository.delete(pedido);
			} else {
				pedido.deletarRestaurante();
			}
		}
	}

	public Page<Pedido> listarTodos(Pageable paginacao) {
		return pedidoRepository.findAll(paginacao);
	}

	public Page<Pedido> listarPorCliente(Long idCliente, Pageable paginacao) {
		return pedidoRepository.findByClienteId(idCliente, paginacao);
	}

	public Page<Pedido> listarPorRestaurante(Long idRestaurante, Pageable paginacao) {
		return pedidoRepository.findByRestauranteId(idRestaurante, paginacao);
	}

	public Page<Pedido> listarPorClienteERestaurante(Long idCliente, Long idRestaurante, Pageable paginacao) {
		return pedidoRepository.findByClienteIdAndRestauranteId(idCliente, idRestaurante, paginacao);
	}

	public Optional<Pedido> detalhar(Long idPedido) {
		return pedidoRepository.findById(idPedido);
	}
	
	public Pedido realizarPedido(Cliente cliente, FormaDePagamento formaDePagamento) {
		Restaurante restaurante = cliente.getSacola().get(0).getItemCardapio().getRestaurante();
		List<ItemPedido> pedidos = new ArrayList<>();
		BigDecimal valorTotal = BigDecimal.valueOf(0);
		List<ItemSacola> sacola = cliente.getSacola();
		for (ItemSacola itemSacola : sacola) {
			ItemPedido itemPedido = new ItemPedido(itemSacola);
			pedidos.add(itemPedido);
			valorTotal = valorTotal.add(itemPedido.getPreco());
			sacolaService.removerItem(cliente, itemSacola);
			itemPedidoRepository.save(itemPedido);
		}
		sacola.removeAll(sacola);
		Pedido pedido = new Pedido(cliente, restaurante, pedidos, valorTotal, formaDePagamento);
		for (ItemPedido itemPedido : pedidos) {
			itemPedido.atribuirAoPedido(pedido);
		}
		pedidoRepository.save(pedido);
		return pedido;
	}
	
		public boolean avancarStatus(Pedido pedido) {
			return pedido.avancarStatus();
		}

		public boolean cancelar(Pedido pedido) {
			return pedido.cancelar();
		}
	
}
