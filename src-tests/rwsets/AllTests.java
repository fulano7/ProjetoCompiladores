package rwsets;
//import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // Add test classes here
  rwsets.coffeemaker.TestCoffeeMaker.class,
  rwsets.core.Sanity.class
})

public class AllTests  {
//  //This can be empty if you are using an IDE that includes support for JUnit
//  //such as Eclipse.  However, if you are using Java on the command line or
//  //with a simpler IDE like JGrasp or jCreator, the following main() and suite()
//  //might be helpful.
//
//  // Execution begins at main().  In this test class, we will execute
//  // a text test runner that will tell you if any of your tests fail.
//  public static void main (String[] args) {
//    junit.textui.TestRunner.run (suite());
//  }
//
//  // The suite() method is helpful when using JUnit 3 Test Runners or Ant.
//  public static junit.framework.Test suite() {
//    return new JUnit4TestAdapter(AllTests.class);
//  }

}