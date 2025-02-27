package Question_5.src;

import java.util.*;

public class NetworkGraph {
    private Map<String, Map<String, Integer>> graph = new HashMap<>();

    public void addNode(String node) {
        graph.putIfAbsent(node, new HashMap<>());
    }

    public void addEdge(String node1, String node2, int cost) {
        graph.get(node1).put(node2, cost);
        graph.get(node2).put(node1, cost);
    }

    public Map<String, Map<String, Integer>> getGraph() {
        return graph;
    }

    public void displayGraph() {
        System.out.println("Network Graph:");
        for (var entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
