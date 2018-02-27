package br.com.megusta.pages.gastos;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.TipoGastoDAO;
import br.com.megusta.domain.TipoGasto;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.pages.form.CrudModelPage;
import jmine.tec.web.wicket.pages.form.FormType;

@SuppressWarnings("serial")
@Secure(id = "URL_LIST_TP_GASTOS", permissionType = UrlPermission.class)
public class PesquisaTipoGastos extends CrudModelPage<PesquisaTipoGastosModel, TipoGasto> {

	@Override
	public Page createNewPage() {
		return new CadastroTipoGastos(new PageParameters());
	}

	@Override
	public List<TipoGasto> search(DAOFactory daoFactory) {
		return daoFactory.getDAOByClass(TipoGastoDAO.class).findAll();
	}

	@Override
	protected Page createFormPage(TipoGasto entity, FormType formType) {
		return new CadastroTipoGastos(this.getPageClass(), new PageParameters(), entity, formType);
	}

	@Override
	protected PesquisaTipoGastosModel createModel() {
		return new PesquisaTipoGastosModel();
	}

	@Override
	protected void addResultTableColumns(ReportTableBuilder<TipoGasto> table) {
		table.addStringColumn("tipoGasto", "tipoGasto", "tipoGasto");
	}
}
