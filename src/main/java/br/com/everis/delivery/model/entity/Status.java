package br.com.everis.delivery.model.entity;

public enum Status {
	
	SENDO_PREPARADO{
		@Override
		public Status proximo() {
			return SENDO_ENTREGUE;
		}
		
	}, SENDO_ENTREGUE {
		@Override
		public Status proximo() {
			return FINALIZADO;
		}
		
	}, FINALIZADO ,CANCELADO;

	public Status proximo() {
		return null;
	}
}
