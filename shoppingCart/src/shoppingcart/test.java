/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shoppingcart;

import java.sql.SQLException;
import static shoppingcart.BasicShoppinngCart.*;


public class test {
    public static void main(String[] args) {
       try {
           String []n ={"iskandar"};
           viewCart hi = new viewCart(n);
           // DisplayCart("iskandar");
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
}
