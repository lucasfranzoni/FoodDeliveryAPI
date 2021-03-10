package br.com.everis.delivery.model.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private BigDecimal valor;
	private String observacao;
	@ManyToOne
	private Pedido pedido;

	public ItemPedido() {
	}
	
	public ItemPedido(ItemSacola itemSacola) {
		this.nome = itemSacola.getItemCardapio().getNome();
		this.descricao = itemSacola.getItemCardapio().getDescricao();
		this.quantidade = itemSacola.getQuantidade();
		this.valor = itemSacola.calcularValor();
		this.observacao = itemSacola.getObservacao();
	}

	public void atribuirAoPedido(Pedido pedido) {
		if(this.pedido == null) {
			this.pedido = pedido;
		}
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public BigDecimal getPreco() {
		return valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public Pedido getPedido() {
		return pedido;
	}

}
