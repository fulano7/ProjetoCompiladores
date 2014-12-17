package rwsets.projetopg;

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
import depend.util.Util;
import depend.util.graph.SimpleGraph;

public class Test_PG_Projeto_2_Jar {
  
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String EXAMPLES_SRC = EXAMPLES +  SEP + "src";
  String EXAMPLES_JAR = EXAMPLES; 
  String RESOURCES_DIR = USER_DIR + SEP + "dat";

  @Test
  public void test0() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {

    String strCompUnit = EXAMPLES_SRC + SEP + "PG_Projeto_2/src/Core.java"; //obs com src
    String PG_Projeto_2_Jar = EXAMPLES_JAR + SEP + "PG_Projeto_2.jar";
    
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(PG_Projeto_2_Jar)).exists());
    
    String line = "if(addRecipe(newRecipe)) {";
    SimpleGraph sg = depend.Main.analyze(PG_Projeto_2_Jar, "PG", strCompUnit, line);
        
    String expectedResultFile = TEST_DIR + SEP + "rwsets/PG_Projeto_2/Test_PG_Projeto_2.test0.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }

  @Test
  public void testAnalysisWithLineContents() throws Exception {
    String strCompUnit = EXAMPLES_SRC + SEP + "PG_Projeto_2/src/Core.java";
    String exclusionFile = RESOURCES_DIR + SEP + "ExclusionAllJava.txt";
    String exclusionFileForCallGraph = RESOURCES_DIR + SEP + "ExclusionForCallGraph.txt";
    String PG_Projeto_2_Jar = EXAMPLES_JAR + SEP + "PG_Projeto_2.jar";
    String targetClass = "LPG_Projeto_2/Core";
    String targetMethod = "editRecipe(LPG_Projeto_2/Recipe;LPG_Projeto_2/Recipe;)Z";

    // checks
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(exclusionFile)).exists());
    Assert.assertTrue((new File(exclusionFileForCallGraph)).exists());
    Assert.assertTrue((new File(PG_Projeto_2_Jar)).exists());


    String[] args = new String[] { 
        "-appJar=" + PG_Projeto_2_Jar,
        "-printWalaWarnings=" + false, 
        "-exclusionFile=" + exclusionFile,
        "-exclusionFileForCallGraph=" + exclusionFileForCallGraph,
        "-dotPath=" + "/usr/bin/dot", 
        "-appPrefix=" + "PG",
        "-listAppClasses=" + false, 
        "-listAllClasses=" + false,
        "-listAppMethods=" + false, 
        "-genCallGraph=" + false,
        "-measureTime=" + false, 
        "-reportType=" + "list",
        "-targetClass=" + targetClass, 
        "-targetMethod=" + targetMethod,
        "-targetLine=99"};
    
    // reading and saving command-line properties
    Properties p = CommandLine.parse(args);
    Util.setProperties(p);

    // clearing warnings from WALA
    Warnings.clear();

    MethodDependencyAnalysis mda = new MethodDependencyAnalysis(p);

    // find informed class    
    IClass clazz = depend.Main.findClass(mda);
    //  find informed method
    IMethod method = depend.Main.findMethod(clazz);
    SimpleGraph sg = depend.Main.run(mda, method);
    
    String expectedResultFile = TEST_DIR + SEP + "rwsets/PG_Projeto_2/Test_PG_Projeto_2.testAnalysisWithLineContents.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toString());
  } 
}