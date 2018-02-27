package br.com.megusta.domain;

public enum StatusPedido {
	
	ABERTO, PAGO;

	public boolean isAberto() {
		return this.equals(StatusPedido.ABERTO);
	}
	
	public boolean isPago() {
		return this.equals(StatusPedido.PAGO);
	}
}
