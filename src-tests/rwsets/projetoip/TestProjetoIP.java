package rwsets.projetoip;

import japa.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rwsets.Helper;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.io.CommandLine;
import com.ibm.wala.util.warnings.Warnings;

import depend.MethodDependencyAnalysis;
import depend.util.Util;
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
  
  @Test
  public void test0(){
    
    String line = "if (pedidos[i].getCodigo() == pedido.getCodigo()) {";
    String compUnitFile = APPS_SRC_DIR+"/Projeto_IP/br/ufpe/cin/dados/RepositorioPedidosArray.java";
    String filter = "br.ufpe.cin";
    
    Assert.assertTrue((new File(compUnitFile)).exists());
    Assert.assertTrue((new File(JAR_FILE)).exists());
    
    try{
      
      SimpleGraph sgTest0 = depend.Main.analyze(JAR_FILE, filter, compUnitFile, line);
      String expectedResultFile = TEST_DIR + "/rwsets/coffeemaker/TestCoffeeMaker.test0.data";
      
      Assert.assertEquals(Helper.readFile(expectedResultFile), sgTest0.toDotString());
      
    } catch (Exception e){
      e.printStackTrace();
    }
  }
  
  
  
}
