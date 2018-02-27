package br.com.megusta.pages.relatorio.gastos.consolidado;

import java.io.Serializable;

public class GastosConsolidadoVO implements Serializable {

	private String tipoGasto;
	
	private double valorTotal;

	public GastosConsolidadoVO(String tipo, double soma) {
		this.tipoGasto = tipo;
		this.valorTotal = soma;
	}

	public String getTipoGasto() {
		return tipoGasto;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setTipoGasto(String tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	
}
