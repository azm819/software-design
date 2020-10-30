package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
    private final AddProductServlet addProductServlet = new AddProductServlet();

    @BeforeEach
    public void setup() throws IOException, SQLException {
        TestUtils.createTable();
    }

    @AfterEach
    public void setdown() throws SQLException {
        TestUtils.dropTable();
    }

    @Test
    public void testEmpty() {
        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(TestUtils.request, TestUtils.response));
        assertTrue(TestUtils.writer.toString().isEmpty());
    }

    @Test
    public void testAddOne() throws IOException {
        when(TestUtils.request.getParameter(TestUtils.NAME)).thenReturn("iPhone");
        when(TestUtils.request.getParameter(TestUtils.PRICE)).thenReturn("1000");

        addProductServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals(TestUtils.RESULT_SUCCESS, TestUtils.writer.toString());
    }

    @Test
    public void testAddWithoutName() throws IOException {
        when(TestUtils.request.getParameter(TestUtils.PRICE)).thenReturn("1000");

        addProductServlet.doGet(TestUtils.request, TestUtils.response);
        assertEquals(TestUtils.RESULT_SUCCESS, TestUtils.writer.toString());
    }

    @Test
    public void testAddWithoutPrice() {
        when(TestUtils.request.getParameter(TestUtils.NAME)).thenReturn("iPhone");

        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(TestUtils.request, TestUtils.response));
        assertTrue(TestUtils.writer.toString().isEmpty());
    }

    @Test
    public void testAddHasErrorOnGettingParams() {
        when(TestUtils.request.getParameter(TestUtils.NAME)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> addProductServlet.doGet(TestUtils.request, TestUtils.response));
        assertTrue(TestUtils.writer.toString().isEmpty());
    }

    @Test
    public void testAddPriceNotNumber() {
        when(TestUtils.request.getParameter(TestUtils.PRICE)).thenReturn("not a number");

        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(TestUtils.request, TestUtils.response));
        assertTrue(TestUtils.writer.toString().isEmpty());
    }
}
