package br.com.megusta.pages.relatorio.gastos.consolidado;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.DAOFactory;
import jmine.tec.report.api.Report;
import jmine.tec.report.base.BaseReportCreator;
import jmine.tec.report.impl.ReportBuilder;
import jmine.tec.report.impl.table.ReportTableBuilder;
import jmine.tec.report.impl.table.ReportTableGroupBuilder;
import jmine.tec.utils.Tuple;
import br.com.megusta.dao.GastosDAO;
import br.com.megusta.dao.TipoGastoDAO;

@SuppressWarnings("serial")
public class RelatorioGastosConsolidadoReportCreator extends BaseReportCreator<RelatorioGastosConsolidadoParameter> {

	@Injected
	private transient DAOFactory daoFactory;

	public RelatorioGastosConsolidadoReportCreator(Class<RelatorioGastosConsolidadoParameter> parameterClass) {
		super(parameterClass);
	}

	public RelatorioGastosConsolidadoReportCreator() {
		super(RelatorioGastosConsolidadoParameter.class);
	}

	@Override
	public String getCreatorKey() {
		return "Relatório de Gastos";
	}

	@Override
	protected List<Tuple<String, Report>> doCreateReports(RelatorioGastosConsolidadoParameter parameter) {
		ReportBuilder reportBuilder = new ReportBuilder("GastosC", this.getFileName("gastosc", parameter));
		GastosDAO gastosDAO = this.daoFactory.getDAOByClass(GastosDAO.class);

		List<GastosConsolidadoVO> lista = new ArrayList<GastosConsolidadoVO>();
		List<String> choices = new ArrayList<String>();
		TipoGastoDAO tipoGastoDAO = daoFactory.getDAOByClass(TipoGastoDAO.class);
		choices = tipoGastoDAO.findAllNames();
		for (String tipo : choices) {
			double soma = gastosDAO.sumValorByPeriodoTipoGastoFixo(parameter.getDataInicio(), parameter.getDataFim(), tipo, parameter.getFixo());
			GastosConsolidadoVO vo = new GastosConsolidadoVO(tipo, soma);
			lista.add(vo);
		}

		if (!lista.isEmpty()) {
			ReportTableBuilder<GastosConsolidadoVO> builder = reportBuilder.createTableBuilder(GastosConsolidadoVO.class, "Gastosc");
			builder.setRecords(lista);

			builder.addStringColumn("tipoGasto", "Tipo Gasto", "tipoGasto");
			builder.addNumberColumn("valorTotal", "Valor Total", "valorTotal", 2);

			ReportTableGroupBuilder<GastosConsolidadoVO> groupBuilder = builder.createGroupBuilder();
			groupBuilder.addSum("valorTotal", 2);

			groupBuilder.close();
			builder.close();
		}
		return this.pack("Relatorio_", parameter, reportBuilder.createReport());
	}
	
	public DAOFactory getDaoFactory() {
		return this.daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	

}
