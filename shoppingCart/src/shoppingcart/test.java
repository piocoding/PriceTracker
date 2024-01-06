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
            viewCart k = new viewCart();
//           AddtoCart("iskandar", "BETIK BIASA","16");
//           AddtoCart("iskandar", "PISANG EMAS","19");
//           AddtoCart("iskandar", "LAI KUNING (SAIZ M)","31");
//           AddtoCart("iskandar", "CILI MERAH - KULAI ","93");
        // AddtoCart("iskandar", "AYAM HIDUP","3");
       //  AddtoCart("iskandar", "AYAM super","2");
//          AddtoCart("iskandar", "TIMUN","113");
         //AddtoCart("iskandar", "Tembikai susu","24");
         //Deleteitem("iskandar","5","MILO");^
           //replaceExistingItem("iskandar", "MILO");
         //  AddtoCart("adwad", "AYAM SUPER");
         //  AddtoCart("adwad", "MILO");
         // DisplayCart("iskandar");
          // DisplayCart("adwad");
           //Deleteitem("iskandar", "AYAM SUPER");
          // Deleteitem("adwad", "AYAM SUPER");
            // DisplayCart("afq");
           // replaceExistingItem("iskandar","AYAM SUPER");
            // addd quatity in existing item
            //addExistingItem("iskandar","AYAM SUPER",9);
           //    DisplayCart("adwad");
            //AddtoCart("iskandar", "MILO")
           k.ViewbyShop("iskandar");
           // DisplayCart("iskandar");
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
}
