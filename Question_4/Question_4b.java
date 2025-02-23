package Question_4;

import java.util.*;

public class Question_4b {
    static class State {
        int pos; // Current position
        int collected; // Bitmask of collected packages
        int roads; // Number of roads taken

        State(int pos, int collected, int roads) {
            this.pos = pos;
            this.collected = collected;
            this.roads = roads;
        }
    }

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

        // Get all packages to collect
        int target = 0;
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                target |= (1 << i); // Set bit for each package location
            }
        }
        if (target == 0)
            return 0; // No packages to collect

        int minRoads = Integer.MAX_VALUE;

        // Try each starting position
        for (int start = 0; start < n; start++) {
            Queue<State> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();

            // Collect package at start if exists
            int initialCollected = 0;

            // Collect all packages within distance 2 of start
            initialCollected |= collectNearbyPackages(start, packages, adj);

            queue.offer(new State(start, initialCollected, 0));

            while (!queue.isEmpty()) {
                State curr = queue.poll();
                String key = curr.pos + "," + curr.collected;

                if (visited.contains(key))
                    continue;
                visited.add(key);

                // If all packages collected and back at start
                if (curr.collected == target && curr.pos == start) {
                    minRoads = Math.min(minRoads, curr.roads);
                    continue;
                }

                // Try moving to each adjacent node
                for (int next : adj.get(curr.pos)) {
                    // Calculate new collected packages after moving
                    int newCollected = curr.collected | collectNearbyPackages(next, packages, adj);
                    queue.offer(new State(next, newCollected, curr.roads + 1));
                }
            }
        }

        return minRoads == Integer.MAX_VALUE ? -1 : minRoads;
    }

    // Helper method to collect packages within distance 2 of current position
    private static int collectNearbyPackages(int pos, int[] packages, List<List<Integer>> adj) {
        int collected = 0;
        if (packages[pos] == 1) {
            collected |= (1 << pos); // Collect package at current position
        }

        // Collect from direct neighbors (distance 1)
        for (int next : adj.get(pos)) {
            if (packages[next] == 1) {
                collected |= (1 << next); // Collect package at neighbor
            }

            // Collect from neighbors of neighbors (distance 2)
            for (int next2 : adj.get(next)) {
                if (packages[next2] == 1) {
                    collected |= (1 << next2); // Collect package at neighbor's neighbor
                }
            }
        }
        return collected;
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
//Terminal output:
//PS D:\DESKTOP\DSA_Assignment>  & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\asus\AppData\Roaming\Code\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin' 'Question_4.Question_4b' 
//Test case 1: 2
//Test case 2: 4