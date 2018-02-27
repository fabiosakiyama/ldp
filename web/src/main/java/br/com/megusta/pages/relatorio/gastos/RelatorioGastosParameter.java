package br.com.megusta.pages.relatorio.gastos;

import jmine.tec.report.base.BaseReportParameter;
import jmine.tec.utils.date.Date;

@SuppressWarnings("serial")
public class RelatorioGastosParameter extends BaseReportParameter {

	private Date dataInicio;

	private Date dataFim;

	private Boolean fixo;

	private String tipoGasto;

	public String getTipoGasto() {
		return this.tipoGasto;
	}

	public void setTipoGasto(String tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public Boolean getFixo() {
		return fixo;
	}

	public void setFixo(Boolean fixo) {
		this.fixo = fixo;
	}

	/**
	 * @return the dataInicio
	 */
	@Override
	public Date getDataInicio() {
		if (this.dataInicio != null) {
			return this.dataInicio;
		}
		return new Date();
	}

	/**
	 * @return the dataFim
	 */
	@Override
	public Date getDataFim() {
		if (this.dataFim != null) {
			return this.dataFim;
		}
		return new Date();
	}

	@Override
	public String getReportFileNamePrefix() {
		return "Onde estou?";
	}

	@Override
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	};

	@Override
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	};
}

