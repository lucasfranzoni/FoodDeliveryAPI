package br.com.everis.delivery.model.form;

import javax.validation.constraints.NotBlank;

import br.com.everis.delivery.model.entity.Endereco;
import br.com.everis.delivery.model.entity.Restaurante;

public class RestauranteForm extends UsuarioForm{
	@NotBlank
	private String cnpj;

	public Restaurante converter( ) {
		Endereco endereco = super.getEndereco();
		Restaurante restaurante = new Restaurante(super.getNome(), super.getEmail(), super.getSenha(), super.getTelefone(), endereco, cnpj);
		return restaurante;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
