package br.com.megusta.pages.relatorio.valores;

import java.io.Serializable;

import br.com.megusta.domain.FormaPagamento;

public class ValoresPedidoVO implements Serializable {

	public Double valor = new Double(0);
	
	public Double valorDinheiro = new Double(0);
	
	public Double valorDebito = new Double(0);
	
	public Double valorCredito = new Double(0);
	
	public Double valorCartao = new Double(0);
	
	public Double valorLiqDebito = new Double(0);
	
	public Double valorLiqCredito = new Double(0);
	
	public Double valorRevendas = new Double(0);
	
	public int quantidadeSorvetes;
	
	public int quantidadeVendida;
	
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getValorDinheiro() {
		return valorDinheiro;
	}

	public void setValorDinheiro(Double valorDinheiro) {
		this.valorDinheiro = valorDinheiro;
	}

	public Double getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(Double valorDebito) {
		this.valorDebito = valorDebito;
	}

	public Double getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(Double valorCredito) {
		this.valorCredito = valorCredito;
	}

	public Double getValorCartao() {
		return valorCartao;
	}

	public void setValorCartao(Double valorCartao) {
		this.valorCartao = valorCartao;
	}

	public Double getValorLiqDebito() {
		return valorLiqDebito;
	}

	public void setValorLiqDebito(Double valorLiqDebito) {
		this.valorLiqDebito = valorLiqDebito;
	}

	public Double getValorLiqCredito() {
		return valorLiqCredito;
	}

	public void setValorLiqCredito(Double valorLiqCredito) {
		this.valorLiqCredito = valorLiqCredito;
	}

	public Double getValorRevendas() {
		return valorRevendas;
	}

	public void setValorRevendas(Double valorRevendas) {
		this.valorRevendas = valorRevendas;
	}

	public int getQuantidadeSorvetes() {
		return quantidadeSorvetes;
	}

	public void setQuantidadeSorvetes(int quantidadeSorvetes) {
		this.quantidadeSorvetes = quantidadeSorvetes;
	}

	public int getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(int quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}
	
}
