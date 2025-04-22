package com.intern;

import com.intern.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableLister {
    public static List<String> tableNames = new ArrayList<>();

    public static void printTableNames() {
        try (Connection conn = DBConnection.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"});

            String currentDatabase = conn.getCatalog();

            System.out.println("Tables in database '" + currentDatabase + "':");
            while (tables.next()) {
                String tableCatalog = tables.getString("TABLE_CAT");
                String tableName = tables.getString("TABLE_NAME");

                if (currentDatabase.equals(tableCatalog)) {
                    tableNames.add(tableName);
                    System.out.println("- " + tableName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
