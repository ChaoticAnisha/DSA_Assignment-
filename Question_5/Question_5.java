package Question_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Question_5 {
    // Main frame for the application
    private JFrame frame;
    // Panel for drawing the network graph
    private NetworkGraphPanel graphPanel;
    // Input fields for costs and bandwidths
    private JTextField costField, bandwidthField;
    // Button to optimize the network
    private JButton optimizeButton;

    public Question_5() {
        // Initialize the main frame
        frame = new JFrame("Network Optimizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Initialize the graph panel
        graphPanel = new NetworkGraphPanel();
        frame.add(graphPanel, BorderLayout.CENTER);

        // Panel for controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Input fields for costs and bandwidths
        costField = new JTextField(10);
        bandwidthField = new JTextField(10);
        controlPanel.add(new JLabel("Cost:"));
        controlPanel.add(costField);
        controlPanel.add(new JLabel("Bandwidth:"));
        controlPanel.add(bandwidthField);

        // Button to optimize the network
        optimizeButton = new JButton("Optimize Network");
        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optimizeNetwork();
            }
        });
        controlPanel.add(optimizeButton);

        // Add control panel to the frame
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Method to optimize the network
    private void optimizeNetwork() {
        // Retrieve user input for costs and bandwidths
        int cost = Integer.parseInt(costField.getText());
        int bandwidth = Integer.parseInt(bandwidthField.getText());

        // Call optimization algorithm
        List<Connection> optimizedConnections = optimizeConnections(cost, bandwidth);

        // Update the graph panel with optimized connections
        graphPanel.updateGraph(optimizedConnections);
    }

    // Simple optimization logic (placeholder)
    private List<Connection> optimizeConnections(int cost, int bandwidth) {
        // For now, return the existing connections
        return graphPanel.getConnections();
    }

    // Method to calculate the shortest path between two nodes
    private List<Point> calculateShortestPath(Point start, Point end) {
        // Implement Dijkstra's algorithm or any other shortest path algorithm
        // Return the list of points representing the shortest path
        return new ArrayList<>(); // Placeholder
    }

    public static void main(String[] args) {
        // Start the application
        SwingUtilities.invokeLater(() -> new Question_5());
    }
}

// Panel for drawing the network graph
class NetworkGraphPanel extends JPanel {
    private List<Connection> connections;
    private List<Point> nodes; // List to store nodes

    public NetworkGraphPanel() {
        connections = new ArrayList<>();
        nodes = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create a new node at the clicked position
                Point newNode = e.getPoint();
                nodes.add(newNode); // Add the new node to the list
                if (nodes.size() > 1) {
                    // Create a connection between the last two nodes
                    Point start = nodes.get(nodes.size() - 2);
                    Point end = newNode;
                    int cost = 1; // Placeholder cost
                    int bandwidth = 1; // Placeholder bandwidth
                    connections.add(new Connection(start, end, cost, bandwidth));
                }
                repaint();
            }
        });
    }

    // Method to update the graph with new connections
    public void updateGraph(List<Connection> newConnections) {
        connections = newConnections;
        repaint(); // Repaint the panel to show updated graph
    }

    // Method to get current connections
    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the network graph here
        for (Connection conn : connections) {
            g.drawLine(conn.start.x, conn.start.y, conn.end.x, conn.end.y);
            g.drawString("Cost: " + conn.cost + ", Bandwidth: " + conn.bandwidth,
                    (conn.start.x + conn.end.x) / 2,
                    (conn.start.y + conn.end.y) / 2);
        }
        // Draw nodes
        for (Point node : nodes) {
            g.fillOval(node.x - 5, node.y - 5, 10, 10); // Draw nodes as small circles
        }
    }
}

// Class to represent a connection between nodes
class Connection {
    Point start;
    Point end;
    int cost;
    int bandwidth;

    public Connection(Point start, Point end, int cost, int bandwidth) {
        this.start = start;
        this.end = end;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }
}
