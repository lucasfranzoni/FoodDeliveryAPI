package br.com.everis.delivery.model.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemCardapio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	@ManyToOne
	private Restaurante restaurante;

	public ItemCardapio() {
	}

	public ItemCardapio(String nome, String descricao, BigDecimal preco, Restaurante restaurante) {
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.restaurante = restaurante;
	}

	
	public Long getId() {
		return id;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void atualizar(ItemCardapio itemCardapio) {
		this.nome = itemCardapio.getNome();
		this.descricao = itemCardapio.getDescricao();
		this.preco = itemCardapio.getPreco();
	}
}
