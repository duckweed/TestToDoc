package org.adscale.testtodoc;

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