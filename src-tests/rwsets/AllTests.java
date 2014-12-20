package rwsets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  // Add test classes here
 rwsets.geeoip.GeeoIpTest.class,
 rwsets.manuip.ManuIpTest.class,
 rwsets.hardware.HardwareTest.class,
 rwsets.robocode.RobocodeTests.class,
 rwsets.logica.LogicaTest.class
 
})

public class AllTests  { }