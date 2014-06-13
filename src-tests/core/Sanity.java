package core;

import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

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
  
  @Test
  public void testBasicDoesNotCrash() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {

    String strCompUnit = USER_DIR + SEP + "src-examples/foo/D.java";
    
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "System.out.println(\"hello\");";    

    // check for crashes
    depend.Main.analyze("foo.jar", "foo", strCompUnit, line);
  }
  
  @Test
  public void testIR_isNotEmpty() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {

    String strCompUnit = USER_DIR + SEP + "src-examples/foo/D.java";
    
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "System.out.println(\"hello\");";
    // line number and class in WALA format 
    String[] lineAndClass = 
        depend.util.parser.Util.getLineAndWALAClassName(line+"", strCompUnit);
    int targetLine = Integer.parseInt(lineAndClass[0]);
    String targetClass = lineAndClass[1];    
    
    String USER_DIR1 = System.getProperty("user.dir");
    String SEP1 = System.getProperty("file.separator");
    
    // default values
    String exclusionFile = USER_DIR1 + SEP1 + "dat" + SEP1 + "ExclusionAllJava.txt";
    String exclusionFileForCallGraph = USER_DIR1 + SEP1 + "dat" + SEP1 + "exclusionFileForCallGraph";
    String dotPath = "/usr/bin/dot";
    
    String[] args = new String[] {
        "-appJar="+"foo.jar", 
        "-printWalaWarnings="+false, 
        "-exclusionFile="+exclusionFile, 
        "-exclusionFileForCallGraph="+exclusionFileForCallGraph, 
        "-dotPath="+dotPath, 
        "-appPrefix="+"foo",
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

    String expected = "< Application, Lfoo/D$E, k(Ljava/lang/String;)V >";
    
    // check
    Assert.assertEquals(expected, imethod.toString());

  }
  
  @Test
  public void testPrimitiveTypeDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {

    String strCompUnit = USER_DIR + SEP + "src-examples/foo/B.java";
    
    // check
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "a.y + a.z > c.y + w";    

    SimpleGraph sg = depend.Main.analyze("foo.jar", "foo", strCompUnit, line);
        
    String expectedResultFile = USER_DIR + SEP + "src-tests/core/Sanity.testPrimitiveTypeDependency.data";

    String expected = Helper.readFile(expectedResultFile);

    // check
    Assert.assertEquals(expected, sg.toDotString());
  }
  
  @Test
  public void testArrayDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    
    String strCompUnit = USER_DIR + SEP + "src-examples/foo/FooArray.java";
    
    // check
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "t[1] = t[1] + \"!\";";    

    SimpleGraph sg = depend.Main.analyze("foo.jar", "foo", strCompUnit, line);
        
    String expectedResultFile = USER_DIR + SEP + "src-tests/core/Sanity.testArrayDependency.data";

    String expected = Helper.readFile(expectedResultFile);
    
    // check
    Assert.assertEquals(expected, sg.toDotString());
    
  }
  
  @Test
  public void testReferenceDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    
    String strCompUnit = USER_DIR + SEP + "src-examples/foo/FooReference.java";
    
    // check
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "System.out.println(t);";    

    SimpleGraph sg = depend.Main.analyze("foo.jar", "foo", strCompUnit, line);
        
    String expectedResultFile = USER_DIR + SEP + "src-tests/core/Sanity.testReferenceDependency.data";

    String expected = Helper.readFile(expectedResultFile);
    
    // check
    Assert.assertEquals(expected, sg.toDotString());
    
  }
  
  
  @Test
  public void testCollectionsDependency() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {
    
    String strCompUnit = USER_DIR + SEP + "src-examples/foo/FooCollections.java";
    
    // check
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String line = "(t.size())";    

    SimpleGraph sg = depend.Main.analyze("foo.jar", "foo", strCompUnit, line);
        
    String expectedResultFile = USER_DIR + SEP + "src-tests/core/Sanity.testCollectionsDependency.data";

    String expected = Helper.readFile(expectedResultFile);
    
    // check
    Assert.assertEquals(expected, sg.toDotString());
    
  }

  @Test
  public void testFlowSensitivity() throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException{
    String strCompUnit = USER_DIR + SEP + "src-examples/foo/FooFlowSensitivity.java";
        
    String USER_DIR1 = System.getProperty("user.dir");
    String SEP1 = System.getProperty("file.separator");
    // default values
    String exclusionFile = USER_DIR1 + SEP1 + "dat" + SEP1
        + "ExclusionAllJava.txt";
    String exclusionFileForCallGraph = USER_DIR1 + SEP1 + "dat" + SEP1
        + "exclusionFileForCallGraph";
    String dotPath = "/usr/bin/dot";
    
    Assert.assertTrue((new File(strCompUnit)).exists());
    
    String targetClass = "Lfoo/FooFlowSensitivity";
    String targetMethod = "read()V";
    
    
    String[] args = new String[] { "-appJar=" + "foo.jar",
        "-printWalaWarnings=" + false, "-exclusionFile=" + exclusionFile,
        "-exclusionFileForCallGraph=" + exclusionFileForCallGraph,
        "-dotPath=" + dotPath, "-appPrefix=" + "foo",
        "-listAppClasses=" + false, "-listAllClasses=" + false,
        "-listAppMethods=" + false, "-genCallGraph=" + false,
        "-measureTime=" + false, "-reportType=" + "dot",
        "-targetClass=" + targetClass, "-targetMethod=" + targetMethod,
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
    
    String expectedResultFile = USER_DIR + SEP + "src-tests/core/Sanity.testFlowSensitivity.data";
    
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());
  }
}
