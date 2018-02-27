package br.com.megusta.pages.pedido.aberto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.utils.date.Date;
import jmine.tec.utils.date.Timestamp;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.component.command.img.ImageCommand;
import jmine.tec.web.wicket.converter.TimestampConverter;
import jmine.tec.web.wicket.pages.form.CrudModelPage;
import jmine.tec.web.wicket.pages.form.FormType;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.PedidoDAO;
import br.com.megusta.domain.Pedido;
import br.com.megusta.pages.SorvetesValueResolver;
import br.com.megusta.pages.pedido.PesquisaPedidoModel;

@SuppressWarnings("serial")
@Secure(id = "URL_LIST_PEDIDO_ABERTO", permissionType = UrlPermission.class)
public class PesquisaPedidoAberto extends CrudModelPage<PesquisaPedidoModel, Pedido> {

	public Page createNewPage() {
		return new AlteraPedidoAberto(new PageParameters());
	}

	@Override
	protected void addSearchFields(WebMarkupContainer container) {
		container.add(ComponentHelper.createBootstrapDateField("dataInicio"));
		container.add(ComponentHelper.createBootstrapDateField("dataFim"));
	}

	public List<Pedido> search(DAOFactory daoFactory) {
		PedidoDAO dao = daoFactory.getDAOByClass(PedidoDAO.class);
		Timestamp inicio = this.getTimestampByDateAndTime(this.getModel().getDataInicio(), "01:00:00");
		Timestamp fim = this.getTimestampByDateAndTime(this.getModel().getDataFim(), "22:59:59");
		List<Pedido> pedidos = dao.findByPeriodoPesquisaEmAberto(inicio, fim, null, null);
		return pedidos;
	}

	@Override
	protected Page createFormPage(Pedido entity, FormType formType) {
		return new AlteraPedidoAberto(new PageParameters(), entity);
	}

	@Override
	protected void addResultTableColumns(ReportTableBuilder<Pedido> table) {
		table.addTimestampColumn("dataHora", "Data/Hora", "dataHora");
		table.addNumberColumn("valor", "Valor", "valor", 2);
		table.addStringColumn("sorvetes", "Sorvetes", new SorvetesValueResolver());
	}

	@Override
	protected PesquisaPedidoModel createModel() {
		return new PesquisaPedidoModel();
	}

	@Override
	protected List<ImageCommand> getTableCommands(Pedido entity) {
		List<ImageCommand> commands = new ArrayList<ImageCommand>();
		commands.add(this.createConfirmDeleteCommand());
		commands.add(this.createEditCommand());
		return commands;
	}

	private Timestamp getTimestampByDateAndTime(Date data, String time) {
		String timeToParse = (time == null) || time.equals("") ? "00:00:00" : time + ":00";
		DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp ts = (Timestamp) new TimestampConverter().convertToObject(dataFormat.format(data) + " " + timeToParse, null);
		ts.setNanos(0);
		return ts;
	}
}
