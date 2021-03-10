package br.com.everis.delivery.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.everis.delivery.model.dto.ItemCardapioDto;
import br.com.everis.delivery.model.dto.RestauranteDto;
import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.form.ItemCardapioForm;
import br.com.everis.delivery.model.service.CardapioService;
import br.com.everis.delivery.model.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{idRestaurante}/cardapio")
public class CardapioController {
	
	@Autowired
	CardapioService cardapioService;
	@Autowired
	RestauranteService restauranteService;

	@GetMapping
	public ResponseEntity<Page<ItemCardapioDto>> listarItens(@PathVariable Long idRestaurante,
			@PageableDefault(page = 0, size = 10, sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		if (restaurante.isPresent()) {
			Page<ItemCardapio> cardapio = cardapioService.listarItens(idRestaurante, paginacao);
			return ResponseEntity.ok(RestauranteDto.converterPaginaCardapio(cardapio));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ItemCardapioDto> adicionarItem(@PathVariable Long idRestaurante,
			@RequestBody @Valid ItemCardapioForm form) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		if (restaurante.isPresent()) {
			ItemCardapio itemCardapio = form.converter(restaurante.get());
			cardapioService.adicionarItem(restaurante.get(), itemCardapio);
			ItemCardapioDto itemCardapioDto = new ItemCardapioDto(itemCardapio);
			return ResponseEntity.status(HttpStatus.CREATED).body(itemCardapioDto);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{idItem}")
	public ResponseEntity<ItemCardapioDto> detalharItem(@PathVariable Long idRestaurante, @PathVariable Long idItem) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		Optional<ItemCardapio> itemCardapio = cardapioService.detalharItem(idItem);
		if (isOk(restaurante, itemCardapio)) {
			ItemCardapioDto itemCardapioDto = new ItemCardapioDto(itemCardapio.get());
			return ResponseEntity.ok(itemCardapioDto);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{idItem}")
	@Transactional
	public ResponseEntity<ItemCardapioDto> atualizarItem(
			@PathVariable Long idRestaurante, @PathVariable Long idItem, @RequestBody @Valid ItemCardapioForm form) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		Optional<ItemCardapio> itemCardapio = cardapioService.detalharItem(idItem);
		if (isOk(restaurante, itemCardapio)) {
			ItemCardapio atualizacoes = form.converter(restaurante.get());
			cardapioService.atualizarItem(itemCardapio.get(), atualizacoes);
			ItemCardapioDto itemCardapioDto = new ItemCardapioDto(itemCardapio.get());
			return ResponseEntity.ok(itemCardapioDto);
		} 
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{idItem}")
	@Transactional
	public ResponseEntity<ItemCardapioDto> removerItem(@PathVariable Long idRestaurante, @PathVariable Long idItem) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(idRestaurante);
		Optional<ItemCardapio> itemCardapio = cardapioService.detalharItem(idItem);
		if (isOk(restaurante, itemCardapio)) {
			cardapioService.removerItem(restaurante.get(), itemCardapio.get());
			return ResponseEntity.ok().build();
		} 
		return ResponseEntity.notFound().build();
	}

	private boolean isOk(Optional<Restaurante> restaurante, Optional<ItemCardapio> itemCardapio) {
		if (restaurante.isPresent() 
				&& itemCardapio.isPresent() 
				&& itemCardapio.get().getRestaurante() == restaurante.get()) {
			return true;
		}
		return false;
	}
}
