package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
    private final AddProductServlet addProductServlet = new AddProductServlet();

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    private final StringWriter writer = new StringWriter();

    private void sqlRequest(String request) throws SQLException {
        try (final var connection = DriverManager.getConnection(TestUtils.DB_URL)) {
            connection.prepareStatement(request).execute();
        }
    }

    @BeforeEach
    public void setup() throws IOException, SQLException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        sqlRequest(TestUtils.CREATE_TABLE);
    }

    @AfterEach
    public void setdown() throws SQLException {
        sqlRequest(TestUtils.DROP_TABLE);
    }

    @Test
    public void testEmpty() {
        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(request, response));
        assertTrue(writer.toString().isEmpty());
    }

    @Test
    public void testAddOne() throws IOException {
        when(request.getParameter("name")).thenReturn("iPhone");
        when(request.getParameter("price")).thenReturn("1000");

        addProductServlet.doGet(request, response);
        assertEquals(TestUtils.RESULT_SUCCESS, writer.toString());
    }

    @Test
    public void testAddWithoutName() throws IOException {
        when(request.getParameter("price")).thenReturn("1000");

        addProductServlet.doGet(request, response);
        assertEquals(TestUtils.RESULT_SUCCESS, writer.toString());
    }

    @Test
    public void testAddWithoutPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("iPhone");

        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(request, response));
        assertTrue(writer.toString().isEmpty());
    }

    @Test
    public void testAddHasErrorOnGettingParams() {
        when(request.getParameter("name")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> addProductServlet.doGet(request, response));
        assertTrue(writer.toString().isEmpty());
    }

    @Test
    public void testAddPriceNotNumber() {
        when(request.getParameter("price")).thenReturn("not a number");

        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(request, response));
        assertTrue(writer.toString().isEmpty());
    }
}