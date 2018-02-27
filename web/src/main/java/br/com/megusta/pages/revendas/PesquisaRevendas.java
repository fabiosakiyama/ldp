package br.com.megusta.pages.revendas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.RevendasDAO;
import br.com.megusta.domain.RevendaEnum;
import br.com.megusta.domain.Revendas;

@SuppressWarnings("serial")
@Secure(id = "URL_LIST_REVENDAS", permissionType = UrlPermission.class)
public class PesquisaRevendas extends CrudModelPage<PesquisaRevendasModel, Revendas> {

	@SuppressWarnings("unused")
	private int valorTotal;
	
	@SuppressWarnings("unused")
	private int quantidadeTotal;
	
	public Page createNewPage() {
		return new CadastroRevendas(new PageParameters());
	}

	@Override
	protected void addSearchFields(WebMarkupContainer container) {
		container.add(ComponentHelper.createBootstrapDateField("dataInicio"));
		container.add(ComponentHelper.createBootstrapDateField("dataFim"));
		container.add(ComponentHelper.createDropDown("revendaEnum", Arrays.asList(RevendaEnum.values())));
		Label label = ComponentHelper.createLabel("valorTotal", new PropertyModel<Integer>(this, "valorTotal"));
		container.add(label);
		Label label2 = ComponentHelper.createLabel("quantidadeTotal", new PropertyModel<Integer>(this, "quantidadeTotal"));
		container.add(label2);
		
	}

	public List<Revendas> search(DAOFactory daoFactory) {
		RevendasDAO dao = daoFactory.getDAOByClass(RevendasDAO.class);
		List<Revendas> revendas = dao.findByPeriodoLocalRevenda(this.getModel().getDataInicio(), this.getModel().getDataFim(), this.getModel().getRevendaEnum());
		valorTotal = 0;
		quantidadeTotal = 0;
		for (Revendas revenda : revendas) {
			valorTotal += revenda.getValor();
			quantidadeTotal += revenda.getQuantidade();
		}
		return revendas;
	}

	@Override
	protected Page createFormPage(Revendas arg0, FormType arg1) {
		return null;
	}

	@Override
	protected void addResultTableColumns(ReportTableBuilder<Revendas> table) {
		table.addDateColumn("data", "Data/Hora", "data");
		table.addNumberColumn("valor", "Valor", "valor", 2);
		table.addNumberColumn("quantidade", "Quantidade", "quantidade", 2);
		table.addStringColumn("revendaEnum", "Local de Revenda", "revendaEnum");
	}

	@Override
	protected PesquisaRevendasModel createModel() {
		return new PesquisaRevendasModel();
	}

	@Override
	protected List<ImageCommand> getTableCommands(Revendas entity) {
		List<ImageCommand> commands = new ArrayList<ImageCommand>();
		commands.add(this.createConfirmDeleteCommand());
		return commands;
	}

}
