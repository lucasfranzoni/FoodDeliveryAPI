package br.com.everis.delivery.model.dto;

import br.com.everis.delivery.model.entity.ItemPedido;

public class ItemPedidoDto {

	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private Double preco;
	private String observacao;

	public ItemPedidoDto(ItemPedido itemPedido) {
		this.id = itemPedido.getId();
		this.nome = itemPedido.getNome();
		this.descricao = itemPedido.getDescricao();
		this.quantidade = itemPedido.getQuantidade();
		this.preco = itemPedido.getPreco().doubleValue();
		this.observacao = itemPedido.getObservacao();
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

	public Double getPreco() {
		return preco;
	}

	public String getObservacao() {
		return observacao;
	}
}
