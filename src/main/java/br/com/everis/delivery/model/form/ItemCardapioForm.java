package br.com.everis.delivery.model.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.Restaurante;

public class ItemCardapioForm {

	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;
	@NotNull
	private Double preco;

	public String getNome() {
		return nome;
	}
	
	public ItemCardapio converter(Restaurante restaurante) {
		ItemCardapio itemCardapio = new ItemCardapio(nome, descricao, BigDecimal.valueOf(preco), restaurante);
		return itemCardapio;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
