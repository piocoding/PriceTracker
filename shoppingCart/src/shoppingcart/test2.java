/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingcart;

//import com.mysql.cj.jdbc.result.*;
import java.sql.Connection; // Represents a connection to the database
import java.sql.DriverManager; // Helps in obtaining a connection to the database
import java.sql.PreparedStatement; // Used for prepared statements
import java.sql.Statement; // Used for executing SQL statements
import java.sql.ResultSet; // Represents a table of data resulting from a query
import java.sql.SQLException; // Handles SQL exceptions
import java.sql.*; //x tahu nk import apa for resultmetadata 

import java.util.Scanner;


public class test2 {
    static String url = "jdbc:mysql://localhost:3306/csv_db 7";
    static String user = "iskandar";
    static String pass = "123";
    static Scanner scan = new Scanner(System.in);
    
    // nak tambah brp dlm  cart
    public static void addExistingItem(String username, String Item_code, int quantity){
        System.out.println("");
        
        try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart where username=Username";
          ResultSet result= statement.executeQuery(query);
          while (result.next()) {
              
                if (result.getString(1).equals(username) && result.getString(2).equals(Item_code)) {
                    
                   
                    String insertQuery = "UPDATE shopping_cart SET Item_quantity = Item_quantity + ? WHERE Item_code = ?";
                    PreparedStatement preparedStatement = connect.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, quantity);
                    preparedStatement.setString(2,Item_code);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Quantity(s) updated to cart successfully.");
                    } else {
                        System.out.println("Failed to update to cart.");
                    }
                    
                    return;
                }
            }
       }catch(SQLException e){
           e.printStackTrace();
       }
        
    }
    // nak ganti terus item quantity barang tu
    
    public static void replaceExistingItem(String username, String Item_code){
        System.out.println("");
        
        try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart where username=Username";
          ResultSet result= statement.executeQuery(query);
          while (result.next()) {
              
                if (result.getString(1).equals(username) && result.getString(2).equals(Item_code)) {
                    
                    System.out.print("Please enter the quantity that you wish to replace into the existing product: ");
                    int x =scan.nextInt();
                    String insertQuery = "UPDATE shopping_cart SET Item_quantity = ? WHERE Item_code = ?";
                    PreparedStatement preparedStatement = connect.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, x);
                    preparedStatement.setString(2,Item_code);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Quantity(s) updated to cart successfully.");
                    } else {
                        System.out.println("Failed to update to cart.");
                        }
                    
                    return;
                }
                else 
                    System.out.println("item not found");
            }
       }catch(SQLException e){
           e.printStackTrace();
       }
        
    }
    public static void DisplayCart(String username) throws SQLException{
        System.out.println("");
        
        
       try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart where username=Username";
          ResultSet result= statement.executeQuery(query);
          System.out.println("Your Shopping Cart: ");
                      if (result.next()) {
                // If a row was returned, create a ResultSetMetaData object to get metadata about the columns
                ResultSetMetaData metadata = result.getMetaData();

                System.out.print("");

                // Print column values 
                do {
                    for (int i = 2; i <= metadata.getColumnCount(); i++) {
                        System.out.print(result.getString(i) + "\t");
                    }
                    System.out.println();
                } while (result.next());
            } else {
                System.out.println("No item in your cart add now");
            }

       }catch(SQLException e){
           e.printStackTrace();
       }

    }
    
    public static void AddtoCart(String username ,String Item_code) throws SQLException{
        System.out.println("");

        try (Connection connect = DriverManager.getConnection(url, user, pass)) {
            Statement statement = connect.createStatement();
            String query ="SELECT * FROM shopping_cart where username=Username";
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
               
                if (result.getString(1).equals(username) && result.getString(3).equals(Item_code)){
                    System.out.println("The product that you want to add has " +result.getInt(4)+ " already.");
                    System.out.print(" Type The Quantity of " + Item_code + " : ");

                    int quantity = scan.nextInt();
                    if (quantity == 0) {
                        return;
                    }
                    addExistingItem(username, Item_code,quantity);
                    return;
                }
                
            }
            System.out.print(" Type The Quantity of " + Item_code + " : ");

            int quantity = scan.nextInt();
            if (quantity == 0) {
                return;
            }

            String insertQuery = "INSERT INTO shopping_cart (Username,Item_code,Item_quantity) VALUES ( ?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(insertQuery);

            // Set the values for the parameters in the prepared statement
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, Item_code);
            preparedStatement.setInt(3, quantity);
// Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item(s) added to cart successfully.");
            } else {
                System.out.println("Failed to add to cart.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void Deleteitem(String username, String Item_code){
        System.out.println("");
        
        System.out.print("Type the quantity of "+Item_code+" you want to remove from your cart: ");
        int delete=scan.nextInt();
         try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart WHERE username=Username";
          ResultSet result= statement.executeQuery(query);
          while (result.next()) {
              
                if (result.getString(1).equals(username) && result.getString(2).equals(Item_code)) {
                    
                    if(delete> result.getInt(3)){
                        System.out.println("The Amount you ask exceed the actual amount of "+Item_code);
                        System.out.println("Press 0 if you want to exit or press 1 to continue");
                        int y=scan.nextInt();
                        if (y==0)
                            return;
                        else{
                            System.out.println("Please enter suitable amount");
                            Deleteitem(username, Item_code);
                            return;
                        }
                    }
                    String insertQuery = "UPDATE shopping_cart SET Item_quantity = Item_quantity - ? WHERE Item_code = ?";
                    PreparedStatement preparedStatement = connect.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, delete);
                    preparedStatement.setString(2,Item_code);
                    
                    int rowsAffected = preparedStatement.executeUpdate();
                    
                    
                    if(result.getInt(3)==delete ){
                       PreparedStatement preparedStatement2 = connect.prepareStatement   ("DELETE  FROM shopping_cart  WHERE Item_quantity <= 0");
                       preparedStatement2.executeUpdate();
                    }
                    
                      
                   if (rowsAffected > 0) {
                        System.out.println("Cart updated successfully.");
                        
                    } else {
                        System.out.println("Failed to update to cart.");
                    }
                    
                    return;
                }
                DisplayCart(username);
            }
       }catch(SQLException e){ e.printStackTrace();
       }
         
    }
}

