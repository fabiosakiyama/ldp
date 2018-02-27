/**
 *
 */
package br.com.megusta.pages.relatorio.pedido;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.api.Report;
import jmine.tec.report.base.BaseReportCreator;
import jmine.tec.report.impl.ReportBuilder;
import jmine.tec.report.impl.table.CellValueResolver;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.report.impl.table.ReportTableGroupBuilder;
import jmine.tec.utils.Tuple;
import jmine.tec.utils.date.Date;
import jmine.tec.utils.date.Timestamp;
import jmine.tec.web.wicket.converter.TimestampConverter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.megusta.dao.PedidoDAO;
import br.com.megusta.domain.Pedido;
import br.com.megusta.pages.SorvetesValueResolver;

/**
 * @author fabio
 * 
 */
public class RelatorioPedidoReportCreator extends BaseReportCreator<RelatorioPedidoReportParameter> {

	@Injected
	private transient DAOFactory daoFactory;

	public RelatorioPedidoReportCreator(Class<RelatorioPedidoReportParameter> parameterClass) {
		super(parameterClass);
	}

	/**
	 * C'tor
	 */
	public RelatorioPedidoReportCreator() {
		super(RelatorioPedidoReportParameter.class);
	}

	public String getCreatorKey() {
		return "Relatório de Pedidos";
	}

	@Override
	protected List<Tuple<String, Report>> doCreateReports(RelatorioPedidoReportParameter parameter) {
		ReportBuilder reportBuilder = new ReportBuilder("Pedidos", this.getFileName("pedidos", parameter));
		PedidoDAO pedidoDAO = this.daoFactory.getDAOByClass(PedidoDAO.class);

		Timestamp tsStart = this.getInicioTimestampByDateAndTime(parameter.getDataInicio(), parameter.getHoraIni());
		Timestamp tsEnd = this.getFimTimestampByDateAndTime(parameter.getDataFim(), parameter.getHoraFim());
		if ((parameter.getHoraFim() == null) || parameter.getHoraFim().equals("")) {
			tsEnd.addDays(1);
		}
		List<Pedido> pedidos = pedidoDAO.findByPeriodo(tsStart, tsEnd, parameter.getFormaPagamento(), parameter.getEntrega());

		if (!pedidos.isEmpty()) {
			ReportTableBuilder<Pedido> builder = reportBuilder.createTableBuilder(Pedido.class, "Pedidos");
			builder.setRecords(pedidos);

			builder.addStringColumn("numeroPedido", "Número Pedido", "id");
			builder.addTimestampColumn("dataHora", "Data/Hora", "dataHora");
			builder.addNumberColumn("valor", "Valor", "valor", 2);
			builder.addNumberColumn("valorLiq", "Valor Liq(Deb/Cred)", new ValorLiqCellResolver(), 2);
			builder.addNumberColumn("troco", "Troco", "troco", 2);
			builder.addNumberColumn("valorRecebido", "Valor Recebido", "valorRecebido", 2);
			builder.addNumberColumn("qtdCobertura", "Qtd. Cobertura", "qtdCobertura", 2);
			builder.addNumberColumn("qtdTopping", "Qtd. Topping", "qtdTopping", 2);
			builder.addNumberColumn("desconto", "Desconto", "desconto", 2);
			builder.addStringColumn("formaPagamento", "Forma Pagamento", "formaPagamento");
			builder.addStringColumn("entrega", "Entrega? ", "entrega");
			builder.addStringColumn("sorvetes", "Sorvetes", new SorvetesValueResolver());

			ReportTableGroupBuilder<Pedido> groupBuilder = builder.createGroupBuilder();
			groupBuilder.addSum("valor", 2);
			groupBuilder.addSum("valorLiq", 2);
			groupBuilder.addSum("troco", 2);
			groupBuilder.addSum("valorRecebido", 2);
			groupBuilder.addSum("qtdCobertura", 2);
			groupBuilder.addSum("qtdTopping", 2);
			groupBuilder.addSum("desconto", 2);
			if (parameter.getEntrega()) {
				groupBuilder.addFooterDynamicString("entrega", new IsEntregaValueResolver(pedidos, parameter.getDataInicio(), parameter.getDataFim()));
			}
			groupBuilder.addFooterDynamicString("sorvetes", new QuantidadeSorvetesValueResolver(pedidos, parameter.getDataInicio(), parameter.getDataFim()));

			groupBuilder.close();
			builder.close();
		}
		return this.pack("Relatorio_", parameter, reportBuilder.createReport());
	}

	/**
	 * Cria um Timestamp a partir da data e horário passados nos parâmetros.
	 * 
	 * @param data
	 *            a data
	 * @param time
	 *            o horário (formato "HH:mm")
	 * @return um Timestamp criado a partir da data e horário passados nos
	 *         parâmetros.
	 */
	private Timestamp getFimTimestampByDateAndTime(Date data, String time) {
		String timeToParse = (time == null) || time.equals("") ? "22:00:00" : time + ":00";
		DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp ts = (Timestamp) new TimestampConverter().convertToObject(dataFormat.format(data) + " " + timeToParse, null);
		ts.setNanos(0);
		return ts;
	}
	
	private Timestamp getInicioTimestampByDateAndTime(Date data, String time) {
		String timeToParse = (time == null) || time.equals("") ? "01:00:00" : time + ":00";
		DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp ts = (Timestamp) new TimestampConverter().convertToObject(dataFormat.format(data) + " " + timeToParse, null);
		ts.setNanos(0);
		return ts;
	}

	public DAOFactory getDaoFactory() {
		return this.daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private class QuantidadeSorvetesValueResolver implements CellValueResolver<Pedido> {

		private List<Pedido> pedidos;

		private Date dataIni;

		private Date dataFim;

		public QuantidadeSorvetesValueResolver(List<Pedido> pedidos, Date dataIni, Date dataFim) {
			this.pedidos = pedidos;
			this.dataIni = dataIni;
			this.dataFim = dataFim;
		}

		public Object resolveCellValue(Pedido rowValue) {
			int qtd = 0;
			for (Pedido pedido : this.pedidos) {
				qtd += pedido.getSorvetes().size();
			}

			if (this.dataIni.compareTo(this.dataFim) != 0) {
				DateTime dataInicial = new DateTime(this.dataIni);
				DateTime dataFinal = new DateTime(this.dataFim);
				Days daysBetween = Days.daysBetween(dataInicial, dataFinal);
				double media = qtd / (daysBetween.getDays() + 1);
				return qtd + " | Media: " + media;
			}
			return qtd;
		}
	}
}
