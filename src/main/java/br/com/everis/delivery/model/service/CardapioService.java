package br.com.everis.delivery.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.repository.ItemCardapioRepository;

@Service
public class CardapioService {
	
	@Autowired
	ItemCardapioRepository itemCardapioRepository;
	@Autowired
	SacolaService sacolaService;

	public void deletarCardapio(List<ItemCardapio> cardapio) {
		sacolaService.removerItensDeCardapioDeletado(cardapio);
		itemCardapioRepository.deleteAll(cardapio);
	}

	public Page<ItemCardapio> listarItens(Long idRestaurante, Pageable paginacao) {
		Page<ItemCardapio> cardapio = itemCardapioRepository.findByRestauranteId(idRestaurante, paginacao);
		return cardapio;
	}

	public void adicionarItem(Restaurante restaurante, ItemCardapio itemCardapio) {
		itemCardapioRepository.save(itemCardapio);
		restaurante.adicionarItemAoCardapio(itemCardapio);
	}

	public Optional<ItemCardapio> detalharItem(Long idItem) {
		return itemCardapioRepository.findById(idItem);
		
	}

	public void atualizarItem(ItemCardapio itemCardapio, ItemCardapio atualizacoes) {
		itemCardapio.atualizar(atualizacoes);
	}

	public void removerItem(Restaurante restaurante, ItemCardapio itemCardapio) {
		restaurante.removerItemDoCardapio(itemCardapio);
		sacolaService.removerItensReferenteItemRemovidoDoCardapio(itemCardapio.getId());
		itemCardapioRepository.deleteById(itemCardapio.getId());
	}
}
