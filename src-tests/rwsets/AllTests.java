package rwsets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  // Add test classes here
  rwsets.coffeemaker.TestCoffeeMaker.class,
  rwsets.core.Sanity.class,
  rwsets.projetoip.TestProjetoIP.class,
  rwsets.projetoipempacotado.TestProjetoIPEmpacotado.class,
  rwsets.projetopg.Test_PG_Projeto_2_Jar.class,
  rwsets.projetogdi.TestProjetoGDI.class
})

public class AllTests  { }