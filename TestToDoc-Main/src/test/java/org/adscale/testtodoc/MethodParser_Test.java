package org.adscale.testtodoc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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

public class MethodParser_Test extends MethodParser {
    @Test
    public void singleMethod() {
        assertEquals(LN_SEP + "class" + LN_SEP + " - " + "method" + LN_SEP, splitMethods(classMethodLine("class", "method")));
        assertEquals(LN_SEP + "1" + LN_SEP + " - " + "2" + LN_SEP, splitMethods(classMethodLine("1", "2")));
    }

    @Test
    public void multipleMethods() {
        String className = "c";
        ArrayList<String> clazzMethods = createClassStrings(className);

        assertEquals(createMassagedClassString(className), splitMethods(clazzMethods));
    }

    @Test
    public void multipleClasses() {

        ArrayList<String> clazzes = new ArrayList<String>();
        clazzes.addAll(createClassStrings("c1"));
        clazzes.addAll(createClassStrings("c2"));

        String expected = createMassagedClassString("c1") + createMassagedClassString("c2");
        assertEquals(expected, splitMethods(clazzes));
    }

    private ArrayList<String> createClassStrings(String className) {
        ArrayList<String> clazzMethods = new ArrayList<String>();
        clazzMethods.add(classMethodLine(className, "m1"));
        clazzMethods.add(classMethodLine(className, "m2"));
        return clazzMethods;
    }

    private String createMassagedClassString(String className) {
        return LN_SEP + className + LN_SEP + " - m1" + LN_SEP + " - m2" + LN_SEP;
    }


    private String classMethodLine(String clazz, String method) {
        return clazz + "::" + method;
    }

}
