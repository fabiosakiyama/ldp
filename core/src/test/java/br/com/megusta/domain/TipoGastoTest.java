package br.com.megusta.domain;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.persist.test.testcase.BOBaseTestCase;
import junit.framework.TestSuite;

public class TipoGastoTest extends BOBaseTestCase<TipoGasto>{
	
	private static final String[] TIPOS_GASTOS = new String [] {"ALUGUEL", "INGREDIENTES", "MANUTENCAO"};

	public static TestSuite suite() {
		return jmine.tec.persist.test.testcase.BOBaseTestCase
				.getDefaultSuite(TipoGastoTest.class);
	}

	@Override
	protected void fillData(int idx, TipoGasto bo) {
		bo.setTipoGasto(TIPOS_GASTOS[idx]);
		
	}

	@Override
	protected void compareData(int idx, TipoGasto bo) throws BeanNotFoundException {
		assertEquals(TIPOS_GASTOS[idx], bo.getTipoGasto());
		
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
