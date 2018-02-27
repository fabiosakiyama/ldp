package br.com.megusta.pages.pedido;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CadastroPedidoModel implements Serializable {

	private String sabor;

	private double preco;

	private int quantidadeDisponivel;

	private int quantidadeAComprar;

	public String getSabor() {
		return this.sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public double getPreco() {
		return this.preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidadeDisponivel() {
		return this.quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public int getQuantidadeAComprar() {
		return this.quantidadeAComprar;
	}

	public void setQuantidadeAComprar(int quantidadeAComprar) {
		this.quantidadeAComprar = quantidadeAComprar;
	}

}
