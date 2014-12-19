package rwsets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Helper {
  public static final String USER_DIR = System.getProperty("user.dir");
  public static final String RESOURCES_DIR = USER_DIR + "/dat";
  public static final String TEST_DIR = USER_DIR + "/src-tests";
  public static final String EXCLUSION_FILE = RESOURCES_DIR + "/ExclusionAllJava.txt";
  public static final String EXCLUSION_FILE_FOR_CALLGRAPH = RESOURCES_DIR + "/ExclusionForCallGraph.txt";
  public static final String CLASS_NOT_FOUND = "A classe não foi encontrada";

  public static String readFile(String fileName) throws FileNotFoundException, IOException {
    StringBuffer sb = new StringBuffer();
    
    FileReader fr = new FileReader(new File(fileName));
    
    BufferedReader br = new BufferedReader(fr);
    
    String tmp;
    
    while ((tmp = br.readLine()) != null) {
      sb.append(tmp);
      sb.append("\n");
    }
    
    br.close();
    
    fr.close();
    
    return sb.toString();
  }
  
  private static String getCurrentTestName() {
    Thread currentThread = Thread.currentThread();
    
    StackTraceElement[] stackTrace = currentThread.getStackTrace();
    
    String testName = stackTrace[3].getClassName().replace(".", "/") + "." + stackTrace[3].getMethodName();
    
    return testName;
  }
  
  public static String getExpectedResultsFilePath() {
    return String.format("%s/%s.data", TEST_DIR, getCurrentTestName());
  }
}