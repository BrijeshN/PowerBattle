package powerbattle;


public class PowerBattle {
    
    public static void main(String[] args) {
        
        // Diplays window on the screen with the follwing title, width and height
        // Create Game object and store it object called game
        System.out.println("Created By: Brijesh Nayak and Da Lin");
        Game game = new Game("Power Battle", 1000, 685);
        
        // Call start mathod of the Game, which will call other methods
        game.start();
        
    }
    
}
