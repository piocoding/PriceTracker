import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class EditAcc {
    
    public EditAcc(String u){
        Scanner scn = new Scanner(System.in);
        
        while (true){
            System.out.println("1. Change username. \n2.Change password.");
            int ans = scn.nextInt();
        
            if ( ans == 1 ) {
                editUser(u);
                break;
            }
            else if ( ans == 2 ) {
                editPass(u);
                break;
            }
            else System.out.println("invalid.");
        }
    }
    
    public void editUser(String u){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("Old username :" + u);
        System.out.print("Enter new username: ");
        
        String newUser = scn.nextLine();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Allahswt24434!";
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            
            ResultSet resultSet = statement.executeQuery("UPDATE TABLE accounts SET username = '" + newUser + "' WHERE username = '" + u + "'\n SELECT * FROM accounts;");
            
            connection.close();
            
            System.out.println("Successfully changed username.");
            u = newUser;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void editPass(String u){
        Scanner scn = new Scanner(System.in);
        
        System.out.print("Enter new password: ");
        
        String newPass = scn.nextLine();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Allahswt24434!";
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            
            ResultSet resultSet = statement.executeQuery("UPDATE TABLE accounts SET password = '" + newPass + "' WHERE username = '" + u + "'\n SELECT * FROM accounts;");
            
            connection.close();
            
            System.out.println("Successfully changed password.");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
