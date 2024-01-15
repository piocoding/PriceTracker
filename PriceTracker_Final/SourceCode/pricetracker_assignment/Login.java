package pricetracker_assignment;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

//make sure accounts table is already created 
// 2 columns, (username, PRIMARY KEY, varchar(20)), (password, varchar(20))
public class Login {
    private boolean status;
    
    public Login(String[] u){
        Scanner scn = new Scanner(System.in);
        
        status = false;
        while ( !status ) {
            System.out.print("1. Login \n2. Registration \nEnter your choice: ");
            int ans = 0;
            try { 
                ans = scn.nextInt();
            } catch (Exception e){
                scn.next(); // consume the invalid input to prevent infinite loop 
                //caused by repeatedly attempting to read the same invalid input.
            }
            if ( ans == 1 ) {
                LoginUI(u);
            }
            else if ( ans == 2 ) {
                RegistrationUI(u);
            }
            else {
                System.out.println("invalid choice.\n");
            }
        }
        
    }
    
    public void LoginUI(String[] u){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("–Login–––");
        System.out.print("Username: ");
        String user = scn.next();
        u[0] = user;
        System.out.print("Password: ");
        String pass = scn.next();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Nurulizzani20.";
        
        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE username = '" + user + "' and password = '" + pass + "'");
            
            if (resultSet.next() ){
                System.out.println("Successfully logged in.\n");
                connection.close();
                status = true;
                
            } else {
                System.out.println("Wrong username or password.\n");
                connection.close();
            }
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void RegistrationUI(String[] u){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("–Registeration–––");
        System.out.print("Set Username: ");
        String user = scn.next();
        System.out.print("Set Password: ");
        String pass = scn.next();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Nurulizzani20.";
        
        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts VALUES ('" + user + "', '" + pass + "')");
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully registered account.\n");
            LoginUI(u);
        }
        catch (Exception e) {
            System.out.println("There exists another user with the same username.\n");
        }
    }
}

