package br.com.megusta;

import br.com.megusta.pages.gastos.PesquisaGastos;
import br.com.megusta.pages.gastos.PesquisaTipoGastos;
import br.com.megusta.pages.pedido.PesquisaPedido;
import br.com.megusta.pages.pedido.aberto.PesquisaPedidoAberto;
import br.com.megusta.pages.relatorio.gastos.RelatorioGastos;
import br.com.megusta.pages.relatorio.gastos.consolidado.RelatorioGastosConsolidado;
import br.com.megusta.pages.relatorio.pedido.RelatorioPedido;
import br.com.megusta.pages.relatorio.pedido.semana.RelatorioMediaDiaSemana;
import br.com.megusta.pages.relatorio.sorvete.RelatorioSorvete;
import br.com.megusta.pages.relatorio.valores.RelatorioValoresPedido;
import br.com.megusta.pages.revendas.PesquisaRevendas;
import br.com.megusta.pages.sorvete.PesquisaSorvete;
import jmine.tec.web.pages.datadigester.importacao.Importacao;
import jmine.tec.web.pages.persist.audit.ConsultaTrilhaAuditoria;
import jmine.tec.web.pages.persist.auth.Authorization;
import jmine.tec.web.pages.rtm.ConsultaExceptionRecord;
import jmine.tec.web.pages.rtm.diagnosticador.ConsultaDiagnosticador;
import jmine.tec.web.pages.user.AlterarSenhaPage;
import jmine.tec.web.pages.user.CrudUsuarioPage;
import jmine.tec.web.wicket.component.menu.cfg.AbstractMenuConfigFactoryBean;
import jmine.tec.web.wicket.component.menu.cfg.MenuConfig;

/**
 * Starting point menu creator
 *
 * @author takeshi
 */
public class WebApplicationMenuCreator extends AbstractMenuConfigFactoryBean {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MenuConfig createMenuConfig() {
		MenuConfig config = new MenuConfig();

		// add menu here
		config.addPage(Authorization.class, "Autorização", "Autorizar");
		config.addPage(ConsultaTrilhaAuditoria.class, "Autorização", "Auditoria");
		config.addPage(ConsultaDiagnosticador.class, "Infra", "Diagnosticador");
		config.addPage(ConsultaExceptionRecord.class, "Infra", "Exceptions");
		config.addPage(Importacao.class, "Infra", "Importacao");
		config.addPage(AlterarSenhaPage.class, "Infra", "Alterar senha");
		config.addPage(CrudUsuarioPage.class, "Infra", "Controle de acesso");

		config.addPage(PesquisaSorvete.class, "Sorvetes");
		config.addPage(PesquisaPedido.class, "Pedidos");
		config.addPage(PesquisaPedidoAberto.class, "Pedidos Em Aberto");
		config.addPage(PesquisaRevendas.class, "Revendas");
		config.addPage(PesquisaTipoGastos.class, "Gastos", "Tipo Gastos");
		config.addPage(PesquisaGastos.class, "Gastos", "Gastos");
		
		config.addPage(RelatorioPedido.class, "Relatórios", "Pedido");
		config.addPage(RelatorioMediaDiaSemana.class, "Relatórios", "Pedido Dia Semana");
		config.addPage(RelatorioValoresPedido.class, "Relatórios", "Valores Pedido");
		config.addPage(RelatorioSorvete.class, "Relatórios", "Sorvete");
		config.addPage(RelatorioGastos.class, "Relatórios", "Gastos");
		config.addPage(RelatorioGastosConsolidado.class, "Relatórios", "Gastos Consolidados");

		return config;
	}

}
