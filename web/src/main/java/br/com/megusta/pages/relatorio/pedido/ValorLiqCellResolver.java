/**
 *
 */
package br.com.megusta.pages.relatorio.pedido;

import jmine.tec.report.impl.table.CellValueResolver;
import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.Pedido;

/**
 * @author fabio
 *
 */
public class ValorLiqCellResolver implements CellValueResolver<Pedido> {

	public Object resolveCellValue(Pedido rowValue) {
		Double valor = rowValue.getValor();
		if (rowValue.getFormaPagamento().equals(FormaPagamento.CREDITO)) {
			valor = valor - (valor * 0.034);
			// 3.4%
		}
		if (rowValue.getFormaPagamento().equals(FormaPagamento.DEBITO)) {
			valor = valor - (valor * 0.026);
			// 2.6%
		}
		return valor;
	}

}
