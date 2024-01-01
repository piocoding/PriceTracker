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
    //ni letak dkt main 
    //CheapestSellers cs = new CheapestSellers(int[] item_code);
    public CheapestSellers(int[] code){
        itemCode = code[0];
        try {
            
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pricecatchers_market",
                "root",
                "Allahswt24434!"
            );

            Statement statement = connection.createStatement();

            String sqlQuery = "SELECT DISTINCT p.premise_code, p.item_code, p.price, lp.address, lp.premise " +
                              "FROM pricecatchers_aug2023 p " +  
                              "JOIN lookup_premise.lookup_premise lp ON p.premise_code = lp.premise_code " +
                              "WHERE p.item_code = " + itemCode +
                              "ORDER BY p.price ASC " +
                              "LIMIT 5";


            ResultSet resultSet = statement.executeQuery(sqlQuery);

            int premiseNumber = 1; 
            while (resultSet.next()) {
                double price = resultSet.getDouble("price");
                String address = resultSet.getString("address");
                String premise = resultSet.getString("premise");
            
                
                System.out.printf("Premise %d: %s%nPrice: RM%.2f%nAddress: %s%n%n",
                  premiseNumber, premise, price, address);

                    premiseNumber++; 
            }

            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
