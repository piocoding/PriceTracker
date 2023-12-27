import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

//make sure accounts table is already created 
// 2 columns, (username, PRIMARY KEY, varchar(20)), (password, varchar(20))
public class Login {
    
    public Login(String u){
        Scanner scn = new Scanner(System.in);
        
        boolean status = false;
        while ( status == false ) {
            System.out.println("1. Login \n2. Registration");
            int ans = scn.nextInt();
            if ( ans == 1 ) {
                LoginUI(status, u);
            }
            else if ( ans == 2 ) {
                RegistrationUI(status, u);
            }
            else {
                System.out.println("invalid choice.");
            }
        }
    }
    
    public void LoginUI(boolean status, String u){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("–Login–––");
        System.out.print("Username: ");
        String user = scn.next();
        u = user;
        System.out.print("Password: ");
        String pass = scn.next();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Allahswt24434!";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE username = '" + user + "' and password = '" + pass + "';");
            
            if (resultSet.next() ){
                System.out.println("Successfully logged in.");
                connection.close();
                status = true;
                
            } else {
                System.out.println("Wrong username or password.");
                connection.close();
            }
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void RegistrationUI(boolean status){
        Scanner scn = new Scanner(System.in);
        
        System.out.println("–Registeration–––");
        System.out.print("Set Username: ");
        String user = scn.next();
        System.out.print("Set Password: ");
        String pass = scn.next();
        
        String url = "jdbc:mysql://localhost:3306/accounts";
        String username = "root";
        String password = "Allahswt24434!";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT INTO APP.TRYACCOUNTS VALUES ('" + user + "', '" + pass + "')\n SELECT * FROM accounts;");
            connection.close();
            System.out.println("Successfully registered account.");
            LoginUI(status, u);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR creating account. (There might already exist an account with the same username.)");
        }
    }
}
