/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Chanteq Demo
 */
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
import java.sql.ResultSet;
import java.sql.Statement;

public class CheapestSellers {
    public CheapestSellers(int[] code){
        int itemCode = code[0];
        try {
            
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pricecatchers_market",
                "root",
                "Allahswt24434!"
            );

            Statement statement = connection.createStatement();

            String sqlQuery = "SELECT DISTINCT p.premise_code, p.item_code, p.price, lp.address, lp.premise, li.item " +
                              "FROM pricecatchers_aug2023 p " +  
                              "JOIN lookup_premise.lookup_premise lp ON p.premise_code = lp.premise_code " +
                              "JOIN lookup_item.lookup_item li ON p.item_code = li.item_code " + 
                              "WHERE p.item_code = " + itemCode + 
                              " ORDER BY p.price ASC " +
                              "LIMIT 5";

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if(resultSet.next()) {
                String itemName = resultSet.getString("item");
                System.out.println("Top 5 Cheapest Sellers for " + itemName);
                System.out.println();
                
                int premiseNumber = 1; 
                do {
                    double price = resultSet.getDouble("price");
                    String address = resultSet.getString("address");
                    String premise = resultSet.getString("premise");
            
                    System.out.printf("Premise %d: %s%nPrice: RM%.2f%nAddress: %s%n%n",
                      premiseNumber, premise, price, address);

                    premiseNumber++; 
                } while(resultSet.next());
            } else {
                System.out.println("No results found");
            }

            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
