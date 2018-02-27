package br.com.megusta.pages.gastos;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.GastosDAO;
import br.com.megusta.domain.Gastos;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.pages.form.CrudModelPage;
import jmine.tec.web.wicket.pages.form.FormType;

@SuppressWarnings("serial")
@Secure(id = "URL_LIST_GASTOS", permissionType = UrlPermission.class)
public class PesquisaGastos extends CrudModelPage<PesquisaGastosModel, Gastos>{

	@Override
	public Page createNewPage() {
		return new CadastroGastos(new PageParameters());
	}

	@Override
	protected Page createFormPage(Gastos entity, FormType formType) {
		return new CadastroGastos(this.getPageClass(), new PageParameters(), entity, formType);
	}
	
	@Override
	protected void addSearchFields(WebMarkupContainer container) {
		container.add(ComponentHelper.createBootstrapDateField("dataInicio"));
		container.add(ComponentHelper.createBootstrapDateField("dataFim"));
	}
	
	@Override
	public List<Gastos> search(DAOFactory daoFactory) {
		GastosDAO gastosDAO = daoFactory.getDAOByClass(GastosDAO.class);
		return gastosDAO.findByDateBetween(this.getModel().getDataInicio(), this.getModel().getDataFim());
	}

	@Override
	protected PesquisaGastosModel createModel() {
		return new PesquisaGastosModel();
	}

	@Override
	protected void addResultTableColumns(ReportTableBuilder<Gastos> table) {
		table.addStringColumn("data", "Data", "data");
		table.addNumberColumn("valor", "Valor", "valor", 2);
		table.addStringColumn("tipoGasto", "Tipo do Gasto", "tipoGasto.tipoGasto");
		table.addStringColumn("categoriaGasto", "Categoria", "categoriaGasto");
		table.addStringColumn("detalheGasto", "Detalhe", "detalheGasto");
		table.addStringColumn("fixo", "Fixo?", new FixoCellValueResolver());
	}
}
