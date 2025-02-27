package Question_5.src; 

import javax.swing.*; 
import java.awt.*; 

public class GraphVisualizer { // Defined GraphVisualizer class
    public static void displayGraph() { // Defined displayGraph method
        JFrame frame = new JFrame("Network Graph"); // Created JFrame with title
        frame.setSize(400, 400); // Set frame size
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set default close operation

        JPanel panel = new JPanel() { // Created JPanel
            @Override
            protected void paintComponent(Graphics g) { // Overridden paintComponent method
                super.paintComponent(g); // Called super method
                g.drawOval(50, 50, 50, 50); // Node A
                g.drawOval(150, 50, 50, 50); // Node B
                g.drawOval(100, 150, 50, 50); // Node C

                g.drawLine(75, 75, 175, 75); // A - B
                g.drawLine(75, 75, 125, 175); // A - C
                g.drawLine(175, 75, 125, 175); // B - C
            }
        };

        frame.add(panel); // Added panel to frame
        frame.setVisible(true); // Set frame visibility to true
    }
}