package br.com.megusta.pages.relatorio.gastos.consolidado;

import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.api.batch.ReportCreator;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.utils.date.Date;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.bootstrap.component.datefield.BootstrapDateTextField;
import jmine.tec.web.wicket.pages.form.ReportPage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@SuppressWarnings("serial")
@Secure(id = "URL_REL_GASTOS_CON", permissionType = UrlPermission.class)
public class RelatorioGastosConsolidado extends ReportPage<RelatorioGastosConsolidadoParameter> {

	private RelatorioGastosConsolidadoParameter modelz;
	
	@SpringBean
	private RelatorioGastosConsolidadoReportCreator reportCreator;
	
	@SpringBean
	private DAOFactory daoFactory;

	@Override
	protected void addSearchFields(WebMarkupContainer c) {
		
		BootstrapDateTextField campoDataIni = ComponentHelper.createBootstrapDateField("dataInicio", new PropertyModel<Date>(modelz, "dataInicio"));
		c.add(campoDataIni);
		BootstrapDateTextField campoDataFim= ComponentHelper.createBootstrapDateField("dataFim", new PropertyModel<Date>(modelz, "dataFim"));
		c.add(campoDataFim);
		
		final CheckBox checkFixo = new CheckBox("fixo", new PropertyModel<Boolean>(modelz, "fixo"));
		c.add(checkFixo);
	}

	

	@Override
	protected ReportCreator<RelatorioGastosConsolidadoParameter> getReportCreator() {
		return this.reportCreator;
	}

	@Override
	protected RelatorioGastosConsolidadoParameter createReportParameter() {
		if (modelz == null) {
			modelz = new RelatorioGastosConsolidadoParameter();
		}
		return modelz;
	}

}
