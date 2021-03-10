package br.com.everis.delivery.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.everis.delivery.model.entity.Endereco;
import br.com.everis.delivery.model.repository.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	public void cadastrar(Endereco endereco) {
		enderecoRepository.save(endereco);
	}

	public void deletar(Endereco endereco) {
		enderecoRepository.deleteById(endereco.getId());
	}

}
