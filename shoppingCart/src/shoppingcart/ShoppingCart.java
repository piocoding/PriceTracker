/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package shoppingcart;
import java.sql.*;
public class ShoppingCart {
    
    ShoppingCart(){
        
    }
    public void addItemToShoppingCart(int userId, String itemName, int itemSubtotal) {
    String sql = "INSERT INTO shopping_cart (user_id, item_name, item_price) VALUES (?, ?, ?)";

    try {
        String url = "jdbc:mysql://localhost:3306/csv_db 7";
        String username = "iskandar";
        String password = "123";

        Connection connection = DriverManager.getConnection(url, username, password);

        String query = "INSERT INTO shopping_cart (user_id, item_name, item_subtotal) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, itemName);
        preparedStatement.setInt(3, itemSubtotal);

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            System.out.println("Product added to shopping cart successfully!");
        } else {
            System.out.println("Failed to add product to shopping cart.");
        }

        preparedStatement.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error while adding item to shopping cart: " + e.getMessage());
    }
}

    
}
