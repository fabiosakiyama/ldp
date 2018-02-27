/**
 *
 */
package br.com.megusta.pages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jmine.tec.report.impl.table.CellValueResolver;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;

/**
 * @author fabio
 *
 */

public class SorvetesValueResolver implements CellValueResolver<Pedido> {

	public Object resolveCellValue(Pedido pedido) {
		List<Sorvete> sorvetes = pedido.getSorvetes();
		Collections.sort(sorvetes);
		StringBuilder sb = new StringBuilder();
		sb.append("Total: " + sorvetes.size() + " | ");

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Sorvete sorvete : sorvetes) {
			Integer qtd = map.get(sorvete.getSabor());
			if (qtd == null) {
				map.put(sorvete.getSabor(), 1);
			} else {
				qtd++;
				map.put(sorvete.getSabor(), qtd);
			}
		}

		for (Entry entry : map.entrySet()) {
			sb.append(  entry.getValue() + "x " + entry.getKey() + ".");
		}
		sb.append(pedido.getQtdCobertura() + "x cobertura.");
		sb.append(pedido.getQtdTopping() + "x toppings.");
		return sb.toString();
	}
}
