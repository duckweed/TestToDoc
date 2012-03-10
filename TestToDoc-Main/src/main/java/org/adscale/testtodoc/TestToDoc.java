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

/**
 * Hello world!
 */
public class TestToDoc {
    protected List<String> jarFile(String demoJar1) {
        ArrayList<String> res = new ArrayList<String>();
        try {
            JarFile jarFile = new JarFile(demoJar1);
            Enumeration<JarEntry> entries = jarFile.entries();
            URLClassLoader loader = createClassLoader(demoJar1);
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
                            res.add(method.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    protected String camelCaseWord(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ('_' == word.charAt(i)) {
                handleUnderscore(sb, word, i);
                continue;
            }
            char c = word.charAt(i);
            if (Character.isLowerCase(c)) {
                sb.append(c);
            } else {
                sb.append(" ");
                sb.append(Character.toLowerCase(c));
            }
        }

        String ret = sb.toString();
        if (ret.startsWith("test")) {
            ret = ret.substring(4, ret.length());
        }
        ret = ret.replaceAll("  ", " ");
        return ret.trim();
    }

    private void handleUnderscore(StringBuilder sb, String word, int offset) {
        int index = offset + 1;
        if(index >= word.length()) {
            sb.append(".");
            return;
        }
        if (Character.isLowerCase(word.charAt(index))) {
            sb.append(", ");
        }else{
            sb.append(". ");
        }
        return;
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

    protected String jarEntryIntoClass(String name) {
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
