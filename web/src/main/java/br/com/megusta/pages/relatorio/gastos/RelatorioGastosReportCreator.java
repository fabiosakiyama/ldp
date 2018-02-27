package br.com.megusta.pages.relatorio.gastos;

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
import br.com.megusta.domain.CategoriaGasto;
import br.com.megusta.domain.Gastos;

@SuppressWarnings("serial")
public class RelatorioGastosReportCreator extends BaseReportCreator<RelatorioGastosParameter> {

	@Injected
	private transient DAOFactory daoFactory;

	public RelatorioGastosReportCreator(Class<RelatorioGastosParameter> parameterClass) {
		super(parameterClass);
	}

	public RelatorioGastosReportCreator() {
		super(RelatorioGastosParameter.class);
	}

	@Override
	public String getCreatorKey() {
		return "Relatório de Gastos";
	}

	@Override
	protected List<Tuple<String, Report>> doCreateReports(RelatorioGastosParameter parameter) {
		ReportBuilder reportBuilder = new ReportBuilder("Gastos", this.getFileName("gastos", parameter));
		GastosDAO gastosDAO = this.daoFactory.getDAOByClass(GastosDAO.class);

		List<Gastos> gastosPessoal = gastosDAO.findByPeriodoTipoGastoFixo(parameter.getDataInicio(), parameter.getDataFim(), parameter.getTipoGasto(), parameter.getFixo(), CategoriaGasto.PESSOAL);
		List<Gastos> gastosPIngrediente = gastosDAO.findByPeriodoTipoGastoFixo(parameter.getDataInicio(), parameter.getDataFim(), parameter.getTipoGasto(), parameter.getFixo(), CategoriaGasto.PALETA_INGREDIENTES);
		List<Gastos> gastosPEstrutura = gastosDAO.findByPeriodoTipoGastoFixo(parameter.getDataInicio(), parameter.getDataFim(), parameter.getTipoGasto(), parameter.getFixo(), CategoriaGasto.PALETA_ESTRUTURA);

		if (!gastosPessoal.isEmpty() || !gastosPIngrediente.isEmpty() || !gastosPEstrutura.isEmpty()) {
			ReportTableBuilder<Gastos> builderPessoal = reportBuilder.createTableBuilder(Gastos.class, "GastosPessoal");
			builderPessoal.setRecords(gastosPessoal);

			builderPessoal.addDateColumn("data", "Data", "data");
			builderPessoal.addNumberColumn("valor", "Valor", "valor", 2);
			builderPessoal.addStringColumn("tipoGasto", "Tipo Gasto", "tipoGasto.tipoGasto");
			builderPessoal.addStringColumn("isFixo", "Fixo? ", "fixo");
			builderPessoal.addStringColumn("detalheGasto", "Detalhe Gastos ", "detalheGasto");

			ReportTableGroupBuilder<Gastos> groupBuilderPessoal = builderPessoal.createGroupBuilder();
			groupBuilderPessoal.addSum("valor", 2);

			groupBuilderPessoal.close();
			builderPessoal.close();
			
			ReportTableBuilder<Gastos> builderPaletaIngrediente = reportBuilder.createTableBuilder(Gastos.class, "GastosPessoal");
			builderPaletaIngrediente.setRecords(gastosPIngrediente);

			builderPaletaIngrediente.addDateColumn("data", "Data", "data");
			builderPaletaIngrediente.addNumberColumn("valor", "Valor", "valor", 2);
			builderPaletaIngrediente.addStringColumn("tipoGasto", "Tipo Gasto", "tipoGasto.tipoGasto");
			builderPaletaIngrediente.addStringColumn("isFixo", "Fixo? ", "fixo");
			builderPaletaIngrediente.addStringColumn("detalheGasto", "Detalhe Gastos ", "detalheGasto");

			ReportTableGroupBuilder<Gastos> groupBuilderPaletaIngrediente = builderPaletaIngrediente.createGroupBuilder();
			groupBuilderPaletaIngrediente.addSum("valor", 2);

			groupBuilderPaletaIngrediente.close();
			builderPaletaIngrediente.close();
			
			ReportTableBuilder<Gastos> builderPaletaEstrutura = reportBuilder.createTableBuilder(Gastos.class, "GastosPessoal");
			builderPaletaEstrutura.setRecords(gastosPEstrutura);

			builderPaletaEstrutura.addDateColumn("data", "Data", "data");
			builderPaletaEstrutura.addNumberColumn("valor", "Valor", "valor", 2);
			builderPaletaEstrutura.addStringColumn("tipoGasto", "Tipo Gasto", "tipoGasto.tipoGasto");
			builderPaletaEstrutura.addStringColumn("isFixo", "Fixo? ", "fixo");
			builderPaletaEstrutura.addStringColumn("detalheGasto", "Detalhe Gastos ", "detalheGasto");

			ReportTableGroupBuilder<Gastos> groupBuilderPaletaEstrutura = builderPaletaEstrutura.createGroupBuilder();
			groupBuilderPaletaEstrutura.addSum("valor", 2);

			builderPaletaEstrutura.close();
			groupBuilderPaletaEstrutura.close();
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
