package rwsets.core;

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

public class Sanity {
  
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String EXAMPLES_SRC = EXAMPLES +  SEP + "src";
  String EXAMPLES_JAR = EXAMPLES; 
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String FOO_JAR = EXAMPLES_JAR + SEP + "foo.jar";
  String RESOURCES_DIR = USER_DIR + SEP + "dat";
  String EXCLUSION_FILE = RESOURCES_DIR + SEP + "ExclusionAllJava.txt";
  String EXCLUSION_FILE_FOR_CALLGRAPH = RESOURCES_DIR + SEP + "ExclusionForCallGraph.txt";
  
  @Before
  public void setup() {
    Assert.assertTrue((new File(EXCLUSION_FILE)).exists());
    Assert.assertTrue((new File(EXCLUSION_FILE_FOR_CALLGRAPH)).exists());
    Assert.assertTrue((new File(FOO_JAR)).exists());
  }
  
  
  @Test
  public void testBasicDoesNotCrash() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "foo/D.java";
    Assert.assertTrue((new File(strCompUnit)).exists());
    String line = "System.out.println(\"hello\");";    
    // check for crashes
    depend.Main.analyze(FOO_JAR, "foo", strCompUnit, line);
  }
  
  @Test
  public void testIR_isNotEmpty() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "foo/D.java";
    // checks
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "System.out.println(\"hello\");";

    // line number and class in WALA format 
    String[] lineAndClass = 
        depend.util.parser.Util.getLineAndWALAClassName(line+"", strCompUnit);
    int targetLine = Integer.parseInt(lineAndClass[0]);
    String targetClass = lineAndClass[1];    

    String dotPath = "/usr/bin/dot";
    
    String[] args = new String[] {
        "-appJar=" + FOO_JAR, 
        "-printWalaWarnings=" + false, 
        "-exclusionFile=" + EXCLUSION_FILE, 
        "-exclusionFileForCallGraph=" +EXCLUSION_FILE_FOR_CALLGRAPH, 
        "-dotPath=" + dotPath, 
        "-appPrefix=" + "foo",
        "-listAppClasses="+false, 
        "-listAllClasses="+false, 
        "-listAppMethods="+false, 
        "-genCallGraph="+false, 
        "-measureTime="+false, 
        "-reportType="+"dot"
    };
    // reading and saving command-line properties
    Properties p = CommandLine.parse(args);
    Util.setProperties(p);
    
    // clearing warnings from WALA
    Warnings.clear();
    
    MethodDependencyAnalysis mda = new MethodDependencyAnalysis(p);
    
    // find informed class
    String strClass = Util.getStringProperty("targetClass");
    IClass clazz = mda.getCHA().lookupClass(TypeReference.findOrCreate(ClassLoaderReference.Application, targetClass));
    if (clazz == null) {
      throw new RuntimeException("Could not find class \"" + strClass + "\"");
    }
    // find informed method
    IMethod imethod = depend.Main.findMethod(mda, clazz, targetLine);    

    // check
    String expected = "< Application, Lfoo/D$E, k(Ljava/lang/String;)V >";
    Assert.assertEquals(expected, imethod.toString());

  }
  
  @Test
  public void testPrimitiveTypeDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {

    String strCompUnit = EXAMPLES_SRC + SEP + "foo/B.java";
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "a.y + a.z > c.y + w";    
    SimpleGraph sg = depend.Main.analyze(FOO_JAR, "foo", strCompUnit, line);

    // check
    String expectedResultFile = TEST_DIR + "/rwsets/core/Sanity.testPrimitiveTypeDependency.data";
    String expected = Helper.readFile(expectedResultFile);
    Assert.assertEquals(expected, sg.toDotString());
  }
  
  @Test
  public void testArrayDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    
    String strCompUnit = EXAMPLES_SRC + SEP + "foo/FooArray.java";
    
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "t[1] = t[1] + \"!\";";    
    SimpleGraph sg = depend.Main.analyze(FOO_JAR, "foo", strCompUnit, line);

    // check
    String expectedResultFile = TEST_DIR + "/rwsets/core/Sanity.testArrayDependency.data";
    String expected = Helper.readFile(expectedResultFile);
    Assert.assertEquals(expected, sg.toDotString());
    
  }
  
  @Test
  public void testReferenceDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {

    String strCompUnit = EXAMPLES_SRC + SEP + "foo/FooReference.java";
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "System.out.println(t);";    
    SimpleGraph sg = depend.Main.analyze(FOO_JAR, "foo", strCompUnit, line);

    // check
    String expectedResultFile = TEST_DIR + "/rwsets/core/Sanity.testReferenceDependency.data";
    String expected = Helper.readFile(expectedResultFile);
    Assert.assertEquals(expected, sg.toDotString());
    
  }
  
  @Test
  public void testCollectionsDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    
    String strCompUnit = EXAMPLES_SRC + SEP + "foo/FooCollections.java";
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "(t.size())";    
    SimpleGraph sg = depend.Main.analyze(FOO_JAR, "foo", strCompUnit, line);
    
    // check
    //TODO: Paulo and Mateus, please check if this is correct. -Marcelo
    String expectedResultFile = TEST_DIR + "/rwsets/core/Sanity.testCollectionsDependency.data";
    String expected = Helper.readFile(expectedResultFile);
    Assert.assertEquals(expected, sg.toDotString());
    
  }

  @Test
  public void testFlowSensitivity() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException{
    
    String strCompUnit = EXAMPLES_SRC + SEP + "foo/FooFlowSensitivity.java";
    String dotPath = "/usr/bin/dot";
    
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String targetClass = "Lfoo/FooFlowSensitivity";
    String targetMethod = "read()V";
    
    String[] args = new String[] { 
        "-appJar=" + FOO_JAR,
        "-printWalaWarnings=" + false, 
        "-exclusionFile=" + EXCLUSION_FILE,
        "-exclusionFileForCallGraph=" + EXCLUSION_FILE_FOR_CALLGRAPH,
        "-dotPath=" + dotPath, 
        "-appPrefix=" + "foo",
        "-listAppClasses=" + false, 
        "-listAllClasses=" + false,
        "-listAppMethods=" + false, 
        "-genCallGraph=" + false,
        "-measureTime=" + false, 
        "-reportType=" + "dot",
        "-targetClass=" + targetClass, 
        "-targetMethod=" + targetMethod,
        "-targetLine=13"};
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
    
    String expectedResultFile = TEST_DIR + SEP + "rwsets/core/Sanity.testFlowSensitivity.data";
    
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }
}