package com.github.fo2rist.mclaren.ui.utils;

public class McLarenHtmlUtils {
    private static final String DEFAULT_CSS = "<head><style>\n"
                                      + ".inline-image {\n"
                                      + "    width:100%;\n"
                                      + "}\n"
                                      + "</style></head>";


    private McLarenHtmlUtils() {
    }

    public static String pretify(String rawMcLarenHtml) {
        return addCSS(replaceEscapeSequences(rawMcLarenHtml));
    }

    private static String addCSS(String html) {
        return DEFAULT_CSS + html;
    }

    private static String replaceEscapeSequences(String rawMcLarenHtml) {
        return rawMcLarenHtml
                .replace("\\r", "")
                .replace("\\n", "\n")
//            .replace("\\t", "\t")
//            .replace("\\\"", "\"");
        ;
    }
}
