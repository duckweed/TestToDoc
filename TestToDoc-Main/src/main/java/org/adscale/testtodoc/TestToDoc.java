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

    protected List<String> handleTests(String jarName) {
        ArrayList<String> tests = new ArrayList<String>();
        try {
            File file = new File(jarName);
            if (file.isFile()) {
                JarFile jarFile = new JarFile(jarName);
                Enumeration<JarEntry> entries = jarFile.entries();
                URLClassLoader loader = createClassLoader(jarName);
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    handleEntry(tests, loader, jarEntry);
                }
            } else {
                System.out.println("handle dir");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tests;
    }

    private void handleEntry(ArrayList<String> res, URLClassLoader loader, JarEntry possibleClass) {
        String clazzPath = possibleClass.getName();
        boolean isClass = clazzPath.endsWith(".class");
        if (isClass) {
            String clazzName = massageToClassPath(clazzPath);
            Class<?> clazz = loadClass(loader, clazzName);
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

    private Class<?> loadClass(URLClassLoader loader, String clazzName) {
        try {
            return loader.loadClass(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    protected String massageToClassPath(String name) {
        String substring = name.substring(0, name.length() - 6);
        substring = substring.replaceAll("/", ".");
        return substring;
    }

    protected List<String> outputTests(String file) {
        List<String> methods = handleTests(file);
        List<String> strings = new ArrayList<String>();
        for (String method : methods) {
            strings.add(camelCaseWord(method));
        }
        return strings;
    }
}
