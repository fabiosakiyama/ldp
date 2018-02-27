/**
 *
 */
package br.com.megusta.pages.relatorio.pedido.semana;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
import br.com.megusta.domain.Pedido;

/**
 * @author fabio
 *
 */
public class RelatorioMediaDiaSemanaReportCreator extends BaseReportCreator<RelatorioMediaDiaSemanaReportParameter> {

	@Injected
	private transient DAOFactory daoFactory;

	private double qtdSegundas = 0;

	private double qtdTercas = 0;

	private double qtdQuartas = 0;

	private double qtdQuintas = 0;

	private double qtdSextas = 0;

	private double qtdSabados = 0;

	private double qtdDomingos = 0;

	public RelatorioMediaDiaSemanaReportCreator(Class<RelatorioMediaDiaSemanaReportParameter> parameterClass) {
		super(parameterClass);
	}

	/**
	 * C'tor
	 */
	public RelatorioMediaDiaSemanaReportCreator() {
		super(RelatorioMediaDiaSemanaReportParameter.class);
	}

	public String getCreatorKey() {
		return "Relatório de Pedidos Semanais";
	}

	@Override
	protected List<Tuple<String, Report>> doCreateReports(RelatorioMediaDiaSemanaReportParameter parameter) {
		ReportBuilder reportBuilder = new ReportBuilder("Pedidos_semanais", this.getFileName("pedidos", parameter));
		PedidoDAO pedidoDAO = this.daoFactory.getDAOByClass(PedidoDAO.class);

		Timestamp tsStart = this.getTimestampByDateAndTime(parameter.getDataInicio(), parameter.getHoraIni());
		Timestamp tsEnd = this.getTimestampByDateAndTime(parameter.getDataFim(), parameter.getHoraFim());
		if ((parameter.getHoraFim() == null) || parameter.getHoraFim().equals("")) {
			tsEnd.addDays(1);
		}
		qtdSegundas = 0;

		qtdTercas = 0;

		qtdQuartas = 0;

		qtdQuintas = 0;

		qtdSextas = 0;

		qtdSabados = 0;

		qtdDomingos = 0;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(tsStart);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(tsEnd);

		while (c2.after(c1)) {

			switch (c1.get(Calendar.DAY_OF_WEEK)) {

			case 1: {
				qtdDomingos++;
				break;
			}
			case 2: {
				qtdSegundas++;
				break;
			}
			case 3: {
				qtdTercas++;
				break;
			}
			case 4: {
				qtdQuartas++;
				break;
			}
			case 5: {
				qtdQuintas++;
				break;
			}
			case 6: {
				qtdSextas++;
				break;
			}
			case 7: {
				qtdSabados++;
				break;
			}

			}
			c1.add(Calendar.DATE, 1);
		}

		List<Pedido> pedidos = pedidoDAO.findByPeriodo(tsStart, tsEnd, parameter.getFormaPagamento(), parameter.getEntrega());

		List<Pedido> segundas = new ArrayList<Pedido>();
		List<Pedido> tercas = new ArrayList<Pedido>();
		List<Pedido> quartas = new ArrayList<Pedido>();
		List<Pedido> quintas = new ArrayList<Pedido>();
		List<Pedido> sextas = new ArrayList<Pedido>();
		List<Pedido> sabados = new ArrayList<Pedido>();
		List<Pedido> domingos = new ArrayList<Pedido>();

		for (Pedido pedido : pedidos) {

			java.sql.Timestamp ts = pedido.getDataHora();
			java.util.GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
			cal.setTime(ts);
			switch (cal.get(java.util.Calendar.DAY_OF_WEEK)) {

			case 1: {
				domingos.add(pedido);
				break;
			}
			case 2: {
				segundas.add(pedido);
				break;
			}
			case 3: {
				tercas.add(pedido);
				break;
			}
			case 4: {
				quartas.add(pedido);
				break;
			}
			case 5: {
				quintas.add(pedido);
				break;
			}
			case 6: {
				sextas.add(pedido);
				break;
			}
			case 7: {
				sabados.add(pedido);
				break;
			}

			}

		}
		MediaDiaSemanaVO vo = new MediaDiaSemanaVO();

		vo.setMediaSegunda(this.calcularMedia(segundas, qtdSegundas));
		vo.setMediaTerca(this.calcularMedia(tercas, qtdTercas));
		vo.setMediaQuarta(this.calcularMedia(quartas, qtdQuartas));
		vo.setMediaQuinta(this.calcularMedia(quintas, qtdQuintas));
		vo.setMediaSexta(this.calcularMedia(sextas, qtdSextas));
		vo.setMediaSabado(this.calcularMedia(sabados, qtdSabados));
		vo.setMediaDomingo(this.calcularMedia(domingos, qtdDomingos));

		ReportTableBuilder<MediaDiaSemanaVO> builder = reportBuilder.createTableBuilder(MediaDiaSemanaVO.class, "Pedidos Semana");
		builder.setRecords(Collections.singletonList(vo));

		builder.addNumberColumn("mediaSegunda", "Média Segunda", "mediaSegunda", 2);
		builder.addNumberColumn("mediaTerca", "Média Terça", "mediaTerca", 2);
		builder.addNumberColumn("mediaQuarta", "Média Quarta", "mediaQuarta", 2);
		builder.addNumberColumn("mediaQuinta", "Média Quinta", "mediaQuinta", 2);
		builder.addNumberColumn("mediaSexta", "Média Sexta", "mediaSexta", 2);
		builder.addNumberColumn("mediaSabado", "Média Sábado", "mediaSabado", 2);
		builder.addNumberColumn("mediaDomingo", "Média Domingo", "mediaDomingo", 2);

		builder.close();
		return this.pack("Relatorio_", parameter, reportBuilder.createReport());
	}

	private double calcularMedia(List<Pedido> pedidos, double qtdDiaSemana) {
		double media = 0;
		for (Pedido pedido : pedidos) {
			media = media + pedido.getValor();

		}
		return media / qtdDiaSemana;
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
