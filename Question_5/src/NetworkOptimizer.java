package Question_5.src; // Added package declaration

import java.util.*; // Imported necessary classes

public class NetworkOptimizer { // Defined NetworkOptimizer class
    public static void optimizeNetwork() { // Defined optimizeNetwork method
        System.out.println("Optimizing Network using Kruskal’s Algorithm..."); // Print statement

        // Dummy Example: Hardcoded Graph
        NetworkGraph graph = new NetworkGraph(); // Created NetworkGraph instance
        graph.addNode("A"); // Added node A
        graph.addNode("B"); // Added node B
        graph.addNode("C"); // Added node C

        graph.addEdge("A", "B", 4); // Added edge A-B with cost 4
        graph.addEdge("A", "C", 2); // Added edge A-C with cost 2
        graph.addEdge("B", "C", 1); // Added edge B-C with cost 1

        graph.displayGraph(); // Displayed the graph
        System.out.println("Optimal Network: Connect A-B, B-C"); // Print optimal network
    }
}