package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetProductsServletTest {
    private final GetProductsServlet getProductsServlet = new GetProductsServlet();

    @BeforeEach
    public void setup() throws IOException, SQLException {
        TestUtils.createTable();
    }

    @AfterEach
    public void setdown() throws SQLException {
        TestUtils.dropTable();
    }

    private void formInsert(Map<String, Integer> items) throws SQLException {
        StringBuilder request = new StringBuilder();
        if (!items.isEmpty()) {
            request.append("insert into product(name, price) values\n");
            for (int i = 0; i < items.size(); ++i) {
                var name = items.keySet().toArray()[i];
                var price = items.values().toArray()[i];
                request.append("('").append(name).append("', '").append(price).append("')");
                if (i < items.size() - 1) {
                    request.append(",\n");
                }
            }
        }
        System.out.print(request.toString());
        TestUtils.sqlRequest(request.toString());
    }

    private String formAnswer(Map<String, Integer> items) {
        StringBuilder answer = new StringBuilder();
        answer.append("<html><body>\n");
        if (!items.isEmpty()) {
            for (var item : items.entrySet()) {
                answer.append(item.getKey()).append("\t").append(item.getValue()).append("</br>\n");
            }
        }
        answer.append("</body></html>");
        return answer.toString();
    }

    @Test
    public void testEmpty() throws IOException {
        getProductsServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals(formAnswer(new LinkedHashMap<>()), TestUtils.writer.toString());
    }

    @Test
    public void testAddOne() throws SQLException, IOException {
        Map<String, Integer> items = new LinkedHashMap<>();
        items.put("iPhone", 1000);
        items.put("iPad", 1500);
        items.put("iMac", 500);
        formInsert(items);
        getProductsServlet.doGet(TestUtils.request, TestUtils.response);

        assertEquals(formAnswer(items), TestUtils.writer.toString());
    }
}
