package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.utils.BDUtils;
import ru.akirakozov.sd.refactoring.utils.HTMLUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private static final String RESULT_OK = "OK";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(BDUtils.COLUMN_LABEL_NAME);
        long price = Long.parseLong(request.getParameter(BDUtils.COLUMN_LABEL_PRICE));

        BDUtils.executeUpdate(
                "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");

        HTMLUtils.sendResponse(response, RESULT_OK);
    }
}
