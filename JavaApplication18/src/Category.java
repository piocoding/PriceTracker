import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Category {
    public Category() throws SQLException {
        
        String cat = CategorySearch();
        while (cat.equals("invalid"))
            if (cat.equals("main menu")){
                MainMenu mainmenu = new MainMenu();
                return;
            }
        
        String sub = Subcategory(cat);
        while (sub.equals("invalid"))
            if (sub.equals("main menu")){
                MainMenu mainmenu = new MainMenu();
                return;
            }
        
        String item =itemCategory(sub);
        while (item.equals("invalid"))
            if (item.equals("main menu")){
                MainMenu mainmenu = new MainMenu();
                return;
            }
        
        lastChoice(item);
        
        
    }

    public String CategorySearch() throws SQLException {
        String[] category = new String [50];
        int cnt = 0;
        
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myproject","root","Goodmorning04.");
        
        Statement stmt = con.createStatement();
        
        String s = "SELECT DISTINCT item_group FROM lookup_item";     
        ResultSet rs = stmt.executeQuery(s);
                
        int i =0;
        while(rs.next()){
            category[i] = rs.getString(1);
            i++;
            cnt++;
        }
        con.close();  
        
        System.out.print("\nSelect a category: \n");
        String choice = ChoiceUI(category, cnt);
        return choice;
    }

    public String Subcategory(String cat) throws SQLException {
        String[] subcategory = new String[50];
        int cnt = 0;
        
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myproject","root","Goodmorning04.");
        Statement stmt = con.createStatement();
        
        String s = "SELECT DISTINCT item_category FROM lookup_item WHERE item_group = '" + cat +"'";     
        ResultSet rs = stmt.executeQuery(s);
        
        int i = 0;
        while(rs.next()){
            subcategory[i] = rs.getString(1);
            i++;
            cnt++;
        }
        con.close();
        
        System.out.print("\nSelect a subcategory: \n");
        String choice = ChoiceUI(subcategory, cnt);
        return choice; 
    }
    
    public String itemCategory(String sub) throws SQLException{
        String[] item = new String [50];
        int cnt = 0;
        
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myproject","root","Goodmorning04.");
        Statement stmt = con.createStatement();
        
        String s = "SELECT DISTINCT item FROM lookup_item WHERE item_category = '" + sub +"'";     
        ResultSet rs = stmt.executeQuery(s);
        
        int i =0;
        while(rs.next()){
            item[i] = rs.getString(1);
            i++;
            cnt++;
        }
        con.close();
        System.out.println("\nSelect an item: \n");
        String choice = ChoiceUI(item, cnt);
        return choice;

  
    }

    public String ChoiceUI(String[] array, int n){
        Scanner sc = new Scanner(System.in);
        
        int i;
        for(i =0; i<n; i++){
            System.out.println((i+1) + "." + array[i]);
        }
        System.out.println(n+1 + "."+ "Back to main menu");
        
        System.out.print("\nEnter your choice: ");
        int ans = sc.nextInt()-1;
        
        if (ans<0 || ans>n)
            return "invalid";
        if (ans==n)
            return "main menu";
        return array[ans];
           
    }

    public void lastChoice(String item) {
        Scanner sc = new Scanner(System.in);
            System.out.println(
                    "1. View item details\n" + 
                    "2. View top 5 cheapest seller\n" + 
                    "3. View price trend\n" + 
                    "4. Add to shopping cart\n");
            
            System.out.print("Enter your choice (1/2/3/4/5) : ");
            int option = sc.nextInt();
            System.out.println();
            switch (option){
                case 1:
                    System.out.println("hi");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid choice.");
            }
           
    }
    
}
