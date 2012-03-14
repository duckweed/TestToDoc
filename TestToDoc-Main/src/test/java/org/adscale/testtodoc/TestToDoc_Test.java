package org.adscale.testtodoc;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class TestToDoc_Test extends TestToDoc {
    public static final String DEMO_JAR = "../TestToDoc-TestJar/target/TestToDoc-TestJar-1.0-SNAPSHOT.jar";
    static final String DEMO_DIR = "target/test-classes";


    @Test
    public void outputTestsNicely() {
        List<String> strings = outputTests(DEMO_JAR);
        assertEquals(1, strings.size());
        assertEquals("org.adscale.testtodoc.testjar.DemonstrateJarFileWalking_Test::a test we want to view", strings.get(0));
    }


    @Test
    public void readJarFile() {
        assertTrue("precond: demoFile should exist", new File(DEMO_JAR).exists());
        List<String> methods = calcMethods(DEMO_JAR);
        assertEquals(1, methods.size());
        assertEquals("org.adscale.testtodoc.testjar.DemonstrateJarFileWalking_Test::ATestWeWantToView", methods.get(0));
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
