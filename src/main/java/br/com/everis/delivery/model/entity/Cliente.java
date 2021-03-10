package br.com.everis.delivery.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Usuario {

	private String cpf;
	@OneToMany(mappedBy = "cliente")
	private List<ItemSacola> sacola = new ArrayList<>();

	public Cliente() {
	}

	public Cliente(String nome, String email, String senha, String telefone, Endereco endereco, String cpf) {
		super(nome, email, senha, telefone, endereco);
		this.cpf = cpf;
	}
	
	public void atualizar(Cliente atualizacoes) {
		super.setNome(atualizacoes.getNome());
		super.setEmail(atualizacoes.getEmail());
		super.setSenha(atualizacoes.getSenha());
		super.setTelefone(atualizacoes.getTelefone());
		super.getEndereco().atualizar(atualizacoes.getEndereco());
		this.cpf = atualizacoes.getCpf();
	}

	public String getNome() {
		return super.getNome();
	}

	public String getEmail() {
		return super.getEmail();
	}

	public String getTelefone() {
		return super.getTelefone();
	}

	public Endereco getEndereco() {
		return super.getEndereco();
	}

	public String getCpf() {
		return cpf;
	}

	public String getSenha() {
		return super.getSenha();
	}

	public List<ItemSacola> getSacola() {
		return sacola;
	}

	public void adicionarItemASacola(ItemSacola itemSacola) {
		sacola.add(itemSacola);
	}

	public void removerItemDaSacola(ItemSacola itemSacola) {
		sacola.remove(itemSacola);
	}
}
