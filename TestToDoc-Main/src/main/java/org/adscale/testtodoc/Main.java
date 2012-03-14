package org.adscale.testtodoc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.File;
import java.util.List;

public class Main {

    static CommandLine cmd;

    public static void main(String[] args) throws Exception {
        parseArgs(args);
        String[] files = cmd.getArgs();
        if (files.length == 1) {
            String filename = files[0].replace("\\", "/");
            if (new File(filename).exists()) {
                run(filename);
                return;
            }
            System.out.println("file not found");
            return;
        }
        System.out.println("usage TestToDoc: jarFileName");
    }

    private static void parseArgs(String[] args) throws ParseException {
        PosixParser parser = new PosixParser();
        Options options = new Options();
        options.addOption("b", false, "line break between classes");
        cmd = parser.parse(options, args);
    }

    private static void run(String arg) {
        List<String> methods = new TestToDoc().outputTests(arg);
        if (methods.isEmpty()) {
            System.out.println("no tests found here");
            return;
        }
        if (cmd.hasOption("b")) {
            System.out.println(new MethodParser().splitMethods(methods));
            return;
        }
        outputStd(methods);

    }

    private static void outputStd(List<String> methods) {
        for (String method : methods) {
            System.out.println(" - " + method);
        }
    }
}