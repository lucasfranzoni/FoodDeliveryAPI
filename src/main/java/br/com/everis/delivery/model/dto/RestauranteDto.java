package br.com.everis.delivery.model.dto;

import org.springframework.data.domain.Page;

import br.com.everis.delivery.model.entity.ItemCardapio;
import br.com.everis.delivery.model.entity.Restaurante;

public class RestauranteDto extends UsuarioDto{
	
	private String cnpj;
	
	public RestauranteDto(Restaurante restaurante) {
		super(restaurante);
		this.cnpj = restaurante.getCnpj();
	}

	public String getCnpj() {
		return cnpj;
	}

	public static Page<RestauranteDto> converterPaginaRestaurantes(Page<Restaurante> restaurantes) {
		return restaurantes.map(RestauranteDto::new);
	}
	
	public static Page<ItemCardapioDto> converterPaginaCardapio(Page<ItemCardapio> cardapio) {
		return cardapio.map(ItemCardapioDto::new);
	}

}
