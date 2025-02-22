package Question_3;
import java.util.*;

public class question_3a {

    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        // Create an adjacency list for the graph
        @SuppressWarnings("unchecked")
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Add edges for the direct connections between devices
        for (int[] connection : connections) {
            int device1 = connection[0] - 1; // 0-indexed
            int device2 = connection[1] - 1; // 0-indexed
            int cost = connection[2];
            graph[device1].add(new int[] { device2, cost });
            graph[device2].add(new int[] { device1, cost });
        }

        // Add virtual edges for each device connecting it to the "virtual hub" with
        // module cost
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        boolean[] visited = new boolean[n];
        int totalCost = 0;

        // Start from the first device (index 0) and treat it as a "hub"
        minHeap.offer(new int[] { 0, modules[0] });
        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int device = current[0];
            int cost = current[1];

            // If the device is already visited, skip it
            if (visited[device]) {
                continue;
            }

            // Mark the device as visited and add the cost
            visited[device] = true;
            totalCost += cost;

            // Explore all possible connections (either module or direct connections)
            for (int[] neighbor : graph[device]) {
                int nextDevice = neighbor[0];
                int nextCost = neighbor[1];
                if (!visited[nextDevice]) {
                    minHeap.offer(new int[] { nextDevice, nextCost });
                }
            }

            // Offer the module cost for all devices to the min-heap
            if (!visited[device]) {
                minHeap.offer(new int[] { device, modules[device] });
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        // Example input
        int n = 3;
        int[] modules = { 1, 2, 2 };
        int[][] connections = { { 1, 2, 1 }, { 2, 3, 1 } };

        // Output the minimum cost to connect all devices
        System.out.println(minCostToConnectDevices(n, modules, connections));
    }
}
//Terminal output
//PS D:\DESKTOP\DSA_Assignment>  & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\asus\AppData\Roaming\Code\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin' 'Question_3.question_3a' 
//3