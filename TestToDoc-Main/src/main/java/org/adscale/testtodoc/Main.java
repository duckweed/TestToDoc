package org.adscale.testtodoc;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            String filename = args[0];
            if (new File(filename).exists()) {
                run(filename);
                return;
            }
            System.out.println("file not found");
            return;
        }
        System.out.println("usage TestToDoc: jarFileName");
    }

    private static void run(String arg) {
        System.out.println(arg);
        List<String> methods = new TestToDoc().outputTests(arg);
        if (methods.isEmpty()) {
            System.out.println("no methods found");
        } else {
            for (String method : methods) {
                System.out.println(" - " + method);
            }
        }
    }
}