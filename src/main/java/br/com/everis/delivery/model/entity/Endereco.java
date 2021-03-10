package br.com.everis.delivery.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cep;
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private Integer numero;
	private String complemento;

	public Endereco() {
	}

	public Endereco(String cep, String estado, String cidade, String bairro, String rua, Integer numero,
			String complemento) {
		this.cep = cep;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public String getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public String getRua() {
		return rua;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Long getId() {
		return id;
	}

	public void atualizar(Endereco atualizacoes) {
		this.cep = atualizacoes.getCep();
		this.estado = atualizacoes.getEstado();
		this.cidade = atualizacoes.getCidade();
		this.bairro = atualizacoes.getBairro();
		this.rua = atualizacoes.getRua();
		this.numero = atualizacoes.getNumero();
		this.complemento = atualizacoes.getComplemento();
	}

}
