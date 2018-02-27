package br.com.megusta.pages.sorvete;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PesquisaSorveteModel implements Serializable {

	private String nome;

	private double preco;

	private int quantidade;
	
	private boolean foraDeLinha;

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return this.preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public boolean isForaDeLinha() {
		return foraDeLinha;
	}

	public void setForaDeLinha(boolean foraDeLinha) {
		this.foraDeLinha = foraDeLinha;
	}
}
