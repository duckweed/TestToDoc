package org.adscale.testtodoc;

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

public class CamelCaser {
    static void handleUnderscore(StringBuilder sb, String word, int offset) {
        int index = offset + 1;
        if (index >= word.length()) {
            sb.append(".");
            return;
        }
        if (Character.isLowerCase(word.charAt(index))) {
            sb.append(", ");
        } else {
            sb.append(". ");
        }
        return;
    }

    protected static String camelCaseWord(String word) {
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
}