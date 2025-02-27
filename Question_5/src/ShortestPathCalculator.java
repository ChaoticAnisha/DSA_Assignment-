package Question_5.src; // Added package declaration

import java.util.*; // Imported necessary classes

public class ShortestPathCalculator { // Defined ShortestPathCalculator class
    public static void findShortestPath() { // Defined findShortestPath method
        System.out.println("Finding Shortest Path using Dijkstra’s Algorithm..."); // Print statement

        NetworkGraph graph = new NetworkGraph(); // Created NetworkGraph instance
        graph.addNode("A"); // Added node A
        graph.addNode("B"); // Added node B
        graph.addNode("C"); // Added node C

        graph.addEdge("A", "B", 4); // Added edge A-B with cost 4
        graph.addEdge("A", "C", 2); // Added edge A-C with cost 2
        graph.addEdge("B", "C", 1); // Added edge B-C with cost 1

        String start = "A", end = "C"; // Defined start and end nodes

        Map<String, Integer> distances = new HashMap<>(); // Created distances map
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Created priority
                                                                                                 // queue

        for (String node : graph.getGraph().keySet()) // Initialized distances
            distances.put(node, Integer.MAX_VALUE); // Set initial distances to infinity
        distances.put(start, 0); // Set distance to start node to 0
        pq.add(start); // Added start node to priority queue

        while (!pq.isEmpty()) { // While priority queue is not empty
            String current = pq.poll(); // Poll the current node
            for (var neighbor : graph.getGraph().get(current).entrySet()) { // For each neighbor
                int newDist = distances.get(current) + neighbor.getValue(); // Calculate new distance
                if (newDist < distances.get(neighbor.getKey())) { // If new distance is shorter
                    distances.put(neighbor.getKey(), newDist); // Update distance
                    pq.add(neighbor.getKey()); // Add neighbor to priority queue
                }
            }
        }

        System.out.println("Shortest Path from " + start + " to " + end + ": " + distances.get(end)); // Print shortest
                                                                                                      // path
    }
}