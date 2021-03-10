package br.com.everis.delivery.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.delivery.model.entity.ItemSacola;

@Repository
public interface ItemSacolaRepository extends JpaRepository<ItemSacola, Long>{
	
	Page<ItemSacola> findByClienteId(Long idCliente, Pageable paginacao);

	List<ItemSacola> findByItemCardapioId (Long idItemCardapio);

}
