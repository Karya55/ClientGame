package tr.edu.sehir.oop;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
    	
        JFrame frame = new JFrame("A World of Balls-Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new BallWorld(640, 480)); // BallWorld is a JPanel
        frame.pack();            // Preferred size of BallWorld
        frame.setVisible(true);  // Show it
    }
}
