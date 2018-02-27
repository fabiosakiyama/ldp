package br.com.megusta.pages.gastos;

import java.io.Serializable;

import jmine.tec.utils.date.Date;

@SuppressWarnings("serial")
public class PesquisaGastosModel implements Serializable{
	
	private Date dataInicio = new Date();

	private Date dataFim = new Date();

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
