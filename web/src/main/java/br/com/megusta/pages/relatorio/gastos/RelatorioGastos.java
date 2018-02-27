package br.com.megusta.pages.relatorio.gastos;

import java.util.ArrayList;
import java.util.List;

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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.megusta.dao.TipoGastoDAO;

@SuppressWarnings("serial")
@Secure(id = "URL_REL_GASTOS", permissionType = UrlPermission.class)
public class RelatorioGastos extends ReportPage<RelatorioGastosParameter> {

	private RelatorioGastosParameter modelz;
	
	@SpringBean
	private RelatorioGastosReportCreator reportCreator;
	
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
		
		List<String> choices = new ArrayList<String>();
		TipoGastoDAO tipoGastoDAO = daoFactory.getDAOByClass(TipoGastoDAO.class);
		choices = tipoGastoDAO.findAllNames();
		
		final DropDownChoice<String> dropDown = ComponentHelper.createDropDown("tipoGasto", new PropertyModel<String>(modelz,
				"tipoGasto"), choices);

		c.add(dropDown);
	}

	

	@Override
	protected ReportCreator<RelatorioGastosParameter> getReportCreator() {
		return this.reportCreator;
	}

	@Override
	protected RelatorioGastosParameter createReportParameter() {
		if (modelz == null) {
			modelz = new RelatorioGastosParameter();
		}
		return modelz;
	}

}
