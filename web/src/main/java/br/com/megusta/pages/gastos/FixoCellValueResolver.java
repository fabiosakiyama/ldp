package br.com.megusta.pages.gastos;

import jmine.tec.report.impl.table.CellValueResolver;
import br.com.megusta.domain.Gastos;

@SuppressWarnings("serial")
public class FixoCellValueResolver implements CellValueResolver<Gastos> {

	@Override
	public Object resolveCellValue(Gastos rowValue) {
		return rowValue.isFixo()+ "";
	}
	
}
