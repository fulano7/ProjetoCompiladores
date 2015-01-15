package rwsets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  // Add test classes here
  rwsets.coffeemaker.TestCoffeeMaker.class,
  rwsets.core.Sanity.class,
  rwsets.soot.SootTests.class
})

public class AllTests  { }