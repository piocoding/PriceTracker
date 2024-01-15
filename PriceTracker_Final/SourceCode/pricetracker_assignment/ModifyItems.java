/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricetracker_assignment;

/**
 *
 * @author user
 */
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

public class ModifyItems {

    //private String itemCode;
    public ModifyItems(String code) {
        //String itemCode = code;
        
        Scanner keyboard = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. Modify item group. \n2. Modify item category. \n3. Cancel.");
            System.out.print("Enter your choice : ");
            int ans = 0;
            try { 
                ans = keyboard.nextInt();
            } catch (Exception e){
                keyboard.next(); // consume the invalid input to prevent infinite loop 
                //caused by repeatedly attempting to read the same invalid input.
            }
            
            if (ans == 1) {
                editGroup(code);
                break;
            }
            else if (ans == 2) {
                editCategory(code);
                break;
            }
            else if (ans == 3) {
                return;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }
    }
    
    public void editGroup(String code) {
        String itemCode = code;
        Scanner keyboard = new Scanner(System.in);
        
        System.out.println();
        System.out.print("Enter new item group : ");
        
        String newGroup = keyboard.nextLine();
        
        String url = "jdbc:mysql://localhost:3306/lookup_item";
        String username = "root";
        String password = "Nurulizzani20.";
        
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE lookup_item SET item_group = '" + newGroup + "' WHERE item_code = '" + itemCode + "'");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully changed item group.\n");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed changing the item group.");
        }
        
        try {
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lookup_item", "root", "Nurulizzani20.");
           
           Statement stmt = (Statement) con.createStatement();
           
           String SQL = "SELECT * FROM lookup_item WHERE item_code= '" + itemCode +"'";
           
           ResultSet rs = stmt.executeQuery(SQL);
           
           while (rs.next()) {
               System.out.println("\nItem : " + rs.getString("item"));
               System.out.println("Unit : " + rs.getString("unit"));
               System.out.println("Item Group : " + rs.getString("item_group"));
               System.out.println("Item Category : " + rs.getString("item_category"));
           }
           rs.close();
           stmt.close();
           con.close();  
        }
        catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
        }
    }
    
    public void editCategory(String code) {
        String itemCode = code;
        Scanner keyboard = new Scanner(System.in);
        
        System.out.println();
        System.out.print("Enter new item category : ");
        
        String newCategory = keyboard.nextLine();
        
        String url = "jdbc:mysql://localhost:3306/lookup_item";
        String username = "root";
        String password = "Nurulizzani20.";
        
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE lookup_item SET item_category = '" + newCategory + "' WHERE item_code = '" + itemCode + "'");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully changed item category.\n");
        }
        catch (Exception e) {
            System.out.println("Failed changing the item category.");
        }
        
        try {
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lookup_item", "root", "Nurulizzani20.");
           
           Statement stmt = (Statement) con.createStatement();
           
           String SQL = "SELECT * FROM lookup_item WHERE item_code= '" + itemCode +"'";
           
           ResultSet rs = stmt.executeQuery(SQL);
           
           while (rs.next()) {
               System.out.println("\nItem : " + rs.getString("item"));
               System.out.println("Unit : " + rs.getString("unit"));
               System.out.println("Item Group : " + rs.getString("item_group"));
               System.out.println("Item Category : " + rs.getString("item_category"));
           }
           rs.close();
           stmt.close();
           con.close();  
        }
        catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
        }
    }
}
