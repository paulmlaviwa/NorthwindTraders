package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class NorthwindTraders {
    public static void main(String[] args) {
        // Opening a connection to the DB
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = "@Cyantist.93_";

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Display home screen
                System.out.println("What do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayAllProducts(url, user, password);
                        break;
                    case 2:
                        displayAllCustomers(url, user, password);
                        break;
                    case 0:
                        System.out.println("Exiting program. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAllProducts(String url, String user, String password) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products")
        ) {
            System.out.printf("%-15s %-40s %-15s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int unitsInStock = resultSet.getInt("UnitsInStock");

                System.out.printf("%-15d %-40s %-15.2f %-10d%n", productID, productName, unitPrice, unitsInStock);
            }
            System.out.println();
        }
    }

    private static void displayAllCustomers(String url, String user, String password) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country")
        ) {
            System.out.printf("%-25s %-40s %-20s %-15s %-15s%n", "ContactName", "CompanyName", "City", "Country", "Phone");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                String contactName = resultSet.getString("ContactName");
                String companyName = resultSet.getString("CompanyName");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");
                String phone = resultSet.getString("Phone");

                System.out.printf("%-25s %-40s %-20s %-15s %-15s%n", contactName, companyName, city, country, phone);
            }
            System.out.println();
        }
    }
}
