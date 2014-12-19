package rwsets.projetoip;

import japa.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rwsets.Helper;

import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import depend.util.graph.SimpleGraph;

public class TestProjetoIP {
  
  private static final String USER_DIR = System.getProperty("user.dir");
  private static final String APPS_DIR = USER_DIR+"/example-apps";
  private static final String APPS_JAR_DIR = APPS_DIR;
  private static final String APPS_SRC_DIR = APPS_DIR+"/src";
  private static final String RESOURCES_DIR = USER_DIR+"/dat";
  private static final String TEST_DIR = USER_DIR+"/src-tests";
  private static final String JAR_FILE = APPS_JAR_DIR+"/projetoip.jar";
  private static final String EXCLUSION_FILE = RESOURCES_DIR+"/ExclusionAllJava.txt";
  private static final String EXCLUSION_FILE_FOR_CALLGRAPH = RESOURCES_DIR+"/ExclusionForCallGraph.txt";
  
  @Before
  public void setup(){
    Assert.assertTrue(new File(JAR_FILE).exists());
    Assert.assertTrue(new File(EXCLUSION_FILE).exists());
    Assert.assertTrue(new File(EXCLUSION_FILE_FOR_CALLGRAPH).exists());
  }
  
  /**
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException
   */
  @Test
  public void testPedidoGetPosicao() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException{
    
    String line = "if (pedidos[i].getCodigo() == pedido.getCodigo()) {";
    String compUnitFile = APPS_SRC_DIR+"/restaurante/RepositorioPedidosArray.java";
    String filter = "restaurante";
    
    Assert.assertTrue((new File(compUnitFile)).exists());
    Assert.assertTrue((new File(JAR_FILE)).exists());
    
    SimpleGraph sgTest0 = depend.Main.analyze(JAR_FILE, filter, compUnitFile,line);
    String expectedResultFile = TEST_DIR + "/rwsets/projetoip/TestProjetoIP.testPedidoGetPosicao.data";
    
    if(new File(expectedResultFile).createNewFile()){
      PrintWriter pw = new PrintWriter(new FileWriter(expectedResultFile));
      pw.print(sgTest0.toDotString());
      pw.close();
    }

    Assert.assertEquals(Helper.readFile(expectedResultFile),sgTest0.toDotString());

  }
  
  /**
   * Known issue: doesn't provide support for Java Interfaces.
   * @throws InvalidClassFileException 
   * @throws ParseException 
   * @throws CancelException 
   * @throws WalaException 
   * @throws IOException 
   */
  @Test
  public void testRemoverProduto() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException{
    
    String line = "public void remover(Produto produto)";
    String compUnitFile = APPS_SRC_DIR+"/restaurante/RepositorioProdutosArray.java";
    String filter = "restaurante";
    
    Assert.assertTrue((new File(compUnitFile)).exists());
    Assert.assertTrue((new File(JAR_FILE)).exists());
    
    SimpleGraph sgTest0 = depend.Main.analyze(JAR_FILE, filter, compUnitFile,line);
    String expectedResultFile = TEST_DIR + "/rwsets/projetoip/TestProjetoIP.testRemoverProduto.data";
    
    if(new File(expectedResultFile).createNewFile()){
      PrintWriter pw = new PrintWriter(new FileWriter(expectedResultFile));
      pw.print(sgTest0.toDotString());
      pw.close();
    }

    Assert.assertEquals(Helper.readFile(expectedResultFile),sgTest0.toDotString());
    
  }
  
  
  
}
