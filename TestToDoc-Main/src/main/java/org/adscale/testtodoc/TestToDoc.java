package org.adscale.testtodoc;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.adscale.testtodoc.CamelCaser.camelCaseWord;

public class TestToDoc {

    protected List<String> jarFile(String jarName) {
        ArrayList<String> res = new ArrayList<String>();
        try {
            JarFile jarFile = new JarFile(jarName);
            Enumeration<JarEntry> entries = jarFile.entries();
            URLClassLoader loader = createClassLoader(jarName);
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                boolean isClass = entryName.endsWith(".class");
                if (isClass) {
                    String clazzName = jarEntryIntoClassName(entryName);
                    Class<?> clazz = loader.loadClass(clazzName);
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        Annotation[] annotations = method.getDeclaredAnnotations();
                        for (Annotation annotation : annotations) {
                            if (annotation.annotationType().getName().endsWith(".Test")) {
                                res.add(method.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
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

    protected String jarEntryIntoClassName(String name) {
        String substring = name.substring(0, name.length() - 6);
        substring = substring.replaceAll("/", ".");
        return substring;
    }

    protected List<String> outputTests(String file) {
        List<String> methods = jarFile(file);
        List<String> strings = new ArrayList<String>();
        for (String method : methods) {
            strings.add(camelCaseWord(method));
        }
        return strings;
    }
}
