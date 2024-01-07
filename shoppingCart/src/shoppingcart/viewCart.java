/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingcart;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class viewCart {
     String url = "jdbc:mysql://localhost:3306/csv_db 7";
     String user = "iskandar";
     String pass = "123";
     Scanner scan = new Scanner(System.in);
     int[] itemcode = new int [100];
     int count =0;
     public  viewCart(String [] u) throws SQLException{
         
         System.out.println("Select options");
         System.out.println("1. View cheapest seller for all selected items");
         System.out.println("2. Find shops to buy items in cart");
         int choice = scan.nextInt();
         switch (choice) {
             case 1:   ViewbyShop( u);
                       break;
             case 2:  viewCheapest(u);
                      break;
         }
         
     }
    
   public void ViewbyShop(String [] u)throws SQLException{
        try (Connection connect = DriverManager.getConnection(url, user, pass)) {
            Statement statement = connect.createStatement();
            String query ="SELECT * FROM shopping_cart where Username= '" + u[0] + "'";
           
            ResultSet result = statement.executeQuery(query);
            
            //save item code
            while (result.next()){
                itemcode[count] = result.getInt(2);
                count++;
            }
            
            
            //find item in the premisecode using itemcode
            for(int i=0;i<count;i++){
              
                
                String query1 = "SELECT * FROM pricecatcher_2023 JOIN lookup_premise ON(lookup_premise.premise_code = pricecatcher_2023.premise_code && item_code = ";
                ResultSet result2 = statement.executeQuery(query1 +itemcode[i] +")");
                
                int premise_code;
                double price;
                String premise;
                while (result2.next()){ 
                     premise_code = result2.getInt(2);
                     price = result2.getDouble(4);
                     premise = result2.getString(6);
                     String insertQuery = "INSERT INTO tempfindshop (Username,Item_code,premise_code,price,premise) VALUES ( ?,?,?,?,?)";
                     PreparedStatement preparedStatement = connect.prepareStatement(insertQuery);
                     preparedStatement.setString(1, u[0]);
                     preparedStatement.setInt(2, itemcode[i]);
                     preparedStatement.setInt(3, premise_code);
                     preparedStatement.setDouble(4, price);
                     preparedStatement.setString(5, premise);
                     preparedStatement.executeUpdate();
                    
            }    }
              
            
            
            
            
            
            
            String query5 ="SELECT* FROM tempfindshop ";
            ResultSet result6 = statement.executeQuery(query5);
             while(result6.next()){
                updateviewbyShop(u);
               if(count==0)
                   break;
               
             }   
            
           

            
            
            
       }catch (SQLException e) {
            e.printStackTrace();
        }
   }
   
    public void updateviewbyShop(String  [] u)throws SQLException{
        try (Connection connect = DriverManager.getConnection(url, user, pass)) {
            Statement statement = connect.createStatement();
             int premisecode2=0;
              // count shop brg banyak
             String query2 ="SELECT premise_code, COUNT(Item_code) as item_count FROM tempfindshop GROUP BY premise_code ORDER BY item_count DESC;";
            ResultSet result3 = statement.executeQuery(query2);
            
           while(result3.next()){
                premisecode2=result3.getInt(1);
                break;
           }
            String query4 ="SELECT premise FROM tempfindshop WHERE premise_code=";
            ResultSet result5 = statement.executeQuery(query4+premisecode2);
             while(result5.next()){
                 System.out.println("Premise: "+result5.getString(1));
                break;
           }         
           
           
           // display barang apa yg ada dlt shop tu
           double price =0;
           double tprice =0;
             String query3 ="SELECT* FROM tempfindshop JOIN lookup_item JOIN shopping_cart ON(lookup_item.item_code = tempfindshop.Item_code && shopping_cart.Item_code = lookup_item.item_code && shopping_cart.Item_code = tempfindshop.Item_code ) WHERE premise_code=";
             ResultSet result4 = statement.executeQuery(query3+premisecode2);
             System.out.printf("%15s","Item");
             System.out.print(" ");
             System.out.printf("%70s" ,"Quantity");
             System.out.print(" ");
             System.out.printf("%20s" ,"Price per unit");
             System.out.print(" ");
             System.out.printf("%20s" ,"Subtotal");
             System.out.println("");
            while(result4.next()){
                
                price = result4.getDouble(4)*result4.getDouble(14);
                System.out.printf("%-80s",result4.getString(7));
                System.out.printf(  "  %-10s",result4.getInt(14));
                System.out.printf("%4s" ," ");
                System.out.printf(  "  %.2f",result4.getDouble(4));
                System.out.printf("%-18s" ," ");
                System.out.printf("%.2f",price);
                tprice+=price;
                System.out.println("");
                PreparedStatement preparedStatement2 = connect.prepareStatement ("DELETE  FROM tempfindshop  WHERE item_code="+result4.getDouble(6));
                preparedStatement2.executeUpdate();
                count--;
                
            }
            System.out.print("Total Price RM");
            System.out.printf("%.2f",tprice);
            System.out.println(" ");
            System.out.println("");   
       }catch (SQLException e) {
           
       
            e.printStackTrace();
        }
    

}
     public void viewCheapest(String u[]) throws SQLException {
            try (Connection connect = DriverManager.getConnection(url, user, pass)) {
                Statement statement = connect.createStatement();
                String query = "SELECT * FROM shopping_cart where Username= '" + u[0] + "'";

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
