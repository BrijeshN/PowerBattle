package powerbattle;

import javax.swing.*;

public class PowerBattle {
    public PowerBattle() {
        JFrame frame = new JFrame("Power Battle");
        frame.add(new Screen());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    
}
    public static void main(String[] args) {
        new PowerBattle();
        
    }
    
}
