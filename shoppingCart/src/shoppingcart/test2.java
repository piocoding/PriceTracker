/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingcart;

import java.sql.Connection; // Represents a connection to the database
import java.sql.DriverManager; // Helps in obtaining a connection to the database
import java.sql.PreparedStatement; // Used for prepared statements
import java.sql.Statement; // Used for executing SQL statements
import java.sql.ResultSet; // Represents a table of data resulting from a query
import java.sql.SQLException; // Handles SQL exceptions
import java.util.Scanner;


public class test2 {
      static String url = "jdbc:mysql://localhost:3306/csv_db 7";
    static String user = "iskandar";
    static String pass = "123";
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
       try {
            
           AddtoCart("iskandar", "AYAM SUPER");
//            Deleteitem("afq","SAYUR");
           AddtoCart("iskandar", "MILO");
         //  AddtoCart("adwad", "AYAM SUPER");
         //  AddtoCart("adwad", "MILO");
           DisplayCart("iskandar");
          // DisplayCart("adwad");
           //Deleteitem("iskandar", "AYAM SUPER");
          // Deleteitem("adwad", "AYAM SUPER");
            // DisplayCart("afq");
           //    DisplayCart("adwad");
            AddtoCart("iskandar", "MILO");
            DisplayCart("iskandar");
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public static void addExistingItem(String username, String Item_code, int quantity){
        
        try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart where username=Username";
          ResultSet result= statement.executeQuery(query);
          while (result.next()) {
              
                if (result.getString(2).equals(username) && result.getString(3).equals(Item_code)) {
                    
                   
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
    
    public static void replaceExistingItem(String username, String Item_code){
        
        try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart where username=Username";
          ResultSet result= statement.executeQuery(query);
          while (result.next()) {
              
                if (result.getString(2).equals(username) && result.getString(3).equals(Item_code)) {
                    
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
            }
       }catch(SQLException e){
           e.printStackTrace();
       }
        
    }
    public static void DisplayCart(String username) throws SQLException{
        
        
       try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart where username=Username";
          ResultSet result= statement.executeQuery(query);
          System.out.println("Your Shopping Cart: ");
          while(result.next()){
              if(result.getString(2).equals(username)){
               String items="";
               for (int i = 4; i >= 3; i--) {
                  items+= result.getString(i)+" ";
              }
              
               System.out.println(items);
              }
          }
       }catch(SQLException e){
           e.printStackTrace();
       }

    }
    
    public static void AddtoCart(String username ,String Item_code) throws SQLException{

        try (Connection connect = DriverManager.getConnection(url, user, pass)) {
            Statement statement = connect.createStatement();
            String query ="SELECT * FROM shopping_cart where username=Username";
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
               
                if (result.getString(2).equals(username) && result.getString(3).equals(Item_code)){
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
        
        System.out.print("Type the quantity of "+Item_code+" you want to remove from your cart: ");
        int delete=scan.nextInt();
         try{
          Connection connect = DriverManager.getConnection(url,user,pass);
          Statement statement = connect.createStatement();
          String query ="SELECT * FROM shopping_cart WHERE username=Username";
          ResultSet result= statement.executeQuery(query);
          while (result.next()) {
              
                if (result.getString(2).equals(username) && result.getString(3).equals(Item_code)) {
                    
                    if(delete>result.getInt(4)){
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
                    
                    
                    if(result.getInt(4)==delete ){
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

