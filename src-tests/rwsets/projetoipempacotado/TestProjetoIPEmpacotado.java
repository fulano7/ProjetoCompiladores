package rwsets.projetoipempacotado;

import static depend.Main.analyze;
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
  private static final String JAR_FILE = APPS_JAR_DIR + "/projetoipempacotado.jar";
  
  @Before
  public void setup() {
    assertTrue(new File(JAR_FILE).exists());
    assertTrue(new File(EXCLUSION_FILE).exists());
    assertTrue(new File(EXCLUSION_FILE_FOR_CALLGRAPH).exists());
  }

  /**
   * Known issue: breaks for subpackages - Fixed
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  @Test
  public void test0() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    String classFileLine = "if (pedidos[i].getCodigo() == pedido.getCodigo()) {";
    String classFilePath = APPS_SRC_DIR + "/br/ufpe/cin/dados/RepositorioPedidosArray.java";
    String packageFilter = "br/ufpe/cin";

    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());

    SimpleGraph graph = analyze(JAR_FILE, packageFilter, classFilePath, classFileLine);
    
    String expectedResultsFile = getExpectedResultsFilePath();
    
    if (new File(expectedResultsFile).createNewFile()) {
      PrintWriter fileWriter = new PrintWriter(new FileWriter(expectedResultsFile));
      fileWriter.print(graph.toDotString());
      fileWriter.close();
    }

    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }
}