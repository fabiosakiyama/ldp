/**
 *
 */
package br.com.megusta.pages.relatorio.sorvete;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.api.Report;
import jmine.tec.report.base.BaseReportCreator;
import jmine.tec.report.impl.ReportBuilder;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.report.impl.table.ReportTableGroupBuilder;
import jmine.tec.utils.Tuple;
import jmine.tec.utils.date.Date;
import jmine.tec.utils.date.Timestamp;
import jmine.tec.web.wicket.converter.TimestampConverter;
import br.com.megusta.dao.PedidoDAO;
import br.com.megusta.dao.SorveteDAO;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;

/**
 * @author fabio
 *
 */
public class RelatorioSorveteReportCreator extends BaseReportCreator<RelatorioSorveteReportParameter> {

	private Map<String, Integer> mapaSaborQtd = new HashMap<String, Integer>();
	@Injected
	private transient DAOFactory daoFactory;

	public RelatorioSorveteReportCreator(Class<RelatorioSorveteReportParameter> parameterClass) {
		super(parameterClass);
	}

	public RelatorioSorveteReportCreator() {
		super(RelatorioSorveteReportParameter.class);
	}

	public String getCreatorKey() {
		return "Relatório de Sorvetes";
	}

	@Override
	protected List<Tuple<String, Report>> doCreateReports(RelatorioSorveteReportParameter parameter) {
		this.mapaSaborQtd.clear();
		ReportBuilder reportBuilder = new ReportBuilder("Pedidos", this.getFileName("pedidos", parameter));
		PedidoDAO pedidoDAO = this.daoFactory.getDAOByClass(PedidoDAO.class);
		SorveteDAO sorveteDAO = this.daoFactory.getDAOByClass(SorveteDAO.class);

		Timestamp tsStart = this.getTimestampByDateAndTime(parameter.getDataInicio(), parameter.getHoraIni());
		Timestamp tsEnd = this.getTimestampByDateAndTime(parameter.getDataFim(), parameter.getHoraFim());
		if ((parameter.getHoraFim() == null) || parameter.getHoraFim().equals("")) {
			tsEnd.addDays(1);
		}
		List<Pedido> pedidos = pedidoDAO.findByPeriodo(tsStart, tsEnd, null, null);
		for (Pedido pedido : pedidos) {
			List<Sorvete> sorvetes = pedido.getSorvetes();
			for (Sorvete sorvete : sorvetes) {
				if (sorvete.getSabor().startsWith("z") || sorvete.getSabor().startsWith("Z")) {
					continue;
				}
				String chave = sorvete.getSabor();
				Integer qtdContada = this.mapaSaborQtd.get(chave);
				// Se já tem
				if (qtdContada != null) {
					// soma 1
					qtdContada++;
					this.mapaSaborQtd.put(chave, qtdContada);
				} else {
					// Senao coloca no mapa com 1
					this.mapaSaborQtd.put(chave, 1);
				}
			}
		}

		List<RelatorioSorveteVO> lista = new ArrayList<RelatorioSorveteVO>();
		for (Entry<String, Integer> entry : this.mapaSaborQtd.entrySet()) {
			String sabor = entry.getKey();
			Integer qtd = entry.getValue();
			RelatorioSorveteVO vo = new RelatorioSorveteVO(sabor, qtd);
			lista.add(vo);
		}

		Collections.sort(lista);
		if (!lista.isEmpty()) {
			ReportTableBuilder<RelatorioSorveteVO> builder = reportBuilder.createTableBuilder(RelatorioSorveteVO.class, "Sorvetes");
			builder.setRecords(lista);

			builder.addStringColumn("sabor", "Sabor", "sabor");
			builder.addNumberColumn("qtd", "Quantidade Vendida", "qtd", 0);

			ReportTableGroupBuilder<RelatorioSorveteVO> groupBuilder = builder.createGroupBuilder();
			groupBuilder.addFooterString("sabor", "Total:");
			groupBuilder.addSum("qtd", 0);

			groupBuilder.close();
			builder.close();
		}
		return this.pack("Relatorio_", parameter, reportBuilder.createReport());
	}

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
