package br.com.everis.delivery.model.dto;

import br.com.everis.delivery.model.entity.ItemCardapio;

public class ItemCardapioDto {
	
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;

	public ItemCardapioDto() {
	}

	public ItemCardapioDto(ItemCardapio itemCardapio) {
		id = itemCardapio.getId();
		nome = itemCardapio.getNome();
		descricao = itemCardapio.getDescricao();
		preco = itemCardapio.getPreco().doubleValue();
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

	public Double getPreco() {
		return preco;
	}


}
