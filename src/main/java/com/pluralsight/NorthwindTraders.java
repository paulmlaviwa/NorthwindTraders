package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NorthwindTraders {
    public static void main(String[] args) {
        // Opening a connection to the DB
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = "@Cyantist.93_";

        try {
            // Establish the connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to select all products
            String query = "SELECT ProductName FROM Products";
            ResultSet resultSet = statement.executeQuery(query);

            // Display the name of each product
            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                System.out.println(productName);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}