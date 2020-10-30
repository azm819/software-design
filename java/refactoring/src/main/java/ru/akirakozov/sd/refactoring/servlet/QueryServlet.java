package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                    response.getWriter().print("<html><body>\n");
                    response.getWriter().print("<h1>Product with max price: </h1>\n");

                    while (rs.next()) {
                        String name = rs.getString("name");
                        long price = rs.getLong("price");
                        response.getWriter().print(name + "\t" + price + "</br>\n");
                    }
                    response.getWriter().print("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                    response.getWriter().print("<html><body>\n");
                    response.getWriter().print("<h1>Product with min price: </h1>\n");

                    while (rs.next()) {
                        String name = rs.getString("name");
                        long price = rs.getLong("price");
                        response.getWriter().print(name + "\t" + price + "</br>\n");
                    }
                    response.getWriter().print("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                    response.getWriter().print("<html><body>\n");
                    response.getWriter().print("Summary price: \n");

                    if (rs.next()) {
                        response.getWriter().print(rs.getLong(1) + "\n");
                    }
                    response.getWriter().print("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                    response.getWriter().print("<html><body>\n");
                    response.getWriter().print("Number of products: \n");

                    if (rs.next()) {
                        response.getWriter().print(rs.getLong(1) + "\n");
                    }
                    response.getWriter().print("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().print("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
