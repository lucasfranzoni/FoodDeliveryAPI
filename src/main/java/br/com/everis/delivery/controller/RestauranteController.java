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

import br.com.everis.delivery.model.dto.RestauranteDto;
import br.com.everis.delivery.model.entity.Restaurante;
import br.com.everis.delivery.model.form.RestauranteForm;
import br.com.everis.delivery.model.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;
	
	@GetMapping
	public Page<RestauranteDto> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		Page<Restaurante> restaurantes = restauranteService.listarTodos(paginacao);
		return RestauranteDto.converterPaginaRestaurantes(restaurantes);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<RestauranteDto> cadastrar(@RequestBody @Valid RestauranteForm form) {
		Restaurante restaurante = form.converter();
		restauranteService.cadastrar(restaurante);
		RestauranteDto restauranteDto = new RestauranteDto(restaurante);
		return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RestauranteDto> detalhar(@PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(id);
		if(restaurante.isPresent()) {
			RestauranteDto restauranteDto = new RestauranteDto(restaurante.get());
			return ResponseEntity.ok(restauranteDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<RestauranteDto> atualizar(@RequestBody @Valid RestauranteForm form, @PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(id);
		if(restaurante.isPresent()) {
			Restaurante atualizacoes = form.converter();
			restauranteService.atualizar(restaurante.get(), atualizacoes);
			RestauranteDto restauranteDto = new RestauranteDto(restaurante.get());
			return ResponseEntity.ok(restauranteDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<RestauranteDto> deletar(@PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteService.detalhar(id);
		if(restaurante.isPresent()) {
			restauranteService.deletar(restaurante.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
