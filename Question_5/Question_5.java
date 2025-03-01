package Question_5; 

import java.awt.*; // Importing AWT classes for GUI components
import java.awt.event.*; // Importing AWT event classes
import javax.swing.*; // Importing Swing classes for GUI components
import java.util.*; // Importing utility classes

public class Question_5 extends JPanel { // Main class extending JPanel
    private final ArrayList<Node> nodes = new ArrayList<>(); // List to store nodes
    private final ArrayList<Edge> edges = new ArrayList<>(); // List to store edges
    private final JButton optimizeButton, addEdgeButton; // Buttons for optimizing and adding edges
    private Node selectedNode1, selectedNode2; // Selected nodes for edge creation

    public Question_5() { // Constructor for the class
        setPreferredSize(new Dimension(600, 400)); // Set preferred size of the panel
        setBackground(Color.WHITE); // Set background color to white
        addMouseListener(new MouseAdapter() { // Add mouse listener for node creation
            public void mousePressed(MouseEvent e) { // Mouse pressed event
                nodes.add(new Node(e.getX(), e.getY(), nodes.size())); // Add new node at mouse position
                repaint(); // Repaint the panel
            }
        });

        optimizeButton = new JButton("Optimize Network"); // Create optimize button
        optimizeButton.addActionListener(e -> optimizeNetwork()); // Add action listener to optimize network

        addEdgeButton = new JButton("Add Edge"); // Create add edge button
        addEdgeButton.addActionListener(e -> addEdge()); // Add action listener to add edge
    }

    private void addEdge() { // Method to add an edge
        if (nodes.size() < 2) // Check if there are at least two nodes
            return; // Exit if not
        String input = JOptionPane.showInputDialog("Enter edge (node1 index, node2 index, cost, bandwidth):"); // Prompt
                                                                                                             
        if (input != null) { // Check if input is not null
            String[] parts = input.split(","); // Split input by commas
            if (parts.length == 4) { // Check if there are four parts
                try {
                    int i1 = Integer.parseInt(parts[0].trim()); // Parse first node index
                    int i2 = Integer.parseInt(parts[1].trim()); // Parse second node index
                    int cost = Integer.parseInt(parts[2].trim()); // Parse cost
                    int bandwidth = Integer.parseInt(parts[3].trim()); // Parse bandwidth
                    if (i1 >= 0 && i1 < nodes.size() && i2 >= 0 && i2 < nodes.size() && i1 != i2) { // Validate indices
                        edges.add(new Edge(nodes.get(i1), nodes.get(i2), cost, bandwidth)); // Add new edge
                        repaint(); // Repaint the panel
                    }
                } catch (NumberFormatException ignored) { // Catch number format exceptions
                }
            }
        }
    }

    private void optimizeNetwork() { // Method to optimize the network
        edges.clear(); // Clear existing edges
        if (nodes.size() < 2) // Check if there are at least two nodes
            return; // Exit if not

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost)); // Create priority queue for
                                                                                            // edges
        for (int i = 0; i < nodes.size(); i++) { // Loop through nodes
            for (int j = i + 1; j < nodes.size(); j++) { // Loop through nodes to create edges
                int cost = (int) Math.sqrt( // Calculate cost based on distance
                        Math.pow(nodes.get(i).x - nodes.get(j).x, 2) + Math.pow(nodes.get(i).y - nodes.get(j).y, 2));
                pq.add(new Edge(nodes.get(i), nodes.get(j), cost, 10)); // Add edge to priority queue
            }
        }

        DisjointSet ds = new DisjointSet(nodes.size()); // Create disjoint set for union-find
        while (!pq.isEmpty() && edges.size() < nodes.size() - 1) { // While there are edges and not enough edges
            Edge edge = pq.poll(); // Get the edge with the lowest cost
            int u = nodes.indexOf(edge.n1); // Get index of first node
            int v = nodes.indexOf(edge.n2); // Get index of second node
            if (ds.find(u) != ds.find(v)) { // Check if nodes are in different sets
                ds.union(u, v); // Union the sets
                edges.add(edge); // Add edge to the result
            }
        }
        repaint(); // Repaint the panel
    }

    @Override
    protected void paintComponent(Graphics g) { // Override paintComponent method
        super.paintComponent(g); // Call superclass method
        for (Edge e : edges) { // Loop through edges
            g.setColor(Color.BLACK); // Set color to black
            g.drawLine(e.n1.x, e.n1.y, e.n2.x, e.n2.y); // Draw edge line
            g.drawString("Cost: " + e.cost + ", BW: " + e.bandwidth, (e.n1.x + e.n2.x) / 2, (e.n1.y + e.n2.y) / 2); // Draw
                                                                                                                    // edge
                                                                                                                    // info
        }
        for (Node n : nodes) { // Loop through nodes
            g.setColor(Color.BLUE); // Set color to blue
            g.fillOval(n.x - 5, n.y - 5, 10, 10); // Draw node
            g.setColor(Color.BLACK); // Set color to black
            g.drawString("N" + n.id, n.x + 5, n.y - 5); // Draw node ID
        }
    }

    public static void main(String[] args) { // Main method
        JFrame frame = new JFrame("Network Optimizer"); // Create frame
        Question_5 panel = new Question_5(); // Create panel
        frame.setLayout(new BorderLayout()); // Set layout
        frame.add(panel, BorderLayout.CENTER); // Add panel to frame
        JPanel buttonPanel = new JPanel(); // Create button panel
        JButton btnOptimize = new JButton("Optimize Network"); // Create optimize button
        JButton btnAddEdge = new JButton("Add Edge"); // Create add edge button
        btnOptimize.addActionListener(e -> panel.optimizeNetwork()); // Add action listener to optimize button
        btnAddEdge.addActionListener(e -> panel.addEdge()); // Add action listener to add edge button
        buttonPanel.add(btnOptimize); // Add optimize button to panel
        buttonPanel.add(btnAddEdge); // Add add edge button to panel
        frame.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to frame
        frame.pack(); // Pack the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        frame.setVisible(true); // Make frame visible
    }
}

class Node { // Node class
    int x, y, id; // Node properties

    Node(int x, int y, int id) { // Constructor for Node
        this.x = x; // Set x coordinate
        this.y = y; // Set y coordinate
        this.id = id; // Set node ID
    }
}

class Edge { // Edge class
    Node n1, n2; // Nodes connected by the edge
    int cost, bandwidth; // Edge properties

    Edge(Node n1, Node n2, int cost, int bandwidth) { // Constructor for Edge
        this.n1 = n1; // Set first node
        this.n2 = n2; // Set second node
        this.cost = cost; // Set cost
        this.bandwidth = bandwidth; // Set bandwidth
    }
}

class DisjointSet { // Disjoint set class for union-find
    private final int[] parent, rank; // Parent and rank arrays

    DisjointSet(int size) { // Constructor for DisjointSet
        parent = new int[size]; // Initialize parent array
        rank = new int[size]; // Initialize rank array
        for (int i = 0; i < size; i++) // Loop to set parent to itself
            parent[i] = i; // Each node is its own parent
    }

    int find(int i) { // Find method
        if (parent[i] != i) // If not the root
            parent[i] = find(parent[i]); // Path compression
        return parent[i]; // Return root
    }

    void union(int i, int j) { // Union method
        int ri = find(i), rj = find(j); // Find roots
        if (ri != rj) { // If roots are different
            if (rank[ri] > rank[rj]) // Union by rank
                parent[rj] = ri; // Make one root the parent of the other
            else if (rank[ri] < rank[rj]) // If ranks are different
                parent[ri] = rj; // Make the other root the parent
            else { // If ranks are the same
                parent[rj] = ri; // Make one root the parent
                rank[ri]++; // Increase rank
            }
        }
    }
}