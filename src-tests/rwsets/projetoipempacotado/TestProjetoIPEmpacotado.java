package rwsets.projetoipempacotado;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rwsets.Helper.CLASS_NOT_FOUND;
import static rwsets.Helper.EXCLUSION_FILE;
import static rwsets.Helper.EXCLUSION_FILE_FOR_CALLGRAPH;
import static rwsets.Helper.USER_DIR;
import static rwsets.Helper.getExpectedResultsFilePath;
import static rwsets.Helper.readFile;
import japa.parser.ParseException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import depend.util.graph.SimpleGraph;

public class TestProjetoIPEmpacotado {
  private static final String APPS_DIR = USER_DIR + "/example-apps";
  private static final String APPS_JAR_DIR = APPS_DIR;
  private static final String APPS_SRC_DIR = APPS_DIR + "/src";
  private static final String JAR_FILE = APPS_JAR_DIR
      + "/projetoipempacotado.jar";
  private static final String PACKAGE_FILTER = "br/ufpe/cin";

  @Before
  public void setup() {
    assertTrue(new File(JAR_FILE).exists());
    assertTrue(new File(EXCLUSION_FILE).exists());
    assertTrue(new File(EXCLUSION_FILE_FOR_CALLGRAPH).exists());
  }

  private static SimpleGraph analyze(String classFilePath, String classFileLine)
      throws IOException, WalaException, CancelException, ParseException,
      InvalidClassFileException {
    return depend.Main.analyze(JAR_FILE, PACKAGE_FILTER, classFilePath,
        classFileLine);
  }

  /**
   * Known issue: breaks for subpackages - Fixed
   * 
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  @Test
  public void test1() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String classFileLine = "if (pedidos[i].getCodigo() == pedido.getCodigo()) {";
    String classFilePath = APPS_SRC_DIR
        + "/IP/br/ufpe/cin/dados/RepositorioPedidosArray.java";
    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());
    SimpleGraph graph = analyze(classFilePath, classFileLine);
    String expectedResultsFile = getExpectedResultsFilePath();
    PrintWriter fileWriter = new PrintWriter(
        new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();
    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }

  @Test
  public void test2() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String classFileLine = "if (this.procurar(pedido.getCodigo()) == null) {";
    String classFilePath = APPS_SRC_DIR
        + "/IP/br/ufpe/cin/dados/RepositorioPedidosArray.java";
    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());
    SimpleGraph graph = analyze(classFilePath, classFileLine);
    String expectedResultsFile = getExpectedResultsFilePath();
    PrintWriter fileWriter = new PrintWriter(
        new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();
    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }

  @Test
  public void test3() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String classFileLine = "Pedido retorno = null;";
    String classFilePath = APPS_SRC_DIR
        + "/IP/br/ufpe/cin/dados/RepositorioPedidosArray.java";
    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());
    SimpleGraph graph = analyze(classFilePath, classFileLine);
    String expectedResultsFile = getExpectedResultsFilePath();
    PrintWriter fileWriter = new PrintWriter(
        new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();
    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }

  @Test
  public void test4() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String classFileLine = "if (pedidos[i].getCodigo().equals(codigo)) {";
    String classFilePath = APPS_SRC_DIR
        + "/IP/br/ufpe/cin/dados/RepositorioPedidosArray.java";
    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());
    SimpleGraph graph = analyze(classFilePath, classFileLine);
    String expectedResultsFile = getExpectedResultsFilePath();
    PrintWriter fileWriter = new PrintWriter(
        new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();
    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }

  @Test
  /**
   * Known issue: doesn't provide support for 'implements' clause
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  public void test5() throws IOException, WalaException, CancelException,
      ParseException, InvalidClassFileException {
    String classFileLine = "if (produtos[i].getCodigo() == produto.getCodigo()) {";
    String classFilePath = APPS_SRC_DIR
        + "/IP/br/ufpe/cin/dados/RepositorioProdutosArray.java";
    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());
    SimpleGraph graph = analyze(classFilePath, classFileLine);
    String expectedResultsFile = getExpectedResultsFilePath();
    PrintWriter fileWriter = new PrintWriter(
        new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();
    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }
  
}