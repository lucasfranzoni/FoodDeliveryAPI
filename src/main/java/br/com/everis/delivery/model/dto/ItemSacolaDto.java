package br.com.everis.delivery.model.dto;

import org.springframework.data.domain.Page;

import br.com.everis.delivery.model.entity.ItemSacola;

public class ItemSacolaDto {
	
	private Long id;
	private Integer quantidade;
	private String nome;
	private String descricao;
	private Double valor;
	private String observacao;

	public ItemSacolaDto() {
	}
	
	public ItemSacolaDto(ItemSacola itemSacola) {
		id = itemSacola.getId();
		quantidade = itemSacola.getQuantidade();
		nome = itemSacola.getItemCardapio().getNome();
		descricao = itemSacola.getItemCardapio().getDescricao();
		valor = itemSacola.calcularValor().doubleValue();
		observacao = itemSacola.getObservacao();
	}

	public static Page<ItemSacolaDto> converter(Page<ItemSacola> sacola) {
		return sacola.map(ItemSacolaDto::new);
	}

	public Long getId() {
		return id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getValor() {
		return valor;
	}

	public String getObservacao() {
		return observacao;
	}
}
