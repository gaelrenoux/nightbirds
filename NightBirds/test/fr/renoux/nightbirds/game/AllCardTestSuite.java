package fr.renoux.nightbirds.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BumTest.class, BurglarTest.class, CookTest.class, DJTest.class,
		GenericCardTest.class, PhotographTest.class, WhoreTest.class })
public class AllCardTestSuite {

}
