package rwsets.logica;

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

public class LogicaTest {
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String EXAMPLES_SRC = EXAMPLES + SEP + "src";
  String EXAMPLES_JAR = EXAMPLES;
  String RESOURCES_DIR = USER_DIR + SEP + "dat";

  @Test
  public void testClearClausulas() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
        + "Resolucao.java";
    String logica = EXAMPLES_JAR + SEP + "logica.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(logica)).exists());
    String line = "clausulas.clear();";
    SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/logica/LogicaTest.testClearClausulas.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }

  @Test
  public void testFunctionsHorn() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
        + "Resolucao.java";
    String logica = EXAMPLES_JAR + SEP + "logica.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(logica)).exists());
    String line = "if (Functions.verifyHorn(exp)){";
    SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/logica/LogicaTest.testFunctionsHorn.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }

  // 
  @Test
  // if (Functions.SAT(exp, clausulas)){
  public void testFunctionsSAT() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
        + "Resolucao.java";
    String logica = EXAMPLES_JAR + SEP + "logica.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(logica)).exists());
    String line = "if (Functions.SAT(exp, clausulas)){";
    SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/logica/LogicaTest.testFunctionsSAT.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }
  @Test
  //if (verifyFNC(exp)){
  public void testResolucaoFNC() throws IOException, WalaException, CancelException,
  ParseException, InvalidClassFileException {
String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
    + "Resolucao.java";
String logica = EXAMPLES_JAR + SEP + "logica.jar";
Assert.assertTrue((new File(strCompUnit)).exists());
Assert.assertTrue((new File(logica)).exists());
String line = " if (Functions.verifyFNC(exp)){";
SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
System.out.println(sg.toDotString());
String expectedResultFile = TEST_DIR + SEP
    + "rwsets/logica/LogicaTest.testResolucaoFNC2.data";
Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
}
  @Test
  //if (verifyFNC(exp)){
  public void testResolucaoFNC2() throws IOException, WalaException, CancelException,
  ParseException, InvalidClassFileException {
String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
    + "Resolucao2.java";
String logica = EXAMPLES_JAR + SEP + "logica.jar";
Assert.assertTrue((new File(strCompUnit)).exists());
Assert.assertTrue((new File(logica)).exists());
String line = "return Functions.verifyFNC(exp);";
SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
System.out.println(sg.toDotString());
String expectedResultFile = TEST_DIR + SEP
    + "rwsets/logica/LogicaTest.testResolucaoFNC2.data";
Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
}
  @Test
  ///if (isLiteral(clausulas.get(i)))
  public void testFunctionsIsLiteral() throws IOException, WalaException, CancelException,
  ParseException, InvalidClassFileException {
String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
    + "Functions.java";
String logica = EXAMPLES_JAR + SEP + "logica.jar";
Assert.assertTrue((new File(strCompUnit)).exists());
Assert.assertTrue((new File(logica)).exists());
String line = "if (isLiteral(clausulas.get(i)))";
SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
System.out.println(sg.toDotString());
String expectedResultFile = TEST_DIR + SEP
    + "rwsets/logica/LogicaTest.testFunctionsIsLiteral.data";
Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
} 
  //  i = eliminar(clausulas, cl, i, j);
  @Test
  public void testFunctionsEliminar() throws IOException, WalaException, CancelException,
  ParseException, InvalidClassFileException {
String strCompUnit = EXAMPLES_SRC + SEP + "Logica" + SEP + "logica" + SEP
    + "Functions.java";
String logica = EXAMPLES_JAR + SEP + "logica.jar";
Assert.assertTrue((new File(strCompUnit)).exists());
Assert.assertTrue((new File(logica)).exists());
String line = "i = eliminar(clausulas, cl, i, j);";
SimpleGraph sg = depend.Main.analyze(logica, "logica", strCompUnit, line);
System.out.println(sg.toDotString());
String expectedResultFile = TEST_DIR + SEP
    + "rwsets/logica/LogicaTest.testFunctionsEliminar.data";
Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
} 
}
