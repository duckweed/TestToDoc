package org.adscale.testtodoc;

import org.junit.Test;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestToDoc {
    String demoJar = "../TestToDoc-TestJar/target/TestToDoc-TestJar-1.0-SNAPSHOT-tests.jar";

    @Test
    public void readJarFile() throws Exception {

        assertTrue("demoFile should exist", new File(demoJar).exists());
        JarFile jarFile = new JarFile(demoJar);
        Enumeration<JarEntry> entries = jarFile.entries();
        URLClassLoader loader = createClassLoader(demoJar);
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            boolean isClass = name.endsWith(".class");
            if (isClass) {
                String substring = jarEntryIntoClass(name);
                Class<?> aClass = loader.loadClass(substring);
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation : annotations) {
                        System.out.println("annotation = " + annotation);
                    }
                }
            }
        }
    }

    @Test
    public void jarEntryIntoClass() {
        assertJarEntryConvertedIntoClassName("org.adscale.testtodoc.testjar.AppTest", "org/adscale/testtodoc/testjar/AppTest.class");
        assertJarEntryConvertedIntoClassName("AppTest", "AppTest.class");
    }

    private URLClassLoader createClassLoader(String fileName) throws Exception {
        String urlPath = makeUrlPath(fileName);
        URL url = new URL(urlPath);
        return URLClassLoader.newInstance(new URL[]{url});
    }

    private String makeUrlPath(String fileName) {
        String path = new File(".").getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        return "file:///" + path + "/" + fileName;
    }

    private void assertJarEntryConvertedIntoClassName(String expectedClassName, String jarEntry) {
        assertEquals(expectedClassName, jarEntryIntoClass(jarEntry));
    }


    private String jarEntryIntoClass(String name) {
        String substring = name.substring(0, name.length() - 6);
        substring = substring.replaceAll("/", ".");
        return substring;
    }
}
