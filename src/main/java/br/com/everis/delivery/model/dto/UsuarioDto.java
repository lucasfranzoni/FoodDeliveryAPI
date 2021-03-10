package br.com.everis.delivery.model.dto;

import br.com.everis.delivery.model.entity.Endereco;
import br.com.everis.delivery.model.entity.Usuario;

public class UsuarioDto {
	
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private Endereco endereco;
	
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.telefone = usuario.getTelefone();
		this.endereco = usuario.getEndereco();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	
}
