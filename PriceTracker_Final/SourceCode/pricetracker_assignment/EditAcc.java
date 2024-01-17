package pricetracker_assignment;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class EditAcc {
    
    public EditAcc(String[] u){
        Scanner scn = new Scanner(System.in);
        
        while (true){
            System.out.print("1. Change username. \n2. Change password. \n\nEnter your choice: ");
            int ans = 0;
            try { 
                ans = scn.nextInt();
            } catch (Exception e){ 
                scn.next(); // consume the invalid input to prevent infinite loop 
                //caused by repeatedly attempting to read the same invalid input.
            }
        
            if ( ans == 1 ) {
                editUser(u);
                break;
            }
            else if ( ans == 2 ) {
                editPass(u);
                break;
            }
            else System.out.println("invalid choice.\n");
        }
    }
    
    public void editUser(String[] u){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("\nOld username: " + u[0]);
        System.out.print("Enter new username: ");
        
        String newUser = scn.nextLine();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Nurulizzani20.";
        
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET username = '" + newUser + "' WHERE username = '" + u[0] + "'");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully changed username.\n");
        }
        catch (Exception e) {
            System.out.println("There exists another user with the same username.\n");
            return;
        }
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pricecatcher-2023-08",username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE shopping_cart SET Username = '" + newUser + "' WHERE username = '" + u[0] + "'");
            statement.executeUpdate();
            connection.close();
            u[0] = newUser;
        }
        catch (Exception e) {
            System.out.println("Error updating username in shopping cart.\n");
        }
        
    }
    
    public void editPass(String[] u){
        Scanner scn = new Scanner(System.in);
        
        System.out.print("Enter new password: ");
        
        String newPass = scn.nextLine();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Nurulizzani20.";
        
        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET password = '" + newPass + "' WHERE username = '" + u[0] + "'");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully changed password.\n");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

