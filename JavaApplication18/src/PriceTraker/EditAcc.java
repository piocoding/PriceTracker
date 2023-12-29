import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class EditAcc {
    
    public EditAcc(String[] u){
        Scanner scn = new Scanner(System.in);
        
        while (true){
            System.out.println("1. Change username. \n2. Change password.");
            int ans = scn.nextInt();
        
            if ( ans == 1 ) {
                editUser(u);
                break;
            }
            else if ( ans == 2 ) {
                editPass(u);
                break;
            }
            else System.out.println("invalid choice.");
        }
    }
    
    public void editUser(String[] u){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("Old username: " + u[0]);
        System.out.print("Enter new username: ");
        
        String newUser = scn.nextLine();
        
        String url = "jdbc:derby://localhost:1527/pricetrackerDB";
        String username = "javasofia";
        String password = "javasofia";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE APP.accounts SET username = '" + newUser + "' WHERE username = '" + u[0] + "'");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully changed username.");
            u[0] = newUser;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void editPass(String[] u){
        Scanner scn = new Scanner(System.in);
        
        System.out.print("Enter new password: ");
        
        String newPass = scn.nextLine();
        
        String url = "jdbc:derby://localhost:1527/pricetrackerDB";
        String username = "javasofia";
        String password = "javasofia";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("UPDATE APP.accounts SET password = '" + newPass + "' WHERE username = '" + u[0] + "'");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully changed password.");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

