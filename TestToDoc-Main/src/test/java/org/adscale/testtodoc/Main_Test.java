package org.adscale.testtodoc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class Main_Test extends Main {

    @Test
    public void usage() {
        main(new String[]{});
        String expected = "usage TestToDoc: jarFileName".trim();
        assertSystemOut(expected);
    }

    @Test
    public void withUnknownFile_outputErrorMessage() {
        main(new String[]{"nonExistentFile"});
        assertSystemOut("file not found");
    }

    @Test
    public void withKnownFile_outputText() {
        main(new String[]{TestToDoc_Test.DEMO_JAR});
        String ls = System.getProperty("line.separator");
        assertSystemOut("- a test we want to view");
    }

    @Test
    public void withKnownDir_outputText() {
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
