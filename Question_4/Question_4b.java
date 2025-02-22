package Question_4;

import java.util.*;

public class Question_4b {
    public static int findMinimumRoads(int[] packages, int[][] roads) {
        int n = packages.length;

        // Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            adj.get(road[0]).add(road[1]);
            adj.get(road[1]).add(road[0]);
        }

        // Count total packages
        int totalPackages = 0;
        for (int p : packages) {
            if (p == 1)
                totalPackages++;
        }
        if (totalPackages == 0)
            return 0;

        // Try each position as starting point
        int minRoadsNeeded = Integer.MAX_VALUE;

        // For each starting position
        for (int start = 0; start < n; start++) {
            Queue<int[]> queue = new LinkedList<>();
            Set<Integer> collected = new HashSet<>();
            Set<String> visited = new HashSet<>();

            // Start BFS from this position
            queue.offer(new int[] { start, 0, 0 }); // node, roadCount, last position
            if (packages[start] == 1)
                collected.add(start);

            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int node = current[0];
                int roadCount = current[1];
                int last = current[2];

                // If all packages collected and back at start
                if (collected.size() == totalPackages && node == start) {
                    minRoadsNeeded = Math.min(minRoadsNeeded, roadCount);
                    continue;
                }

                // Get all nodes within distance 2
                Set<Integer> reachableNodes = new HashSet<>();
                // Direct neighbors (distance 1)
                for (int next : adj.get(node)) {
                    reachableNodes.add(next);
                    // Neighbors of neighbors (distance 2)
                    for (int next2 : adj.get(next)) {
                        if (next2 != node) {
                            reachableNodes.add(next2);
                        }
                    }
                }

                // Try moving to each reachable node
                for (int next : reachableNodes) {
                    // Calculate distance to next node
                    int distance = adj.get(node).contains(next) ? 1 : 2;
                    String path = Math.min(node, next) + "," + Math.max(node, next);

                    // Create new collected set
                    Set<Integer> newCollected = new HashSet<>(collected);
                    if (packages[next] == 1) {
                        newCollected.add(next);
                    }

                    String state = next + "," + newCollected.hashCode();
                    if (!visited.contains(state)) {
                        visited.add(state);
                        queue.offer(new int[] { next, roadCount + distance, node });
                    }
                }
            }
        }

        return minRoadsNeeded == Integer.MAX_VALUE ? -1 : minRoadsNeeded;
    }

    public static void main(String[] args) {
        // Test case 1
        int[] packages1 = { 1, 0, 0, 0, 0, 1 };
        int[][] roads1 = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } };
        System.out.println("Test case 1: " + findMinimumRoads(packages1, roads1));

        // Test case 2
        int[] packages2 = { 0, 0, 0, 1, 1, 0, 0, 1 };
        int[][] roads2 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 4 }, { 3, 5 }, { 4, 6 }, { 5, 7 } };
        System.out.println("Test case 2: " + findMinimumRoads(packages2, roads2));
    }
}