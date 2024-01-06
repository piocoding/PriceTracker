/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingcart;
import java.sql.*;
/**
 *
 * @author user
 */
public class viewItem {
    String url = "jdbc:mysql://localhost:3306/csv_db 7";
    String user = "iskandar";
    String pass = "123";
    int[]itemcode= new int[100];
    int count=0;
    public void viewCheapest(String user) throws SQLException {
    try (Connection connect = DriverManager.getConnection(url, user, pass)) {
        Statement statement = connect.createStatement();
        String query = "SELECT * FROM shopping_cart where username=Username";

        ResultSet result = statement.executeQuery(query);

        // save item code
        while (result.next()) {
            itemcode[count] = result.getInt(2);
            count++;
        }

        System.out.printf("%-50s%-10s%-60s%-20s%-20s\n", "Item", "Quantity", "Cheapest Premise", "Price per unit", "Subtotal");

        for (int i = 0; i < count; i++) {

            String query1 = "SELECT * FROM shopping_cart JOIN pricecatcher_2023 JOIN lookup_premise ON (pricecatcher_2023.item_code=shopping_cart.Item_code && pricecatcher_2023.premise_code = lookup_premise.premise_code)";
            String query2 = " where (username=Username && shopping_cart.item_code = " + itemcode[i];
            String query3 = ") ORDER BY pricecatcher_2023.price ASC;";
            ResultSet result1 = statement.executeQuery(query1 + query2 + query3);

            double price = 0;
            double tprice = 0;

            while (result1.next()) {
                price = result1.getDouble(4) * result1.getDouble(8);

                // item
                System.out.printf("%-50s", result1.getString(3));
                // quantity
                System.out.printf("%-10s", result1.getInt(4));
                // premise
                System.out.printf("%-60s", result1.getString(10));
                // price per unit
                System.out.printf("%.2f", result1.getDouble(8));
                // subtotal
                System.out.printf("%-20s"," ");
                System.out.printf("%.2f", price);
                tprice += price;

                System.out.println("");
                break;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
