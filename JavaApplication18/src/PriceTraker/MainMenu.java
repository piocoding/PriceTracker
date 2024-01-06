import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    
    public MainMenu() throws SQLException{
        Scanner sc = new Scanner(System.in);
            System.out.println(
                    "1. Browse by Categories\n" + 
                    "2. Search for a Product\n" + 
                    "3. View Shopping Cart\n" + 
                    "4. Account Settings\n" + 
                    "5. Exit\n");
            
            System.out.print("Enter your choice (1/2/3/4/5) : ");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice){
                case 1:
                Category category = new Category();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid choice.");
            }
    }
    
}
