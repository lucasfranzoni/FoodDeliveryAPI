package br.com.everis.delivery.model.dto;

import org.springframework.data.domain.Page;

import br.com.everis.delivery.model.entity.Cliente;
import br.com.everis.delivery.model.entity.ItemSacola;

public class ClienteDto extends UsuarioDto{

	private String cpf;
	
	public ClienteDto(Cliente cliente) {
		super(cliente);
		this.cpf = cliente.getCpf();
	}

	public String getCpf() {
		return cpf;
	}

	public static Page<ClienteDto> converterPaginaClientes(Page<Cliente> clientes) {
		return clientes.map(ClienteDto::new);
	}

	public static Page<ItemSacolaDto> converterPaginaSacola(Page<ItemSacola> sacola) {
		return sacola.map(ItemSacolaDto::new);
	}
	
	
}
