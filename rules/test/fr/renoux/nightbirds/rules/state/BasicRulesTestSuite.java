package fr.renoux.nightbirds.rules.state;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LegalBlankCardTest.class, IllegalBlankCardTest.class, DistrictTest.class })
public class BasicRulesTestSuite {

}
