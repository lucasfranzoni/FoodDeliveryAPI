package br.com.everis.delivery.model.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemSacola {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantidade;
	@ManyToOne
	private ItemCardapio itemCardapio;
	private String observacao;
	@ManyToOne
	private Cliente cliente;

	public ItemSacola() {
	}
	
	public ItemSacola(Integer quantidade, ItemCardapio itemCardapio, String observacao, Cliente cliente) {
		this.quantidade = quantidade;
		this.itemCardapio = itemCardapio;
		this.observacao = observacao;
		this.cliente = cliente;
	}

	public BigDecimal calcularValor() {
		return itemCardapio.getPreco().multiply(BigDecimal.valueOf(quantidade));
	}
	
	public Long getId() {
		return id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public ItemCardapio getItemCardapio() {
		return itemCardapio;
	}

	public String getObservacao() {
		return observacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void atualizar(ItemSacola itemSacola) {
		this.quantidade = itemSacola.getQuantidade();
		this.observacao = itemSacola.getObservacao();
	}

}
