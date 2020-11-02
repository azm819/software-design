package ru.akirakozov.sd.refactoring.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HTMLUtils {
    private static final String CONTENT_TYPE = "text/html";

    private static final String HTML_BEGIN = "<html><body>\n";
    private static final String HTML_END = "</body></html>";

    public static void sendResponse(HttpServletResponse response, String stringResponce) throws IOException {
        response.getWriter().print(stringResponce);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static String formItemsAnswer(String header, Map<String, List<Long>> map) {
        StringBuilder response = new StringBuilder(HTML_BEGIN).append(header);

        for (var elem : map.entrySet()) {
            for (var value : elem.getValue()) {
                response.append(elem.getKey()).append("\t").append(value).append("</br>\n");
            }
        }

        response.append(HTML_END);
        return response.toString();
    }

    public static String formIntegerAnswer(String header, Long integer) {
        StringBuilder response = new StringBuilder(HTML_BEGIN).append(header);

        response.append(integer).append("\n");

        response.append(HTML_END);
        return response.toString();
    }
}
