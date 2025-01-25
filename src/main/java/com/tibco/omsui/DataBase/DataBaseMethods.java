package com.tibco.omsui.DataBase;

import java.sql.*;

public class DataBaseMethods {

    public Connection getConnection(String databaseURL,String dbUsername,String dbPassword, String className) throws ClassNotFoundException, SQLException {
        Class.forName(className);
        return DriverManager.getConnection(databaseURL, dbUsername, dbPassword);
    }

    public int executeQueryForCount(String query, String databaseURL, String dbUsername, String dbPassword, String className) throws SQLException, ClassNotFoundException {
        int count = 0;
        try (Connection connection = getConnection(databaseURL,dbUsername, dbPassword, className);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return count;
    }

    public int executeUpdate(String query,String databaseURL, String dbUsername, String dbPassword, String className) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection(databaseURL, dbUsername, dbPassword, className);
             Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
