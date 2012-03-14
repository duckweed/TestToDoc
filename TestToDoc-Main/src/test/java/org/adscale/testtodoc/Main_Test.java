package org.adscale.testtodoc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class Main_Test extends Main {

    @Test
    public void usage() throws Exception {
        main(new String[]{});
        String expected = "usage TestToDoc: jarFileName".trim();
        assertSystemOut(expected);
    }

    @Test
    public void withNonExistentFile_outputErrorMessage() throws Exception {
        main(new String[]{"nonExistentFile"});
        assertSystemOut("file not found");
    }

    @Test
    public void withJarFile_outputText_singleLinePerTest() throws Exception {
        main(new String[]{TestToDoc_Test.DEMO_JAR});
        assertSystemOut("- org.adscale.testtodoc.testjar.DemonstrateJarFileWalking_Test::a test we want to view");
    }


    @Test
    public void withJarFile_outputText_breakBetweenTestClasses() throws Exception {
        main(new String[]{TestToDoc_Test.DEMO_JAR, "-b"});
        assertSystemOut("org.adscale.testtodoc.testjar.DemonstrateJarFileWalking_Test" + LN_SEP + " - a test we want to view");
    }

    @Test
    public void withKnownDir_outputText() throws Exception {
        main(new String[]{TestToDoc_Test.DEMO_DIR});
        System.out.println("os.toString() = " + os.toString());
    }

    @Test
    public void demoCaptureSystemOutput() {
        System.out.print("hello");
        assertEquals("hello", os.toString());
    }


    PrintStream out;
    PrintStream err;
    ByteArrayOutputStream os;

    static String LN_SEP = System.getProperty("line.separator");

    @After
    public void after() {
        System.setOut(out);
        System.setErr(err);
    }

    @Before
    public void before() {
        out = System.out;
        err = System.err;

        os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        System.setOut(ps);
        System.setErr(ps);
    }

    private void assertSystemOut(String expected) {
        assertEquals(expected, os.toString().trim());
    }
}
