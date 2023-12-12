package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class NorthwindTraders {
    private static final String URL = "jdbc:mysql://localhost:3306/northwind";
    private static final String USER = "root";
    private static final String PASSWORD = "**************";

    private static final BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Display home screen
                System.out.println("What do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayAllProducts();
                        break;
                    case 2:
                        displayAllCustomers();
                        break;
                    case 3:
                        displayAllCategories();
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

    private static void displayAllProducts() throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products");
                ResultSet resultSet = statement.executeQuery()
        ) {
            System.out.printf("%-15s %-40s %-15s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----------------------------------------");

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

    private static void displayAllCustomers() throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country");
                ResultSet resultSet = statement.executeQuery()
        ) {
            System.out.printf("%-25s %-40s %-20s %-15s %-15s%n", "ContactName", "CompanyName", "City", "Country", "Phone");
            System.out.println("---------------------------------------------------------------------------");

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

    private static void displayAllCategories() throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID");
                ResultSet resultSet = statement.executeQuery()
        ) {
            System.out.printf("%-15s %-40s%n", "Category ID", "Category Name");
            System.out.println("----------------------------------------");

            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");

                System.out.printf("%-15d %-40s%n", categoryID, categoryName);
            }

            System.out.println("Enter a Category ID to display products in that category (or enter 0 to go back): ");
            int selectedCategoryID = new Scanner(System.in).nextInt();

            if (selectedCategoryID != 0) {
                displayProductsInCategory(selectedCategoryID);
            }

            System.out.println();
        }
    }

    private static void displayProductsInCategory(int categoryID) throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE CategoryID = ?");
        ) {
            statement.setInt(1, categoryID);
            ResultSet resultSet = statement.executeQuery();

            System.out.printf("%-15s %-40s %-15s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----------------------------------------");

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
}
