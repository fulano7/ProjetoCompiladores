package rwsets.projetopg;

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

public class Test_PG_Projeto_2_Jar {
  private static final String APPS_DIR = USER_DIR + "/example-apps";
  private static final String APPS_JAR_DIR = APPS_DIR;
  private static final String APPS_SRC_DIR = APPS_DIR + "/src";
  private static final String JAR_FILE = APPS_JAR_DIR + "/PG_Projeto_2.jar";
  private static final String PACKAGE_FILTER = "pgprojeto2";

  @Before
  public void setup() {
    assertTrue(new File(JAR_FILE).exists());
    assertTrue(new File(EXCLUSION_FILE).exists());
    assertTrue(new File(EXCLUSION_FILE_FOR_CALLGRAPH).exists());
  }

  private static SimpleGraph analyze(String classFilePath, String classFileLine) {
    try {
      return depend.Main.analyze(JAR_FILE, PACKAGE_FILTER, classFilePath, classFileLine);
    } catch (IOException | WalaException | CancelException | ParseException | InvalidClassFileException e) {
      return null;
    }
  }

  /**
   * Unexpected result: can't find class 'Core'
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  @Test
  public void test1() throws IOException {
    String classFileLine = "luz = leitura_luz(iluninacaoParam);";
    String classFilePath = APPS_SRC_DIR + "/PG_Projeto_2/pgprojeto2/Core.java";

    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());

    SimpleGraph graph = analyze(classFilePath, classFileLine);

    String expectedResultsFile = getExpectedResultsFilePath();

    PrintWriter fileWriter = new PrintWriter(new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();

    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }
  
  /**
   * Unexpected result: can't find class 'Superficie'
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  @Test
  public void test2() throws IOException {
    String classFileLine = "int resY = box.imagem[fzInt-1].getHeight(); int resX = box.imagem[fzInt-1].getWidth();";
    String classFilePath = APPS_SRC_DIR + "/PG_Projeto_2/pgprojeto2/Superficie.java";

    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());

    SimpleGraph graph = analyze(classFilePath, classFileLine);

    String expectedResultsFile = getExpectedResultsFilePath();

    PrintWriter fileWriter = new PrintWriter(new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();

    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }
  
  /**
   * Unexpected result: throws null pointer exception
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  @Test
  public void test3() throws IOException {
    String classFileLine = "this.C = C;";
    String classFilePath = APPS_SRC_DIR + "/PG_Projeto_2/pgprojeto2/Camera.java";

    assertTrue(CLASS_NOT_FOUND, new File(classFilePath).exists());

    SimpleGraph graph = analyze(classFilePath, classFileLine);

    String expectedResultsFile = getExpectedResultsFilePath();

    PrintWriter fileWriter = new PrintWriter(new FileWriter(expectedResultsFile));
    fileWriter.print(graph.toDotString());
    fileWriter.close();

    assertTrue(new File(expectedResultsFile).exists());
    assertEquals(readFile(expectedResultsFile), graph.toDotString());
  }
  
}
