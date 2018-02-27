package br.com.megusta.pages.relatorio.valores;

import jmine.tec.report.api.batch.ReportCreator;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.pages.form.ReportPage;

import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author fabio
 *
 */
@Secure(id = "URL_REL_VAL_PEDIDO", permissionType = UrlPermission.class)
public class RelatorioValoresPedido extends ReportPage<RelatorioValoresPedidoReportParameter> {

	@SpringBean
	private RelatorioValoresPedidoReportCreator reportCreator;

	@Override
	protected ReportCreator<RelatorioValoresPedidoReportParameter> getReportCreator() {
		return this.reportCreator;
	}

	@Override
	protected RelatorioValoresPedidoReportParameter createReportParameter() {
		return new RelatorioValoresPedidoReportParameter();
	}

}
