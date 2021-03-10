package br.com.everis.delivery.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.delivery.model.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	Page<Pedido> findByClienteId(Long idCliente, Pageable paginacao);
	
	Page<Pedido> findByRestauranteId(Long idRestaurante, Pageable paginacao);

	Page<Pedido> findByClienteIdAndRestauranteId(Long idCliente, Long idRestaurante, Pageable paginacao);
}
