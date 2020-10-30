package test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {
    public final static String DB_URL = "jdbc:sqlite:test.db";

    public final static String NAME = "name";
    public final static String PRICE = "price";

    public final static String DROP_TABLE = "drop table if exists product";
    public final static String CREATE_TABLE =
            "create table if not exists product( " +
            "id integer primary key autoincrement not null," +
            "name text not null," +
            "price int not null)";

    public final static String RESULT_SUCCESS = "OK";
    public final static String RESULT_EMPTY = "";

    final static HttpServletRequest request = mock(HttpServletRequest.class);
    final static HttpServletResponse response = mock(HttpServletResponse.class);

    final static StringWriter writer = new StringWriter();

    public static void sqlRequest(String request) throws SQLException {
        try (final var connection = DriverManager.getConnection(TestUtils.DB_URL)) {
            connection.prepareStatement(request).execute();
        }
    }

    public static void createTable() throws SQLException, IOException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        sqlRequest(CREATE_TABLE);
    }

    public static void dropTable() throws SQLException {
        sqlRequest(DROP_TABLE);
    }
}
