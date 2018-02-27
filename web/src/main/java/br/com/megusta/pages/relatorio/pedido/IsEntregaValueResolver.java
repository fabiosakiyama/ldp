package br.com.megusta.pages.relatorio.pedido;

import java.util.List;

import jmine.tec.report.impl.table.CellValueResolver;
import jmine.tec.utils.date.Date;
import br.com.megusta.domain.Pedido;

/**
 * @author fabio
 *
 */
public class IsEntregaValueResolver implements CellValueResolver<Pedido> {

	private List<Pedido> pedidos;

	public IsEntregaValueResolver(List<Pedido> pedidos, Date dataInicio, Date dataFim) {
		this.pedidos = pedidos;
	}

	public Object resolveCellValue(Pedido rowValue) {
		return this.pedidos.size();
	}

}
