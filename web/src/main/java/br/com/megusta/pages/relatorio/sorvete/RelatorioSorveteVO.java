package br.com.megusta.pages.relatorio.sorvete;

import java.io.Serializable;

public class RelatorioSorveteVO implements Serializable, Comparable<RelatorioSorveteVO> {

	private String sabor;

	private Integer qtd;

	public RelatorioSorveteVO(String sabor, Integer qtd) {
		this.sabor = sabor;
		this.qtd = qtd;
	}

	public String getSabor() {
		return this.sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public Integer getQtd() {
		return this.qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public int compareTo(RelatorioSorveteVO o) {
		return o.getQtd().compareTo(this.getQtd());
	}

}
