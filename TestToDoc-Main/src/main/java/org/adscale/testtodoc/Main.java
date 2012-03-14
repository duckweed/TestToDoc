package org.adscale.testtodoc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.File;
import java.util.List;

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
        System.out.println("usage TestToDoc: jarFileName" + MethodParser.LN_SEP + " -b : break between classes");
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