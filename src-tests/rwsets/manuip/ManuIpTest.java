package rwsets.manuip;

import japa.parser.ParseException;

import org.junit.Assert;
import org.junit.Test;

import rwsets.Helper;

import java.io.File;
import java.io.IOException;

import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;

import depend.util.graph.SimpleGraph;


public class ManuIpTest {
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String EXAMPLES_SRC = EXAMPLES + SEP + "src";
  String EXAMPLES_JAR = EXAMPLES;
  String RESOURCES_DIR = USER_DIR + SEP + "dat";

 @Test
  public void testSalaDados() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Biblioteca_2" + SEP + "fachada"
        + SEP + "Principal.java";
    String manuip = EXAMPLES_JAR + SEP + "biblioteca.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(manuip)).exists());
    String line = "String[] salaDados = mSala.getCacheDados();";
    SimpleGraph sg = depend.Main.analyze(manuip, "fachada", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/manuip/ManuIpTest.testSalaDadosCache.data";
    
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
    
  }
 @Test
 public void testRealizarEmprestimo() throws IOException, WalaException,
     CancelException, ParseException, InvalidClassFileException {
   String strCompUnit = EXAMPLES_SRC + SEP + "Biblioteca_2" + SEP + "fachada"
       + SEP + "Principal.java";
   String manuip = EXAMPLES_JAR + SEP + "biblioteca.jar";
   Assert.assertTrue((new File(strCompUnit)).exists());
   Assert.assertTrue((new File(manuip)).exists());
   String line = "String[] salaDados = mSala.getCacheDados();";
   SimpleGraph sg = depend.Main.analyze(manuip, "fachada", strCompUnit,
       line);
   System.out.println(sg.toDotString());
   String expectedResultFile = TEST_DIR + SEP
       + "rwsets/manuip/ManuIpTest.testRealizarEmprestimo.data";
   
   Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
   
 }
 @Test
 public void testInserirLivro() throws IOException, WalaException,
     CancelException, ParseException, InvalidClassFileException {
   String strCompUnit = EXAMPLES_SRC + SEP + "Biblioteca_2" + SEP + "negocios"
       + SEP + "ManipularLivro.java";
   String manuip = EXAMPLES_JAR + SEP + "biblioteca.jar";
   Assert.assertTrue((new File(strCompUnit)).exists());
   Assert.assertTrue((new File(manuip)).exists());
   String line = "generico.inserirLivro(livro);";
   SimpleGraph sg = depend.Main.analyze(manuip, "negocios", strCompUnit,
       line);
   System.out.println(sg.toDotString());
   String expectedResultFile = TEST_DIR + SEP
       + "rwsets/manuip/ManuIpTest.testInserirLivro.data";
   
   Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
   
 }

 @Test
 public void testCampoValido() throws IOException, WalaException,
     CancelException, ParseException, InvalidClassFileException {
   String strCompUnit = EXAMPLES_SRC + SEP + "Biblioteca_2" + SEP + "negocios"
       + SEP + "ManipularLivro.java";
   String manuip = EXAMPLES_JAR + SEP + "biblioteca.jar";
   Assert.assertTrue((new File(strCompUnit)).exists());
   Assert.assertTrue((new File(manuip)).exists());
   String line = "if (!campoValido(assunto)) {";
   SimpleGraph sg = depend.Main.analyze(manuip, "negocios", strCompUnit,
       line);
   System.out.println(sg.toDotString());
   String expectedResultFile = TEST_DIR + SEP
       + "rwsets/manuip/ManuIpTest.testCampoValido.data";
   
   Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
   
 }
 
}
