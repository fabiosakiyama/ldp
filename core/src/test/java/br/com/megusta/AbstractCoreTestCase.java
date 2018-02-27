package br.com.megusta;

import jmine.tec.persist.test.junit4.DBEnv;
import jmine.tec.persist.test.junit4.DBTestCase;

/**
 * AbstractCore TestCase and bitbucket commit test
 */
@DBEnv(refdb = "core-db.xml", spring = "core-test-beans.xml")
public abstract class AbstractCoreTestCase extends DBTestCase {

}
