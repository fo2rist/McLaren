package com.github.fo2rist.mclaren.ui.utils;

public class McLarenHtmlUtils {
    private static final String DEFAULT_CSS =
            "<head>"
            + "  <style>"
            + "    .inline-image { width:100%;}"
            + "    @font-face{ font-family:\"ropa_sans\"; src: url('file:///android_asset/fonts/ropa_sans_regular.ttf');}"
            + "    body { font-family: \"ropa_sans\"; }"
            + "    h2 strong, div p strong { color: black;}"
            + "    table, p { color: #808080;}"
            + "    table { border-collapse: collapse;}"
            + "    table tr:nth-child(odd) { background: #FAFAFA;}"
            + "    td { padding: 8; font-size: 80%;}"
            + "  </style>"
            + "</head>";

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
        ;
    }
}
