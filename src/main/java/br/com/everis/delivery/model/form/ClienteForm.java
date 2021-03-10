package br.com.everis.delivery.model.form;

import javax.validation.constraints.NotBlank;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.Endereco;

public class ClienteForm extends UsuarioForm{
	@NotBlank
	private String cpf;

	public Cliente converter( ) {
		Endereco endereco = super.getEndereco();
		Cliente cliente = new Cliente(super.getNome(), super.getEmail(), super.getSenha(), super.getTelefone(), endereco, cpf);
		return cliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
