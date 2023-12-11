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
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
            ResultSet resultSet = statement.executeQuery(query);

            // Display the info of each product
            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int unitsInStock = resultSet.getInt("UnitsInStock");

                System.out.println("ProductID: " + productID +
                        ", ProductName: " + productName +
                        ", UnitPrice: " + unitPrice +
                        ", UnitsInStock: " + unitsInStock);
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