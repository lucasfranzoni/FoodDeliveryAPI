package br.com.everis.delivery.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.ItemSacola;
import br.com.everis.delivery.model.repository.ItemSacolaRepository;

@Service
public class SacolaService {
	
	@Autowired
	ItemSacolaRepository itemSacolaRepository;

	public void envaziar(List<ItemSacola> sacola) {
		itemSacolaRepository.deleteAll(sacola);
		sacola.removeAll(sacola);
	}

	public void removerItensDeCardapioDeletado(List<ItemCardapio> cardapio) {
		for(ItemCardapio itemCardapio : cardapio) {
			List<ItemSacola> itensNaSacola = itemSacolaRepository.findByItemCardapioId(itemCardapio.getId());
			itemSacolaRepository.deleteAll(itensNaSacola);
		}
	}
	
	public void removerItensReferenteItemRemovidoDoCardapio(Long idItemCardapio) {
		List<ItemSacola> itensNaSacola = itemSacolaRepository.findByItemCardapioId(idItemCardapio);
		itemSacolaRepository.deleteAll(itensNaSacola);
	}

	public Page<ItemSacola> listarItens(Long idCliente, Pageable paginacao) {
		return itemSacolaRepository.findByClienteId(idCliente, paginacao);
		
	}

	public void adicionarItem(Cliente cliente, ItemSacola itemSacola) {
		itemSacolaRepository.save(itemSacola);
		cliente.adicionarItemASacola(itemSacola);
	}

	public Optional<ItemSacola> detalharItem(Long idItem) {
		return itemSacolaRepository.findById(idItem);
	}

	public void atualizarItem(ItemSacola itemSacola, ItemSacola atualizacoes) {
		itemSacola.atualizar(atualizacoes);
	}

	public void removerItem(Cliente cliente, ItemSacola itemSacola) {
		cliente.removerItemDaSacola(itemSacola);
		itemSacolaRepository.deleteById(itemSacola.getId());
	}
}
