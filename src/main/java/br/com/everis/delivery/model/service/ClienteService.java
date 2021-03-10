package br.com.everis.delivery.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	EnderecoService enderecoService;
	@Autowired
	SacolaService itemSacolaService;
	@Autowired
	PedidoService pedidoService;

	public Page<Cliente> listarTodos(Pageable paginacao) {
		return clienteRepository.findAll(paginacao);
	}

	public void cadastrar(Cliente cliente) {
		enderecoService.cadastrar(cliente.getEndereco());
		clienteRepository.save(cliente);
	}

	public Optional<Cliente> detalhar(Long id) {
		return clienteRepository.findById(id);
	}

	public void atualizar(Cliente cliente, Cliente atualizacoes) {
		cliente.atualizar(atualizacoes);
	}

	public void deletar(Cliente cliente) {
		enderecoService.deletar(cliente.getEndereco());
		itemSacolaService.envaziar(cliente.getSacola());
		pedidoService.deletarPorCliente(cliente);
		clienteRepository.deleteById(cliente.getId());
	}

}
