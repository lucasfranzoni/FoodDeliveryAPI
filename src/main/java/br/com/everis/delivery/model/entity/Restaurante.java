package br.com.everis.delivery.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Restaurante extends Usuario{
	
	private String cnpj;
	@OneToMany(mappedBy = "restaurante")
	private List<ItemCardapio> cardapio = new ArrayList<>();
	
	public Restaurante() {
	}
	
	public Restaurante(String nome, String email, String senha, String telefone, Endereco endereco, String cnpj) {
		super(nome, email, senha, telefone, endereco);
		this.cnpj = cnpj;
	}

	public Long getId() {
		return super.getId();
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void adicionarItemAoCardapio(ItemCardapio itemCardapio) {
		cardapio.add(itemCardapio);
	}
	
	public void removerItemDoCardapio(ItemCardapio itemCardapio) {
		cardapio.remove(itemCardapio);
	}

	public List<ItemCardapio> getCardapio() {
		return cardapio;
	}

	public void atualizar(Restaurante atualizacoes) {
		super.setNome(atualizacoes.getNome());
		super.setEmail(atualizacoes.getEmail());
		super.setSenha(atualizacoes.getSenha());
		super.setTelefone(atualizacoes.getTelefone());
		super.getEndereco().atualizar(atualizacoes.getEndereco());
		this.cnpj = atualizacoes.getCnpj();
		
	}
	
}
