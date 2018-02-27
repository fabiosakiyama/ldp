/**
 *
 */
package br.com.megusta.pages.relatorio.pedido;

import jmine.tec.report.base.BaseReportParameter;
import jmine.tec.utils.date.Date;
import jmine.tec.web.wicket.component.injection.FormInputProvider;
import jmine.tec.web.wicket.component.injection.composite.LabeledFormInput;
import br.com.megusta.domain.FormaPagamento;

/**
 * @author fabio
 *
 */
@FormInputProvider
public class RelatorioPedidoReportParameter extends BaseReportParameter {

	private String horaIni;

	private String horaFim;

	private Date dataInicio;

	private Date dataFim;

	private FormaPagamento formaPagamento;

	private Boolean entrega;

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

	public FormaPagamento getFormaPagamento() {
		return this.formaPagamento;
	}

	@LabeledFormInput(label = "Forma Pagamento")
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Boolean getEntrega() {
		return this.entrega;
	}

	@LabeledFormInput(label = "Entrega")
	public void setEntrega(Boolean entrega) {
		this.entrega = entrega;
	}

}
