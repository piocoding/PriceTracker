package pricetracker_assignment;

public class Clear {
  Clear(){
    // \033[H: It moves the cursor at the top left corner of the screen or console.
    // \033[2J: It clears the screen from the cursor to the end of the screen.
    System.out.print("\033[H\033[2J");  
    // flush() function that resets the cursor position at the top of the screen.
    System.out.flush();  
  }

}
