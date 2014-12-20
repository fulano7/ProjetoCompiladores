package rwsets.robocode;

import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import rwsets.Helper;

import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;

import depend.util.graph.SimpleGraph;

public class RobocodeTests {
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String EXAMPLES_SRC = EXAMPLES + SEP + "src";
  String EXAMPLES_JAR = EXAMPLES;
  String RESOURCES_DIR = USER_DIR + SEP + "dat";
//Nenhum dos testes seguintes
@Test
  public void testAvoiderTurn() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Robocode" + SEP + "last"
        + SEP + "Avoider.java";
    String robocode = EXAMPLES_JAR + SEP + "robocode.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(robocode)).exists());
    String line = "turnRadarLeft(-(90 - 90 * direction) + getRadarHeading() - getHeading());";
    SimpleGraph sg = depend.Main.analyze(robocode, "last", strCompUnit,
        line);
    
    System.out.println(sg.toDotString());
    /*
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/manuip/Robocode.testAvoiderTurn.data";
    
   Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
   */ 
  }
 @Test
 public void testAvoiderNearWall() throws IOException, WalaException,
     CancelException, ParseException, InvalidClassFileException {
   String strCompUnit = EXAMPLES_SRC + SEP + "Robocode" + SEP + "last"
       + SEP + "Avoider.java";
   String robocode = EXAMPLES_JAR + SEP + "robocode.jar";
   Assert.assertTrue((new File(strCompUnit)).exists());
   Assert.assertTrue((new File(robocode)).exists());
   String line = "while (!nearWall()) {";
   SimpleGraph sg = depend.Main.analyze(robocode, "last", strCompUnit,
       line);
   
   System.out.println(sg.toDotString());
   /*
   String expectedResultFile = TEST_DIR + SEP
       + "rwsets/manuip/Robocode.testAvoiderNearWall.data";
   
  Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  */ 
 }
 @Test
 public void testSmartEnergy() throws IOException, WalaException,
     CancelException, ParseException, InvalidClassFileException {
   String strCompUnit = EXAMPLES_SRC + SEP + "Robocode" + SEP + "last"
       + SEP + "Smart.java";
   String robocode = EXAMPLES_JAR + SEP + "robocode.jar";
   Assert.assertTrue((new File(strCompUnit)).exists());
   Assert.assertTrue((new File(robocode)).exists());
   String line = "double changeInEnergy = previousEnergy - e.getEnergy();";
   SimpleGraph sg = depend.Main.analyze(robocode, "last", strCompUnit,
       line);
   
   System.out.println(sg.toDotString());
   /*
   String expectedResultFile = TEST_DIR + SEP
       + "rwsets/manuip/Robocode.testAvoiderNearWall.data";
   
  Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  */ 
 }
}
