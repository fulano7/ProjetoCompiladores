package rwsets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  // Add test classes here
  rwsets.coffeemaker.TestCoffeeMaker.class,
  rwsets.core.Sanity.class,
<<<<<<< HEAD
  rwsets.projetoip.TestProjetoIP.class,
  rwsets.projetoipempacotado.TestProjetoIPEmpacotado.class,
  rwsets.projetopg.Test_PG_Projeto_2_Jar.class,
  rwsets.projetogdi.TestProjetoGDI.class
=======
  rwsets.soot.SootTests.class
 /* if688-2014.2-group-2 */
  rwsets.escola.GeeoIpTest.class,
  rwsets.hardware.HardwareTest.class,
  rwsets.logica.LogicaTest.class,
  rwsets.manuip.ManuIpTest.class
>>>>>>> professor/master
})

public class AllTests  { }