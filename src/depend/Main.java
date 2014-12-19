package depend;

import japa.parser.ParseException;

import java.io.IOException;
import java.util.Properties;

import com.ibm.wala.classLoader.IBytecodeMethod;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.impl.Everywhere;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.Selector;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.io.CommandLine;
import com.ibm.wala.util.warnings.Warnings;

import depend.util.Util;
import depend.util.graph.SimpleGraph;

public class Main {


  /**
   * entry point when running the analysis from the command-line
   * 
   * @param args currently hard-coded (modify appJar)
   * 
   * @throws IOException
   * @throws IllegalArgumentException
   * @throws WalaException
   * @throws CancelException
   * @throws InterruptedException 
   */
  public static void main(String[] args) throws IOException, IllegalArgumentException, WalaException, CancelException, InterruptedException {
    
    if (args == null || args.length == 0) {
      System.out.println("Please, inform inputs");
      System.exit(0);
    }

    MethodDependencyAnalysis mDepAn = createMDA(args);
    // find informed class    
    IClass clazz = findClass(mDepAn);
    //  find informed method
    IMethod method = findMethod(clazz);
    
    SimpleGraph depGraph = run(mDepAn, method);
    
    // dump results
    Util.dumpResults(depGraph);

  }

  public static IMethod findMethod(IClass clazz) {
    String strMethod = Util.getStringProperty("targetMethod");
    IMethod method = clazz.getMethod(Selector.make(strMethod));
    if (method == null) {
      throw new RuntimeException("Could not find method \"" + strMethod + "\" in " + clazz.getName());
    }
    return method;
  }

  public static IClass findClass(MethodDependencyAnalysis mDepAn) {
    String strClass = Util.getStringProperty("targetClass");
    IClass clazz = mDepAn.getCHA().lookupClass(TypeReference.findOrCreate(ClassLoaderReference.Application, strClass));
    if (clazz == null) {
      throw new RuntimeException("Could not find class \"" + strClass + "\"");
    }
    return clazz;
  }

  public static SimpleGraph run(MethodDependencyAnalysis mDepAn, IMethod method) throws IOException, WalaException, CancelException {
    // run the dependency analysis
    mDepAn.run();
    
    // decide whether or not will filter results based on lines
    // TODO: solution needs to consider control flow.  it 
    // is more elaborate than this. -Marcelo
    String strLine = Util.getStringProperty("targetLine");
    
    int line = -1;
    if (strLine != null && !strLine.isEmpty()) {
      line = Integer.valueOf(strLine);      
    } 
    
    // build dependency graph
    boolean forwardDependencies = Util.getBooleanProperty(Util.FORWARD_DEPENDENCIES_PROPERTY_NAME,
                                                          false);
    boolean withIndirects = Util.getBooleanProperty(Util.WITH_INDIRECTS_PROPERTY_NAME, false);
    return mDepAn.getDependenciesGraph(method, line, forwardDependencies, withIndirects);

  }

  private static MethodDependencyAnalysis createMDA(String[] args)
      throws IOException, WalaException, CancelException {
    // reading and saving command-line properties
    Properties p = CommandLine.parse(args);
    Util.setProperties(p);
    
    // clearing warnings from WALA
    Warnings.clear();
    
    // performing dependency analysis
    return new MethodDependencyAnalysis(p);
  }
  
  /************ programmatic interface **************/
  
  /**
   * Analyze method dependencies for an informed specific 
   * source line selected from the compilation unit.
   *   
   * This method generates a pdf file with the dependency
   * graph in the output directory  
   * 
   * 
   * @param jarFilePath
   * @param packageFilter
   * @param strCompUnit
   * @param targetLineContents
   * @throws IOException
   * @throws WalaException
   * @throws CancelException
   * @throws ParseException
   * @throws InvalidClassFileException 
   */
  public static SimpleGraph analyze(String jarFilePath, String packageFilter, String classFilePath, String classFileLine) throws IOException, WalaException, CancelException, ParseException, InvalidClassFileException {    
    // line number and class in WALA format 
    String[] lineAndClass = depend.util.parser.Util.getLineAndWALAClassName(classFileLine, classFilePath);
    
    int targetLineNumber = Integer.parseInt(lineAndClass[0]);
    
    String targetClass = lineAndClass[1].replace(".", "/");
    String userDir = System.getProperty("user.dir");
    String sep = System.getProperty("file.separator");
    String exclusionFile = userDir + sep + "dat" + sep + "ExclusionAllJava.txt";
    String exclusionFileForCallGraph = userDir + sep + "dat" + sep + "exclusionFileForCallGraph";
    String dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
    
    String[] args = new String[] {
        "-appJar=" + jarFilePath, 
        "-printWalaWarnings=" + false, 
        "-exclusionFile=" + exclusionFile, 
        "-exclusionFileForCallGraph=" + exclusionFileForCallGraph, 
        "-dotPath=" + dotPath, 
        "-appPrefix=" + packageFilter,
        "-listAppClasses=" + false, 
        "-listAllClasses=" + false, 
        "-listAppMethods=" + false, 
        "-genCallGraph=" + false, 
        "-measureTime=" + false, 
        "-reportType=" + "dot"
    };
    
    MethodDependencyAnalysis mda = createMDA(args);
    
    // find informed class
    IClass clazz = mda.getCHA().lookupClass(TypeReference.findOrCreate(ClassLoaderReference.Application, targetClass));
    
    if (clazz == null) {
      throw new RuntimeException("Could not find class \"" + targetClass + "\"");
    }
    
    // find informed method
    IMethod imethod = findMethod(mda, clazz, targetLineNumber);
    
    // run the analysis
    SimpleGraph graph = run(mda, imethod);
    
    return graph;
  }
  
  public static IMethod findMethod(MethodDependencyAnalysis mda, IClass clazz, int targetLine) throws InvalidClassFileException {
    IMethod result = null;
    for (IMethod iMethod : clazz.getDeclaredMethods()) {
      
      //TODO: please check this.  it doe not seem to work for Sanity.test0
      
      @SuppressWarnings("static-access")
      IR ir = mda.cache.getIRFactory().makeIR(iMethod, Everywhere.EVERYWHERE, mda.options.getSSAOptions());
      SSAInstruction[] instructions = ir.getInstructions();
      IBytecodeMethod ibm = (IBytecodeMethod) ir.getMethod();
      int lo = ibm.getLineNumber(ibm.getBytecodeIndex(0));
      int hi = ibm.getLineNumber(ibm.getBytecodeIndex(instructions.length-1));
      
      if (lo <= targetLine && hi >= targetLine) {
        result = iMethod;
        break;
      }
      
    }
    if (result == null) {
      throw new RuntimeException("method not found!");
    }
    
    return result;
  }



}
