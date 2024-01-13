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
 * @author nurulizzani
 */
public class FuzzySearch {
    private String product;
    private String[] user = new String[1];
    
    public FuzzySearch(String[] u) throws SQLException{
        user[0] = u[0];
        userInput();
    }
    
    public void userInput() throws SQLException {
        Scanner keyboard = new Scanner(System.in);
        String[] itemCode =  new String[30];
        int  count = 0;
       
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lookup_item", "root", "Nurulizzani20.");
           
            Statement stmt = (Statement) con.createStatement();
           
            System.out.print("Enter your product : ");
            product = keyboard.next();
           
            String SQL = "SELECT * FROM lookup_item WHERE item LIKE '%" + product + "%'";
           
            ResultSet rs = stmt.executeQuery(SQL);
            int i = 0, a = 1;
            System.out.println();
            while (rs.next()) {
                System.out.println(a + ". Product : " + rs.getString("item"));
                System.out.println("Unit : " + rs.getString("unit"));
                itemCode[i] = rs.getString(1);
                i++;
                a++;
                count++;
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        
        if (count==0){
            System.out.println("No product of the name.");
            InvalidName();
            return;
        }
        String choice = ChoiceUI(itemCode, count);
        SelectedItem selected = new SelectedItem(user, choice);
    }
    
    public String ChoiceUI(String[] array, int n) {
        Scanner keyboard = new Scanner (System.in);

        System.out.print("\nEnter your choice of product : ");
        int ans = keyboard.nextInt() - 1;
        
        return array[ans];
    }
    
    public void InvalidName() throws SQLException{
        Scanner scn = new Scanner(System.in);
        System.out.println("\n1. Search Again. \n2. Back to Main Menu.\n");
        System.out.print("Enter your choice: ");
        int ans = scn.nextInt();
        
        switch (ans) {
            case 1:
                userInput();
                break;
            case 2:
                MainMenu mainmenu = new MainMenu(user);
                break;
            default:
                System.out.println("Invalid choice.");
                InvalidName();
                break;
        }
    }
}
