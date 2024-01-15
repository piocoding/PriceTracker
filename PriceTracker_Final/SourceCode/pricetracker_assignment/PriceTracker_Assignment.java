/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pricetracker_assignment;

import java.sql.SQLException;

/**
 *1
 * 
 * @author user
 */
public class PriceTracker_Assignment {
    
    public static void main(String[] args) throws SQLException {
        
        String[] username = new String[1];
        Login login = new Login(username);
        
        MainMenu mainMenu = new MainMenu(username);
        
       
//        FuzzySearch search = new FuzzySearch();
//        search.userInput();
        
    }
}
