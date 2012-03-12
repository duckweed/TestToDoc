package org.adscale.testtodoc;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestToDoc_Test extends TestToDoc {
    public static final String DEMO_JAR = "../TestToDoc-TestJar/target/TestToDoc-TestJar-1.0-SNAPSHOT.jar";
    static final String DEMO_DIR = "target/test-classes";


    @Test
    public void outputTestsNicely() {
        List<String> strings = outputTests(DEMO_JAR);
        assertEquals(1, strings.size());
        assertEquals("a test we want to view", strings.get(0));
    }


    @Test
    public void readJarFile() {
        assertTrue("precond: demoFile should exist", new File(DEMO_JAR).exists());
        List<String> methods = calcMethods(DEMO_JAR);
        assertEquals(1, methods.size());
        assertEquals("ATestWeWantToView", methods.get(0));
    }

    @Test
    public void jarEntryIntoClass() {
        assertJarEntryConvertedIntoClassName("org.adscale.testtodoc.testjar.AppTest", "org/adscale/testtodoc/testjar/AppTest.class");
        assertJarEntryConvertedIntoClassName("AppTest", "AppTest.class");
    }

    @Test
    public void readDirectory() throws Exception {
        File dirName = new File(DEMO_DIR);
        List<String> tests = new ArrayList<String>();
        assertTrue("precond: demoFile should exist", dirName.exists());
        handleDir(DEMO_DIR, tests);
    }


    private void assertJarEntryConvertedIntoClassName(String expectedClassName, String jarEntry) {
        assertEquals(expectedClassName, massageToClassPath(jarEntry));
    }
}
