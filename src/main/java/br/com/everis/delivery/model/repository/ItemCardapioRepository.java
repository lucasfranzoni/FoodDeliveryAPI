package br.com.everis.delivery.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.delivery.model.entity.ItemCardapio;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Long>{
	
	Page<ItemCardapio> findByRestauranteId(Long idRestaurante, Pageable paginacao);

}
