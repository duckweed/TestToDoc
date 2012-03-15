package org.adscale.testtodoc;

import java.util.ArrayList;
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

public class MethodParser {

    static String LN_SEP = System.getProperty("line.separator");

    protected String splitMethods(String clazzMethods) {
        List<String> strings = new ArrayList<String>();
        strings.add(clazzMethods);
        return splitMethods(strings);
    }

    protected String splitMethods(List<String> clazzMethods) {
        String currentClazz = "";
        String output = "";
        for (String clazzMethod : clazzMethods) {
            String[] strings = clazzMethod.split("::");
            String clazz = strings[0];
            String method = strings[1];
            if (clazz.equals(currentClazz)) {
                output += " - " + method + LN_SEP;
            } else {
                currentClazz = clazz;
                output += LN_SEP + clazz + LN_SEP + " - " + method + LN_SEP;
            }
        }
        return output;
    }
}
