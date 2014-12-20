package rwsets.hardware;


import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import rwsets.Helper;

import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;

import depend.util.graph.SimpleGraph;

public class HardwareTest {
  String USER_DIR = System.getProperty("user.dir");
  String SEP = System.getProperty("file.separator");
  String EXAMPLES = USER_DIR + SEP + "example-apps";
  String TEST_DIR = USER_DIR + SEP + "src-tests";
  String EXAMPLES_SRC = EXAMPLES + SEP + "src";
  String EXAMPLES_JAR = EXAMPLES;
  String RESOURCES_DIR = USER_DIR + SEP + "dat";

  @Test
  public void testArquivoPrintln() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Hardware" + SEP + "hardware"
        + SEP + "Montador.java";
    String hardware = EXAMPLES_JAR + SEP + "hardware.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(hardware)).exists());
    String line = "io.println(\"END;\");";
    SimpleGraph sg = depend.Main.analyze(hardware, "hardware", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/hardware/Hardware.testArquivoPrintln.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());

  }

  @Test
  public void testGetBinario() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Hardware" + SEP + "hardware"
        + SEP + "Montador.java";
    String hardware = EXAMPLES_JAR + SEP + "hardware.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(hardware)).exists());
    String line = "opcode = R.OPCODE.get(i).binario;";
    SimpleGraph sg = depend.Main.analyze(hardware, "hardware", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/hardware/Hardware.testGetBinario.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());

  }
  @Test
  public void testImprimir() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Hardware" + SEP + "hardware"
        + SEP + "Montador.java";
    String hardware = EXAMPLES_JAR + SEP + "hardware.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(hardware)).exists());
    String line = "imprimir(formata_R(RS, RT, RD, shamt, opcode, funcao),assembly);";
    SimpleGraph sg = depend.Main.analyze(hardware, "hardware", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/hardware/Hardware.testImprimir.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());

  }
  
  @Test
  public void testOpcodeAdd() throws IOException, WalaException,
      CancelException, ParseException, InvalidClassFileException {
    String strCompUnit = EXAMPLES_SRC + SEP + "Hardware" + SEP + "hardware"
        + SEP + "TipoJ.java";
    String hardware = EXAMPLES_JAR + SEP + "hardware.jar";
    Assert.assertTrue((new File(strCompUnit)).exists());
    Assert.assertTrue((new File(hardware)).exists());
    String line = "OPCODE.add(new Estrutura(\"j\",\"000010\"));";
    SimpleGraph sg = depend.Main.analyze(hardware, "hardware", strCompUnit,
        line);
    System.out.println(sg.toDotString());
    String expectedResultFile = TEST_DIR + SEP
        + "rwsets/hardware/TipoJ.testOpcodeAdd.data";
    Assert.assertEquals(Helper.readFile(expectedResultFile), sg.toDotString());

  }
  

}
