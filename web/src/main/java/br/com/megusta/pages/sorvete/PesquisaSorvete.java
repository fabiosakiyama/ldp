package br.com.megusta.pages.sorvete;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.component.command.img.ImageCommand;
import jmine.tec.web.wicket.pages.form.CrudModelPage;
import jmine.tec.web.wicket.pages.form.FormType;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.SorveteDAO;
import br.com.megusta.domain.Sorvete;

@SuppressWarnings("serial")
@Secure(id = "URL_LIST_SORVETE", permissionType = UrlPermission.class)
public class PesquisaSorvete extends
CrudModelPage<PesquisaSorveteModel, Sorvete> {
	
	private int qtdTotalSorvetes;
	
	@Injected
	private SorveteDAO sorveteDAO;

	public Page createNewPage() {
		qtdTotalSorvetes = sorveteDAO.findQtdTotalSomenteSorvetes();
		return new CadastroSorvete(this.getPageClass(), new PageParameters());
	}

	@Override
	protected void addSearchFields(WebMarkupContainer container) {
		container.add(new CheckBox("foraDeLinha",  new PropertyModel<Boolean>(this.getModel(), "foraDeLinha")));
		Label label = ComponentHelper.createLabel("qtdTotalSorvetes", new PropertyModel<Integer>(this, "qtdTotalSorvetes"));
		container.add(label);
	}

	public List<Sorvete> search(DAOFactory daoFactory) {
		List<Sorvete> sorvetes = sorveteDAO.findAllByForaDeLinha(getModel().isForaDeLinha());
		qtdTotalSorvetes = sorveteDAO.findQtdTotalSomenteSorvetes();
		return sorvetes;
	}

	@Override
	protected Page createFormPage(Sorvete entity, FormType formType) {
		return new CadastroSorvete(this.getPageClass(), new PageParameters(),
				entity, formType);
	}

	@Override
	protected void addResultTableColumns(ReportTableBuilder<Sorvete> table) {
		table.addStringColumn("sabor", "Sabor", "sabor");
		table.addNumberColumn("preco", "Preço", "preco", 2);
		table.addNumberColumn("quantidade", "Quantidade", "quantidade", 0);
		table.addSorting("quantidade");
		table.addSorting("preco");
		table.addSorting("sabor");
	}

	@Override
	protected PesquisaSorveteModel createModel() {
		return new PesquisaSorveteModel();
	}

	@Override
	protected List<ImageCommand> getTableCommands(Sorvete entity) {
		List<ImageCommand> commands = new ArrayList<ImageCommand>();
		commands.add(this.createConfirmDeleteCommand());
		commands.add(this.createEditCommand());
		return commands;
	}

}
