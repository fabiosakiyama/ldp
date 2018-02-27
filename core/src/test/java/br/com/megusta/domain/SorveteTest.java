package br.com.megusta.domain;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.persist.test.testcase.BOBaseTestCase;
import junit.framework.TestSuite;

public class SorveteTest extends BOBaseTestCase<Sorvete> {

	private static final Sabor[] SABORES = new Sabor[] { Sabor.ACAI_BANANA,
		Sabor.MORANGO_LEITECONDENSADO, Sabor.PACOCA };

	private static final double[] PRECOS = new double[] { 3, 6, 9 };

	private static final int[] QUANTIDADE = new int[] { 30, 62, 200 };

	public static TestSuite suite() {
		return jmine.tec.persist.test.testcase.BOBaseTestCase
				.getDefaultSuite(SorveteTest.class);
	}

	@Override
	protected void compareData(int idx, Sorvete bo)
			throws BeanNotFoundException {
		assertEquals(SABORES[idx].getNome(), bo.getSabor());
		assertEquals(PRECOS[idx], bo.getPreco());
		assertEquals(QUANTIDADE[idx], bo.getQuantidade());
	}

	@Override
	protected void fillData(int idx, Sorvete bo) {
		bo.setSabor(SABORES[idx].getNome());
		bo.setPreco(PRECOS[idx]);
		bo.setQuantidade(QUANTIDADE[idx]);
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
