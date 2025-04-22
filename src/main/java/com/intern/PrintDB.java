package com.intern;

import com.intern.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrintDB {

    public static List<String[]> fetchTable(String tableName) {
        List<String[]> rows = new ArrayList<>();
        String query = "SELECT * FROM "+tableName;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Add column names as the first row
            String[] header = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                header[i - 1] = metaData.getColumnLabel(i);
            }
            rows.add(header);

            // Add data rows
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    row[i - 1] = (value != null) ? value.toString() : null;
                }
                rows.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }
}
