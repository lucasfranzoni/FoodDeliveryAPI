package br.com.everis.delivery.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	RestauranteRepository restauranteRepository;
	@Autowired
	EnderecoService enderecoService;
	@Autowired
	CardapioService itemCardapioService;
	@Autowired
	PedidoService pedidoService;

	public Page<Restaurante> listarTodos(Pageable paginacao) {
		Page<Restaurante> restaurantes = restauranteRepository.findAll(paginacao);
		return restaurantes;
	}

	public void cadastrar(Restaurante restaurante) {
		enderecoService.cadastrar(restaurante.getEndereco());
		restauranteRepository.save(restaurante);
	}

	public Optional<Restaurante> detalhar(Long id) {
		return restauranteRepository.findById(id);
	}

	public void atualizar(Restaurante restaurante, Restaurante atualizacoes) {
		restaurante.atualizar(atualizacoes);
	}

	public void deletar(Restaurante restaurante) {
		enderecoService.deletar(restaurante.getEndereco());
		pedidoService.deletarPorRestaurante(restaurante);
		itemCardapioService.deletarCardapio(restaurante.getCardapio());
		restauranteRepository.deleteById(restaurante.getId());
	}
}
