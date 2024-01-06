/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricetracker_assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author nurulizzani
 */
public class FuzzySearch {
    private String product;
    private int a = 1;
    private int count;
    private int ans;
    private int i = 0;
    
    public String userInput() {
       Scanner keyboard = new Scanner(System.in);
       String[] action =  new String[30];
       int count = 0;
       
       try {
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lookup_item", "root", "Nurulizzani20.");
           
           Statement stmt = (Statement) con.createStatement();
           
           System.out.print("Enter your product : ");
           product = keyboard.next();
           
           String SQL = "SELECT * FROM lookup_item WHERE item LIKE '%" + product + "%'";
           
           ResultSet rs = stmt.executeQuery(SQL);
           
           System.out.println();
           while (rs.next()) {
               System.out.println(a + ". Product : " + rs.getString("item"));
               System.out.println("Unit : " + rs.getString("unit"));
               action[i] = rs.getString(1);
               action[i] = rs.getString(2);
               i++;
               a++;
               count++;
           }
           con.close();
       } catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
       }
       
       String choice = ChoiceUI(action, count);
       return choice;
    }
    
    public  String ChoiceUI(String[] array, int n) {
        Scanner keyboard = new Scanner (System.in);

        System.out.print("\nEnter your choice of product : ");
        int ans = keyboard.nextInt() - 1;

        System.out.println("\nSelect actions : ");
        System.out.println("1. View item details");
        //System.out.println("2. Modify item details");
        System.out.println("2. View top 5 cheapest seller");
        System.out.println("3. View price trend");
        System.out.println("4. Add to shopping cart");
        System.out.print("Enter your choice : ");
        int actionChoice = keyboard.nextInt();
        
        if (actionChoice == 1) {
        try {
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lookup_item", "root", "Nurulizzani20.");
           
           Statement stmt = (Statement) con.createStatement();
           
           String SQL = "SELECT * FROM lookup_item WHERE item= '" + array[ans] +"'";
           
           ResultSet rs = stmt.executeQuery(SQL);
           
           while (rs.next()) {
               System.out.println("\nItem : " + rs.getString("item"));
               System.out.println("Unit : " + rs.getString("unit"));
               System.out.println("Item Group : " + rs.getString("item_group"));
               System.out.println("Item Category : " + rs.getString("item_category"));
           }
       } catch (Exception e) {
           System.out.println("Error : " + e.getMessage());
       }
       }
        return array[ans];
    }
}
