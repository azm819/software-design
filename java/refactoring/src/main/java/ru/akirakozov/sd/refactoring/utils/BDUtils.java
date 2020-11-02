package ru.akirakozov.sd.refactoring.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BDUtils {
    private static final String SQL_URL = "jdbc:sqlite:test.db";

    public static final String COLUMN_LABEL_NAME = "name";
    public static final String COLUMN_LABEL_PRICE = "price";

    public static void executeUpdate(String sql) {
        try (Connection c = DriverManager.getConnection(SQL_URL)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String executeQuery(String sql, String header, boolean shouldShowItems) {
        try {
            try (Connection c = DriverManager.getConnection(SQL_URL)) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                String result = "";
                if (shouldShowItems) {
                    Map<String, List<Long>> items = new HashMap<>();
                    while (rs.next()) {
                        String name = rs.getString(COLUMN_LABEL_NAME);
                        Long price = rs.getLong(COLUMN_LABEL_PRICE);
                        if (items.containsKey(name)) {
                            items.get(name).add(price);
                        }
                        else {
                            List<Long> list = new ArrayList<>();
                            list.add(price);
                            items.put(name, list);
                        }
                    }
                    result = HTMLUtils.formItemsAnswer(header, items);
                } else {
                    if (rs.next()) {
                        result = HTMLUtils.formIntegerAnswer(header, rs.getLong(1));
                    }
                }

                rs.close();
                stmt.close();
                return result;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
