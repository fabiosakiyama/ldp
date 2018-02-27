package br.com.megusta.domain;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.persist.test.testcase.BOBaseTestCase;
import jmine.tec.utils.date.Date;
import junit.framework.TestSuite;

public class RevendasTest extends BOBaseTestCase<Revendas> {

	private static final Date[] DATAS_HORA = new Date[] { new Date(), new Date(2012, 2, 6),
			new Date(2015, 2, 2) };

	private static final double[] VALORES = new double[] { 25, 30, 55.5 };

	private static RevendaEnum[] REVENDAS_ENUM;

	public static TestSuite suite() {
		return jmine.tec.persist.test.testcase.BOBaseTestCase.getDefaultSuite(RevendasTest.class);
	}

	@Override
	protected void initializeTestData() throws BeanNotFoundException {
		super.initializeTestData();
		REVENDAS_ENUM = new RevendaEnum[] { RevendaEnum.CARRINHO, RevendaEnum.MINUANO, RevendaEnum.EVENTOS };
	}

	@Override
	protected void compareData(int idx, Revendas bo) throws BeanNotFoundException {
		assertEquals(DATAS_HORA[idx], bo.getData());
		assertEquals(VALORES[idx], bo.getValor());
		assertEquals(REVENDAS_ENUM[idx], bo.getRevendaEnum());
	}

	@Override
	protected void fillData(int idx, Revendas bo) {
		bo.setData(DATAS_HORA[idx]);
		bo.setValor(VALORES[idx]);
		bo.setRevendaEnum(REVENDAS_ENUM[idx]);
	}

	@Override
	protected int getTestDataSize() {
		return 3;
	}

	@Override
	protected String[] getResourcesBaseReferencia() {
		return new String[] {};
	}

	@Override
	protected String getSpringMainXMLFilename() {
		return "core-test-beans.xml";
	}

}
