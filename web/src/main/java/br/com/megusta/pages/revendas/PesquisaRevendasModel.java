package br.com.megusta.pages.revendas;

import java.io.Serializable;

import br.com.megusta.domain.RevendaEnum;

import jmine.tec.utils.date.Date;

@SuppressWarnings("serial")
public class PesquisaRevendasModel implements Serializable {

	private Date dataInicio = new Date();

	private Date dataFim = new Date();
	
	private RevendaEnum revendaEnum;
	
	public RevendaEnum getRevendaEnum() {
		return revendaEnum;
	}

	public void setRevendaEnum(RevendaEnum revendaEnum) {
		this.revendaEnum = revendaEnum;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
}
