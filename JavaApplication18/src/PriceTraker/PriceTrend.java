/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Chanteq Demo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PriceTrend {
    public static void main(String[] args) {
        
        try {
            
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pricecatchers_market",
                "root",
                "Allahswt24434!"
            );

            String sqlQuery = "SELECT DAY(p.date) AS Day, " +
                              "CONCAT(REPEAT('$', ROUND(AVG(p.price) / 0.30))) AS Price, " +
                              "i.item " +
                              "FROM pricecatchers_aug2023 p " +
                              "JOIN lookup_item.lookup_item i ON p.item_code = i.item_code " +
                              "WHERE p.item_code = '21' " + 
                              "GROUP BY Day, i.item " +
                              "ORDER BY Day";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String itemName = resultSet.getString("item");
                System.out.println("Price Trend Chart for " + itemName);
                System.out.println("");
                System.out.println(" Days  |   Price");
                System.out.println("---------------------");
            }

            do {
                int day = resultSet.getInt("Day");
                String price = resultSet.getString("Price");
                System.out.printf("%02d     | %s%n", day, price);
            } while (resultSet.next());
            
            System.out.println();
            System.out.println("Scale: \n$ = RM0.30");

            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
