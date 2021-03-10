package br.com.everis.delivery.model.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Cliente cliente;
	@ManyToOne
	private Restaurante restaurante;
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;
	private BigDecimal valorTotal;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	private FormaDePagamento formaDePagamento;

	public Pedido() {
	}

	public Pedido(Cliente cliente, Restaurante restaurante, List<ItemPedido> itens, BigDecimal valorTotal,
			FormaDePagamento formaDePagamento) {
		this.cliente = cliente;
		this.restaurante = restaurante;
		this.itens = itens;
		this.valorTotal = valorTotal;
		this.status = Status.SENDO_PREPARADO;
		this.formaDePagamento = formaDePagamento;
	}

	public void deletarRestaurante() {
		this.restaurante = null;
	}

	public void deletarCliente() {
		this.cliente = null;
	}

	public boolean avancarStatus() {
		if (status == Status.CANCELADO || status == Status.FINALIZADO) {
			return false;
		} else {
			status = status.proximo();
			return true;
		}
	}

	public boolean cancelar() {
		if (status == Status.CANCELADO || status == Status.FINALIZADO) {
			return false;
		} else {
			status = Status.CANCELADO;
			return true;
		}
	}
	
	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public Status getStatus() {
		return status;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}
}
