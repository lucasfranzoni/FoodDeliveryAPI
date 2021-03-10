package br.com.everis.delivery.model.form;

import javax.validation.constraints.NotNull;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.ItemSacola;

public class ItemSacolaForm {
	@NotNull
	private Integer quantidade;
	@NotNull
	private Long itemCardapioId;
	@NotNull
	private String observacao;
	
	public Long getItemCardapioId() {
		return itemCardapioId;
	}

	public ItemSacola converter(Cliente cliente, ItemCardapio itemCardapio) {
		ItemSacola itemSacola = new ItemSacola(quantidade, itemCardapio, observacao, cliente);
		return itemSacola;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public void setItemCardapioId(Long itemCardapioId) {
		this.itemCardapioId = itemCardapioId;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
}
