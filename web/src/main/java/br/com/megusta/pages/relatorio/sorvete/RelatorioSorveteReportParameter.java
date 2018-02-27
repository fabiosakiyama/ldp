/**
 *
 */
package br.com.megusta.pages.relatorio.sorvete;

import jmine.tec.report.base.BaseReportParameter;
import jmine.tec.utils.date.Date;
import jmine.tec.web.wicket.component.injection.FormInputProvider;
import jmine.tec.web.wicket.component.injection.composite.LabeledFormInput;

/**
 * @author fabio
 *
 */
@FormInputProvider
public class RelatorioSorveteReportParameter extends BaseReportParameter {

	private String horaIni;

	private String horaFim;

	private Date dataInicio;

	private Date dataFim;

	// private String sorvete;
	//
	// public String getSorvete() {
	// return this.sorvete;
	// }
	//
	// @LabeledFormInput(label = "Sabor")
	// public void setSorvete(String sorvete) {
	// this.sorvete = sorvete;
	// }

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
	@LabeledFormInput(label = "Data Inicial", required = true)
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	};

	@Override
	@LabeledFormInput(label = "Data Final", required = true)
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	};

	public String getHoraIni() {
		return this.horaIni;
	}

	@LabeledFormInput(label = "Hora Inicial (HH:mm)")
	public void setHoraIni(String dataHoraIni) {
		this.horaIni = dataHoraIni;
	}

	public String getHoraFim() {
		return this.horaFim;
	}

	@LabeledFormInput(label = "Hora Final (HH:mm)")
	public void setHoraFim(String dataHoraFim) {
		this.horaFim = dataHoraFim;
	}

	@Override
	public String getReportFileNamePrefix() {
		return "Relatorio Sorvete Parameter";
	}

}
