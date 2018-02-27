package br.com.megusta.domain;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.persist.test.testcase.BOBaseTestCase;
import jmine.tec.utils.date.Date;
import junit.framework.TestSuite;

public class GastosTest extends BOBaseTestCase<Gastos> {

	private static final Date[] DATAS = new Date[] { new Date(), new Date(2012, 2, 6), new Date(2015, 2, 2) };

	private static final double[] VALORES = new double[] { 378.54, 231, 2521.5 };

	private static final boolean[] IS_FIXO = new boolean[] { true, false, false };

	private static TipoGasto[] TIPO_GASTO;
	
	private static CategoriaGasto[] CATEGORIA_GASTO = CategoriaGasto.values();
	
	public static TestSuite suite() {
		return jmine.tec.persist.test.testcase.BOBaseTestCase.getDefaultSuite(GastosTest.class);
	}
	
	@Override
	protected void initializeTestData() throws BeanNotFoundException {
		TIPO_GASTO = new TipoGastoTest().getSavedTestData().toArray(new TipoGasto[3]);

	}

	@Override
	protected void fillData(int idx, Gastos bo) {
		bo.setData(DATAS[idx]);
		bo.setValor(VALORES[idx]);
		bo.setTipoGasto(TIPO_GASTO[idx]);
		bo.setFixo(IS_FIXO[idx]);
		bo.setCategoriaGasto(CATEGORIA_GASTO[idx]);
	}

	@Override
	protected void compareData(int idx, Gastos bo) throws BeanNotFoundException {
		assertEquals(DATAS[idx], bo.getData());
		assertEquals(VALORES[idx], bo.getValor());
		assertEquals(TIPO_GASTO[idx], bo.getTipoGasto());
		assertEquals(IS_FIXO[idx], bo.isFixo());
		assertEquals(CATEGORIA_GASTO[idx], bo.getCategoriaGasto());
	}

	@Override
	protected String getSpringMainXMLFilename() {
		return "core-test-beans.xml";
	}

	@Override
	protected String[] getResourcesBaseReferencia() {
		return new String[] {};
	}

	@Override
	protected int getTestDataSize() {
		return 3;
	}
}
