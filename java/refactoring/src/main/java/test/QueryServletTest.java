package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class QueryServletTest {
    private final QueryServlet queryServlet = new QueryServlet();

    @BeforeEach
    public void setup() throws IOException, SQLException {
        TestUtils.createTable();
    }

    @AfterEach
    public void setdown() throws SQLException {
        TestUtils.dropTable();
    }

    private void insertData() throws SQLException {
        Map<String, Integer> items = new LinkedHashMap<>();
        items.put("iPhone", 1000);
        items.put("iPad", 1500);
        items.put("iMac", 500);
        TestUtils.formInsert(items);
    }

    @Test
    public void testMaxEmpty() throws IOException {
        when(TestUtils.request.getParameter("command")).thenReturn("max");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testMax() throws IOException, SQLException {
        insertData();
        when(TestUtils.request.getParameter("command")).thenReturn("max");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "iPad\t1500</br>\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testMinEmpty() throws IOException {
        when(TestUtils.request.getParameter("command")).thenReturn("min");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testMin() throws IOException, SQLException {
        insertData();
        when(TestUtils.request.getParameter("command")).thenReturn("min");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "iMac\t500</br>\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testSumEmpty() throws IOException {
        when(TestUtils.request.getParameter("command")).thenReturn("sum");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "Summary price: \n" +
                "0\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testSum() throws IOException, SQLException {
        insertData();
        when(TestUtils.request.getParameter("command")).thenReturn("sum");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "Summary price: \n" +
                "3000\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testCountEmpty() throws IOException {
        when(TestUtils.request.getParameter("command")).thenReturn("count");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "Number of products: \n" +
                "0\n" +
                "</body></html>", TestUtils.writer.toString());
    }

    @Test
    public void testCount() throws IOException, SQLException {
        insertData();
        when(TestUtils.request.getParameter("command")).thenReturn("count");
        queryServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals("<html><body>\n" +
                "Number of products: \n" +
                "3\n" +
                "</body></html>", TestUtils.writer.toString());
    }
}
