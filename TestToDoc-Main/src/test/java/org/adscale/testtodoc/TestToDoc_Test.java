package org.adscale.testtodoc;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestToDoc_Test extends TestToDoc {
    public static final String demoJar = "../TestToDoc-TestJar/target/TestToDoc-TestJar-1.0-SNAPSHOT.jar";

    @Test
    public void outputTestsNicely() {
        List<String> strings = outputTests(demoJar);
        assertEquals(1, strings.size());
        assertEquals("a test we want to view", strings.get(0));
    }


    @Test
    public void readJarFile() {
        assertTrue("demoFile should exist", new File(demoJar).exists());
        List<String> methods = jarFile(demoJar);
        assertEquals(1, methods.size());
        assertEquals("ATestWeWantToView", methods.get(0));
    }

    @Test
    public void jarEntryIntoClass() {
        assertJarEntryConvertedIntoClassName("org.adscale.testtodoc.testjar.AppTest", "org/adscale/testtodoc/testjar/AppTest.class");
        assertJarEntryConvertedIntoClassName("AppTest", "AppTest.class");
    }

    private void assertJarEntryConvertedIntoClassName(String expectedClassName, String jarEntry) {
        assertEquals(expectedClassName, jarEntryIntoClassName(jarEntry));
    }
}
