package com.github.fo2rist.mclaren.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class McLarenHtmlUtils {
    private static final String DEFAULT_CSS =
            "<head>"
            + "  <style>"
            + "    .inline-image { width:100%;}"
            + "    img {max-width: 100%}"
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

    /**
     * Add styles to the HTML to make it prettier and strip from incorrect escape sequences.
     * @return updated HTML or empty string if source HTML is null
     */
    public static String pretify(@Nullable String rawMcLarenHtml) {
        if (rawMcLarenHtml == null) {
            return "";
        }

        return addCSS(replaceEscapeSequences(rawMcLarenHtml));
    }

    private static String addCSS(@NonNull String html) {
        return DEFAULT_CSS + html;
    }

    private static String replaceEscapeSequences(@NonNull String rawMcLarenHtml) {
        return rawMcLarenHtml
                .replace("\\r", "")
                .replace("\\n", "\n")
        ;
    }
}
