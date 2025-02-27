package Question_5.src; // Added package declaration

import javax.swing.*; // Imported necessary classes

public class Main { // Defined Main class
    public static void main(String[] args) { // Defined main method
        SwingUtilities.invokeLater(() -> { // Used SwingUtilities to ensure GUI updates are done on the Event Dispatch
                                           // Thread
            JFrame frame = new JFrame("Network Optimization Tool"); // Created JFrame with title
            frame.setSize(400, 300); // Set frame size
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

            JButton visualizeButton = new JButton("Visualize Network"); // Created visualize button
            JButton optimizeButton = new JButton("Optimize Network"); // Created optimize button
            JButton shortestPathButton = new JButton("Calculate Shortest Path"); // Created shortest path button

            JPanel panel = new JPanel(); // Created JPanel
            panel.add(visualizeButton); // Added visualize button to panel
            panel.add(optimizeButton); // Added optimize button to panel
            panel.add(shortestPathButton); // Added shortest path button to panel

            frame.add(panel); // Added panel to frame
            frame.setVisible(true); // Set frame visibility to true

            // Event Listeners
            visualizeButton.addActionListener(e -> GraphVisualizer.displayGraph()); // Added action listener to
                                                                                    // visualize button
            optimizeButton.addActionListener(e -> NetworkOptimizer.optimizeNetwork()); // Added action listener to
                                                                                       // optimize button
            shortestPathButton.addActionListener(e -> ShortestPathCalculator.findShortestPath()); // Added action
                                                                                                  // listener to
                                                                                                  // shortest path
                                                                                                  // button
        }); 
    } 
} 