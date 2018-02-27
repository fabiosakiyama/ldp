package br.com.megusta.domain;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.persist.test.testcase.BOBaseTestCase;
import jmine.tec.utils.date.Timestamp;
import junit.framework.TestSuite;

public class PedidoTest extends BOBaseTestCase<Pedido> {

	private static final Timestamp[] DATAS_HORA = new Timestamp[] { new Timestamp(), new Timestamp(2012, 2, 6, 12, 40, 22, 11),
			new Timestamp(2015, 2, 2, 6, 15, 00, 33) };

	private static final double[] VALORES = new double[] { 25, 30, 55.5 };

	private static Sorvete[] SORVETES;

	private static FormaPagamento[] FORMA_PAGAMENTO;
	
	private static StatusPedido[] STATUS_PEDIDO;

	private static Boolean[] ENTREGA;

	public static TestSuite suite() {
		return jmine.tec.persist.test.testcase.BOBaseTestCase.getDefaultSuite(PedidoTest.class);
	}

	@Override
	protected void initializeTestData() throws BeanNotFoundException {
		super.initializeTestData();
		SORVETES = new SorveteTest().getSavedTestData().toArray(new Sorvete[3]);
		FORMA_PAGAMENTO = new FormaPagamento[] { FormaPagamento.BRINDE, FormaPagamento.DEBITO, FormaPagamento.DINHEIRO };
		STATUS_PEDIDO = new StatusPedido[] { StatusPedido.ABERTO, StatusPedido.PAGO, StatusPedido.ABERTO };
		ENTREGA = new Boolean[] { Boolean.FALSE, Boolean.TRUE, Boolean.TRUE };
	}

	@Override
	protected void compareData(int idx, Pedido bo) throws BeanNotFoundException {
		assertEquals(DATAS_HORA[idx], bo.getDataHora());
		assertEquals(VALORES[idx], bo.getValor());
		assertEquals(SORVETES[idx], bo.getSorvetes().get(0));
		assertEquals(FORMA_PAGAMENTO[idx], bo.getFormaPagamento());
		assertEquals(STATUS_PEDIDO[idx], bo.getStatusPedido());
		assertEquals(ENTREGA[idx], bo.getEntrega());
	}

	@Override
	protected void fillData(int idx, Pedido bo) {
		if (bo.getSorvetes() != null) {
			bo.getSorvetes().clear();
		}
		bo.setDataHora(DATAS_HORA[idx]);
		bo.setValor(VALORES[idx]);
		bo.setFormaPagamento(FORMA_PAGAMENTO[idx]);
		bo.setStatusPedido(STATUS_PEDIDO[idx]);
		bo.getSorvetes().add(SORVETES[idx]);
		bo.setEntrega(ENTREGA[idx]);
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
