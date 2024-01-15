/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricetracker_assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class SelectedItem {
    private String itemCode;
    private String[] user = new String[1];
    
    public SelectedItem(String[] u,String code) throws SQLException{
        user[0] = u[0];
        itemCode = code;
        
        ChoiceUI();
    }
    
    public void ChoiceUI() throws SQLException{
        Scanner scn = new Scanner(System.in);
        System.out.println(
                    "1. View item details\n" + 
                    "2. Modify Item Details\n" + 
                    "3. View top 5 cheapest seller\n" + 
                    "4. View price trend\n" + 
                    "5. Add to shopping cart\n");
            
        System.out.print("Enter your choice: ");
        int choice = 0;
        try { 
            choice = scn.nextInt();
        } catch (Exception e){
            scn.next(); // consume the invalid input to prevent infinite loop 
            //caused by repeatedly attempting to read the same invalid input.
        }
            
        switch (choice){
                case 1:
                    ItemDetails();
                    backUI();
                    break;
                case 2:
                    ModifyItems modify = new ModifyItems(itemCode);
                    backUI();
                    break;
                case 3:
                    CheapestSellers cheapest = new CheapestSellers(itemCode);
                    backUI();
                    break;
                case 4:
                    PriceTrend trend = new PriceTrend(itemCode);
                    backUI();
                    break;
                case 5:
                    BasicShoppinngCart cart = new BasicShoppinngCart();
                    String itemName = getName(itemCode);
                    int itemcode1=Integer.parseInt(itemCode);
                    cart.AddtoCart(user[0],itemName,itemcode1);
                    backUI();
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid choice.");
                    ChoiceUI();
            }
    }
    
    public void ItemDetails(){
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
        } catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
        }
    }
    
    public void backUI() throws SQLException{
        Scanner scn = new Scanner(System.in);
        
        System.out.println("\n1. Back to Item. \n2. Back to Main Menu.");
        System.out.print("\nEnter your choice: ");
        
        int ans = scn.nextInt();
        
        switch(ans){
            case 1:
                ChoiceUI();
                break;
            case 2:
                MainMenu mainmenu = new MainMenu(user);
                return;
            default:
                System.out.println("Invalid choice.");
                backUI();
                break;
        }
    }
    public String getName(String code){
        String name = "";
        Scanner keyboard = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lookup_item", "root", "Nurulizzani20.");
            Statement stmt = (Statement) con.createStatement();
            
            String SQL = "SELECT item FROM lookup_item WHERE item_code = '" + code + "'";
           
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            name = "" + rs.getString(1);
            con.close();
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
        return name;
    }
}

