/**
 *
 */
package br.com.megusta.pages.relatorio.sorvete;

import jmine.tec.report.api.batch.ReportCreator;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.pages.form.ReportPage;

import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author fabio
 *
 */
@Secure(id = "URL_REL_SORVETE", permissionType = UrlPermission.class)
public class RelatorioSorvete extends ReportPage<RelatorioSorveteReportParameter> {

	@SpringBean
	private RelatorioSorveteReportCreator reportCreator;

	@Override
	protected ReportCreator<RelatorioSorveteReportParameter> getReportCreator() {
		return this.reportCreator;
	}

	@Override
	protected RelatorioSorveteReportParameter createReportParameter() {
		return new RelatorioSorveteReportParameter();
	}

}
