package org.adscale.testtodoc;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.adscale.testtodoc.CamelCaser.camelCaseWord;

public class TestToDoc {

    protected List<String> calcMethods(String jarName) {
        ArrayList<String> tests = new ArrayList<String>();
        try {
            File file = new File(jarName);
            if (file.isFile()) {
                handleJar(jarName, tests);
            } else {
                handleDir(jarName, tests);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tests;
    }

    void handleJar(String jarName, List<String> tests) throws Exception {
        JarFile jarFile = new JarFile(jarName);
        Enumeration<JarEntry> entries = jarFile.entries();
        URLClassLoader loader = createClassLoader(jarName);
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            handleEntry(tests, loader, jarEntry);
        }
    }

    private void handleEntry(List<String> res, URLClassLoader loader, JarEntry possibleClass) {
        String clazzPath = possibleClass.getName();
        boolean isClass = clazzPath.endsWith(".class");
        if (isClass) {
            String clazzName = massageToClassPath(clazzPath);
            Class<?> clazz = loadClass(loader, clazzName);
            handleClass(res, clazz);
        }
    }

    void handleClass(List<String> res, Class<?> clazz) {
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

    Class<?> loadClass(URLClassLoader loader, String clazzName) {
        try {
            return loader.loadClass(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can't find '" + clazzName + "' in loader '" + loader + "'", e);
        }
    }

    URLClassLoader createClassLoader(String fileName) throws Exception {
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
        substring = substring.replace("\\", ".");
        return substring;
    }

    protected List<String> outputTests(String file) {
        List<String> methods = calcMethods(file);
        List<String> camelCased = camelCaseList(methods);
        return camelCased;
    }

    private List<String> camelCaseList(List<String> methods) {
        List<String> strings = new ArrayList<String>();
        for (String method : methods) {
            strings.add(camelCaseWord(method));
        }
        return strings;
    }

    protected void handleDir(String dirName, List<String> tests) throws Exception {
        File dir = new File(dirName);
        Iterator<File> iterator = FileUtils.iterateFiles(dir, new String[]{"class"}, true);

        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.isFile()) {
                String clazzPath = file.getCanonicalPath().substring(dir.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
                boolean isClass = clazzPath.endsWith(".class");
                if (isClass) {
                    String clazzName = massageToClassPath(clazzPath);
                    URLClassLoader classLoader = createClassLoader(dirName);

                    System.out.println("created class loader with '" + dirName + "'");

                    Class<?> clazz = loadClass(classLoader, clazzName);
                    handleClass(tests, clazz);
                }
            } else {
                System.out.println("handle dir");
            }
        }
    }
}
