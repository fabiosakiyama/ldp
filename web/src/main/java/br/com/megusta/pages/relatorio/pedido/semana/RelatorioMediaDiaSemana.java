package br.com.megusta.pages.relatorio.pedido.semana;

import jmine.tec.report.api.batch.ReportCreator;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.pages.form.ReportPage;

import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author fabio
 *
 */
@Secure(id = "URL_REL_PEDIDO_SEMANA", permissionType = UrlPermission.class)
public class RelatorioMediaDiaSemana extends ReportPage<RelatorioMediaDiaSemanaReportParameter> {

	@SpringBean
	private RelatorioMediaDiaSemanaReportCreator reportCreator;

	@Override
	protected ReportCreator<RelatorioMediaDiaSemanaReportParameter> getReportCreator() {
		return this.reportCreator;
	}

	@Override
	protected RelatorioMediaDiaSemanaReportParameter createReportParameter() {
		return new RelatorioMediaDiaSemanaReportParameter();
	}

}
