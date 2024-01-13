/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricetracker_assignment;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class viewCart {
     String url = "jdbc:mysql://localhost:3306/pricecatcher-2023-08";
     String user = "root";
     String pass = "Nurulizzani20.";
     Scanner scan = new Scanner(System.in);
     int[] itemcode = new int [100];
     String[] itemname = new String [100];
     int count;
     String[] username = new String[1];
     BasicShoppinngCart k = new BasicShoppinngCart();
     public  viewCart(String [] u) throws SQLException{
         username[0] = u[0];
         
         ChoiceUI();
         
     }
         
     
     public void ChoiceUI() throws SQLException{
         System.out.println("Select options");
         System.out.println("1. View cheapest seller for all selected items");
         System.out.println("2. Find shops to buy items in cart");
         System.out.println("3. Edit your cart");
         System.out.print("Enter your choice :");
         int choice = 0;
            try { 
                choice = scan.nextInt();
            } catch (Exception e){
                scan.next(); // consume the invalid input to prevent infinite loop 
                //caused by repeatedly attempting to read the same invalid input.
            }
         switch (choice) {
             case 1:count=0;
                    viewCheapest(username);
                    backUI();
                    break;
             case 2:count=0;
                    ViewbyShop( username );
                     backUI();
                    break;
             case 3:count=0;
                    editCart( username );
                    
                     backUI();
                    break;
             default:
                System.out.println("Invalid choice. Please select a valid choice.");
                ChoiceUI();
         }
     }
   public void ViewbyShop (String [] u)throws SQLException{
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
              
                
                String query1 = "SELECT * FROM pricecatcher JOIN lookup_premise.lookup_premise ON(lookup_premise.lookup_premise.premise_code = pricecatcher.premise_code && item_code = ";
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
                
                if(count!=0)
                updateviewbyShop(u);
                else
                    return;
               
               
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
             String query3 ="SELECT DISTINCT* FROM tempfindshop JOIN lookup_item.lookup_item  JOIN shopping_cart ON(lookup_item.lookup_item.item_code = tempfindshop.Item_code && shopping_cart.Item_code = lookup_item.lookup_item.item_code && shopping_cart.Item_code = tempfindshop.Item_code ) WHERE premise_code=";
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
                    PreparedStatement preparedStatement2 = connect.prepareStatement ("DELETE  FROM tempfindshop  WHERE item_code="+result4.getInt(6));
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

                    String query1 = "SELECT * FROM shopping_cart JOIN pricecatcher JOIN lookup_premise.lookup_premise ON (pricecatcher.item_code=shopping_cart.Item_code && pricecatcher.premise_code = lookup_premise.lookup_premise.premise_code)";
                    String query2 = " where (username=Username && shopping_cart.item_code = " + itemcode[i];
                    String query3 = ") ORDER BY pricecatcher.price ASC;";
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
     public void editCart(String u[]) throws SQLException {
            try (Connection connect = DriverManager.getConnection(url, user, pass)) {
                Statement statement = connect.createStatement();

                String query = "SELECT * FROM shopping_cart where Username= '" + u[0] + "'";
                int i=1;

                ResultSet result = statement.executeQuery(query);
                System.out.println("Your cart");

                // save item code
                System.out.printf("%-10s%-50s%-10s\n","Bil","Item", "Quantity");
                while (result.next()) {
                    itemcode[count] = result.getInt(2);
                    itemname[count]=result.getString(3);
                    
                     count++;
                    System.out.printf("%-10s%-50s%-10s\n",i,result.getString(3),result.getInt(4)); 
                    i++;
                    
                }
                ChoiceUI2(u[0]);
                
                

                

                
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
}
     public void ChoiceUI2(String user) throws SQLException{
         System.out.println("");
         System.out.println("Select 1 to add  ");
         System.out.println("Select 2  to remove  ");
         System.out.println("Select 0  to return menu View Shopping Cart  ");
         System.out.print("Enter your choice :");
         
         int choice = scan.nextInt();
         System.out.println("");
         switch (choice){
             case 0:ChoiceUI();
                    break;
         case 1:System.out.println("Select 0 if  want return to choice");
                System.out.print("Enter your choice :");
                int choice1 = scan.nextInt();
                if (choice1 ==0) 
                    ChoiceUI();
                else{
                System.out.print("How many you want to add: ");
                int quantity1=scan.nextInt();
                k.addExistingItem( user, itemcode[choice1-1],quantity1);
                }
                    
       
                break;
         case 2:System.out.println("Select 0 if  want return to choice");
                System.out.print("Enter your choice :");
                int choice2 = scan.nextInt();
                
                
                switch(choice2){
                    case 0: ChoiceUI();
                            break;
                    default: k.Deleteitem( user,  itemcode[choice2-1], itemname[choice2-1]); 
                }
                
                
                    
        }
        
            
         
         
         
     }
     public void backUI() throws SQLException{
        Scanner scn = new Scanner(System.in);
        
        System.out.println("\n1. Back to View Shopping Cart. \n2. Back to Main Menu.");
        System.out.print("\nEnter your choice: ");
        
        int ans = scn.nextInt();
        
        switch(ans){
            case 1:
                ChoiceUI();
                break;
            case 2:
                MainMenu mainmenu = new MainMenu(username);
                return;
            default:
                System.out.println("Invalid choice.");
                backUI();
                break;
        }
       
     }
}