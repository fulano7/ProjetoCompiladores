package rwsets.geeoip;

import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import rwsets.Helper;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.io.CommandLine;
import com.ibm.wala.util.warnings.Warnings;

import depend.MethodDependencyAnalysis;
import depend.util.graph.SimpleGraph;

import org.junit.Assert;

public class GeeoIpTest {
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String EXAMPLES_SRC = EXAMPLES + SEP + "src";
  String EXAMPLES_JAR = EXAMPLES;
  String RESOURCES_DIR = USER_DIR + SEP + "dat";

 @Test
  public void testAtualizarProfessor() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "GeeoIp" + SEP + "fachadaEscola"
        + SEP + "Escola.java";
    String geeoip = EXAMPLES_JAR + SEP + "Oi.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(geeoip)).exists());
    String line = "pessoas.atualizar(professor);";
    SimpleGraph sg = depend.Main.analyze(geeoip, "fachadaEscola", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/geeoip/GeeoIpTest.testAtualizarProfessor.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }

   @Test
  public void testFindNameInArray() throws ParseException,
      InvalidClassFileException, IOException, WalaException, CancelException {

    String strCompUnit = EXAMPLES_SRC + SEP + "GeeoIp" + SEP + "fachadaEscola"
        + SEP + "Escola.java";
    String geeoip = EXAMPLES_JAR + SEP + "Oi.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(geeoip)).exists());
    String line = "if (repositorio.equalsIgnoreCase(\"array\")) {";
    SimpleGraph sg = depend.Main.analyze(geeoip, "fachadaEscola", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/geeoip/GeeoIpTest.testFindNameInArray.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }

   @Test
  public void testFindPerson() throws ParseException,
      InvalidClassFileException, IOException, WalaException, CancelException {

    String strCompUnit = EXAMPLES_SRC + SEP + "GeeoIp" + SEP + "fachadaEscola"
        + SEP + "Escola.java";
    String geeoip = EXAMPLES_JAR + SEP + "Oi.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(geeoip)).exists());
    String line = " Pessoa p = pessoas.procurar(cpf);// verifica se a pessoa já foi";
    SimpleGraph sg = depend.Main.analyze(geeoip, "fachadaEscola", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/geeoip/GeeoIpTest.testFindPerson.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }

  @Test
  public void testPaginaPrincipal() throws ParseException,
      InvalidClassFileException, IOException, WalaException, CancelException {

    String strCompUnit = EXAMPLES_SRC + SEP + "GeeoIp" + SEP + "classesBase"
        + SEP + "Boletim.java";
    String geeoip = EXAMPLES_JAR + SEP + "Oi.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(geeoip)).exists());
    String line = "this.disciplinas.inserir(d);";

    SimpleGraph sg = depend.Main.analyze(geeoip, "classesBase", strCompUnit,
        line);

    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/geeoip/GeeoIpTest.testInserirDisciplinaBoletim.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
    Assert.assertEquals(1, 1);

  }

  /*
   * @Test public void testPaginaPrincipal2() throws ParseException,
   * InvalidClassFileException, IOException, WalaException, CancelException {
   * 
   * String strCompUnit = EXAMPLES_SRC + SEP + "GeeoIp" + SEP + "dados" + SEP +
   * "IteratorListaPessoa.java"; String geeoip = EXAMPLES_JAR + SEP + "Oi.jar";
   * Assert.assertTrue((new File(strCompUnit)).exists()); Assert.assertTrue((new
   * File(geeoip)).exists()); String line =
   * "this.repositorio = new RepositorioListaPessoa();";
   * 
   * SimpleGraph sg = depend.Main.analyze(geeoip, "dados", strCompUnit, line);
   * 
   * System.out.println(sg.toDotString()); String expectedResultFile = TEST_DIR
   * + SEP + "rwsets/geeoip/GeeoIpTest.testFindPerson.data";
   * Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
   * Assert.assertEquals(1, 1);
   * 
   * }
   */
}
