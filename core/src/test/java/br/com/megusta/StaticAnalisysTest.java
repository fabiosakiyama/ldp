package br.com.megusta;

import jmine.tec.persist.test.testcase.AbstractBOStaticAnalysisTest;

/**
 * Static Analisys Test
 */
public abstract class StaticAnalisysTest extends AbstractBOStaticAnalysisTest {

	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	protected String getSpringMainXMLFilename() {
		return "core-test-beans.xml";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	protected String getControllerSpringId() {
		return "simpleController";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	protected String[] getResourcesBaseReferencia() {
		return new String[] { "core-db.xml" };
	}

	/**
	 * @return boolean
	 */
	@Override
	protected boolean failOnSchemaWarnings() {
		return false;
	}

}
