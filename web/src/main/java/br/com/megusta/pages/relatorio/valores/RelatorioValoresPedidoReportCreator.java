/**
 *
 */
package br.com.megusta.pages.relatorio.valores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.api.Report;
import jmine.tec.report.base.BaseReportCreator;
import jmine.tec.report.impl.ReportBuilder;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.utils.Tuple;
import jmine.tec.utils.date.Date;
import jmine.tec.utils.date.Timestamp;
import jmine.tec.web.wicket.converter.TimestampConverter;
import br.com.megusta.dao.PedidoDAO;
import br.com.megusta.dao.RevendasDAO;
import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.Pedido;

/**
 * @author fabio
 *
 */
public class RelatorioValoresPedidoReportCreator extends BaseReportCreator<RelatorioValoresPedidoReportParameter> {

	@Injected
	private transient DAOFactory daoFactory;

	public RelatorioValoresPedidoReportCreator(Class<RelatorioValoresPedidoReportParameter> parameterClass) {
		super(parameterClass);
	}

	/**
	 * C'tor
	 */
	public RelatorioValoresPedidoReportCreator() {
		super(RelatorioValoresPedidoReportParameter.class);
	}

	public String getCreatorKey() {
		return "Relatório de Valores de Pedidos";
	}

	@Override
	protected List<Tuple<String, Report>> doCreateReports(RelatorioValoresPedidoReportParameter parameter) {
		ReportBuilder reportBuilder = new ReportBuilder("Pedidos", this.getFileName("pedidos", parameter));
		PedidoDAO pedidoDAO = this.daoFactory.getDAOByClass(PedidoDAO.class);
		RevendasDAO revendasDAO = this.daoFactory.getDAOByClass(RevendasDAO.class);

		Timestamp tsStart = this.getTimestampByDateAndTime(parameter.getDataInicio(), parameter.getHoraIni());
		Timestamp tsEnd = this.getTimestampByDateAndTime(parameter.getDataFim(), parameter.getHoraFim());
		if ((parameter.getHoraFim() == null) || parameter.getHoraFim().equals("")) {
			tsEnd.addDays(1);
		}
		ValoresPedidoVO vo = new ValoresPedidoVO();
		Double somaValorPeriodo = pedidoDAO.somaValorPeriodo(tsStart, tsEnd, null);
		Double somaValorRevenda = revendasDAO.somaValorRevenda(parameter.getDataInicio(), parameter.getDataFim());
		vo.setValor(somaValorPeriodo + somaValorRevenda);
		vo.setValorDinheiro(pedidoDAO.somaValorPeriodo(tsStart, tsEnd, FormaPagamento.DINHEIRO));
		vo.setValorDebito(pedidoDAO.somaValorPeriodo(tsStart, tsEnd, FormaPagamento.DEBITO));
		vo.setValorCredito(pedidoDAO.somaValorPeriodo(tsStart, tsEnd, FormaPagamento.CREDITO));
		vo.setValorCartao(vo.getValorCredito() + vo.getValorDebito());
		vo.setValorLiqCredito(pedidoDAO.somaValorLiquidoPeriodo(tsStart, tsEnd, FormaPagamento.CREDITO));
		vo.setValorLiqDebito(pedidoDAO.somaValorLiquidoPeriodo(tsStart, tsEnd, FormaPagamento.DEBITO));
		vo.setValorRevendas(somaValorRevenda);
		List<Pedido> pedidos = pedidoDAO.findByPeriodo(tsStart, tsEnd, null, null);
		int qtd = 0;
		for (Pedido pedido : pedidos) {
			qtd += pedido.getSorvetes().size();
		}
		vo.setQuantidadeSorvetes(qtd);
		
		List<Pedido> pedidosPagos = pedidoDAO.findByPeriodo(tsStart, tsEnd, FormaPagamento.CREDITO, null);
		pedidosPagos.addAll(pedidoDAO.findByPeriodo(tsStart, tsEnd, FormaPagamento.DEBITO, null));
		pedidosPagos.addAll(pedidoDAO.findByPeriodo(tsStart, tsEnd, FormaPagamento.DINHEIRO, null));
		int qtdPaga = 0;
		for (Pedido pedido : pedidosPagos) {
			qtdPaga += pedido.getSorvetes().size();
		}
		vo.setQuantidadeVendida(qtdPaga);
		
		List<ValoresPedidoVO> vos = Collections.singletonList(vo);
		if (!vos.isEmpty()) {
			ReportTableBuilder<ValoresPedidoVO> builder = reportBuilder.createTableBuilder(ValoresPedidoVO.class, "ValoresPedidos");
			builder.setRecords(vos);
			builder.addNumberColumn("valor", "Valor Total c/ Revendas", "valor", 2);
			builder.addNumberColumn("valorDinheiro", "Valor Dinheiro", "valorDinheiro", 2);
			builder.addNumberColumn("valorDebito", "Valor Debito", "valorDebito", 2);
			builder.addNumberColumn("valorCredito", "Valor Credito", "valorCredito", 2);
			builder.addNumberColumn("valorCartao", "Valor Cartao", "valorCartao", 2);
			builder.addNumberColumn("valorLiqDebito", "Valor Liq Debito", "valorLiqDebito", 2);
			builder.addNumberColumn("valorLiqCredito", "Valor Liq Credito", "valorLiqCredito", 2);
			builder.addNumberColumn("valorRevendas", "Valor Revendas", "valorRevendas", 2);
			builder.addNumberColumn("quantidadeSorvetes", "Quantidade Total", "quantidadeSorvetes", 0);
			builder.addNumberColumn("quantidadeVendida", "Quantidade Paga", "quantidadeVendida", 0);
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
	private Timestamp getTimestampByDateAndTime(Date data, String time) {
		String timeToParse = (time == null) || time.equals("") ? "00:00:00" : time + ":00";
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
}
