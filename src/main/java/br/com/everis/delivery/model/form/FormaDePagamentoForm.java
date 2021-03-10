package br.com.everis.delivery.model.form;

import javax.validation.constraints.NotNull;

import br.com.everis.delivery.model.entity.FormaDePagamento;

public class FormaDePagamentoForm {

	@NotNull
	FormaDePagamento formaDePagamento;

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}
}
