package org.adscale.testtodoc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Copyright 2012 Andrew Oxenburgh
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Main_Test extends Main {

    @Test
    public void usage() throws Exception {
        main(new String[]{});
        String expected = "usage TestToDoc: jarFileName" + LN_SEP + " -b : break between classes";
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
