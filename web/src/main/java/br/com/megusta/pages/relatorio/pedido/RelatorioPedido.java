package br.com.megusta.pages.relatorio.pedido;

import jmine.tec.report.api.batch.ReportCreator;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.pages.form.ReportPage;

import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author fabio
 *
 */
@Secure(id = "URL_REL_PEDIDO", permissionType = UrlPermission.class)
public class RelatorioPedido extends ReportPage<RelatorioPedidoReportParameter> {

	@SpringBean
	private RelatorioPedidoReportCreator reportCreator;

	@Override
	protected ReportCreator<RelatorioPedidoReportParameter> getReportCreator() {
		return this.reportCreator;
	}

	@Override
	protected RelatorioPedidoReportParameter createReportParameter() {
		return new RelatorioPedidoReportParameter();
	}

}
