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
public class QueryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        String commandResponse = "";

        if ("max".equals(command)) {

            commandResponse = BDUtils.executeQuery(
                    "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                    "<h1>Product with max price: </h1>\n",
                    true);

        } else if ("min".equals(command)) {

            commandResponse = BDUtils.executeQuery(
                    "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                    "<h1>Product with min price: </h1>\n",
                    true);

        } else if ("sum".equals(command)) {

            commandResponse = BDUtils.executeQuery(
                    "SELECT SUM(price) FROM PRODUCT",
                    "Summary price: \n",
                    false);

        } else if ("count".equals(command)) {

            commandResponse = BDUtils.executeQuery(
                    "SELECT COUNT(*) FROM PRODUCT",
                    "Number of products: \n",
                    false);

        } else {
            commandResponse = "Unknown command: " + command;
        }

        HTMLUtils.sendResponse(response, commandResponse);
    }

}
