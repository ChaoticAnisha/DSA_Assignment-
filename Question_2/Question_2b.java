package Question_2;
public class Question_2b {
    public static void main(String[] args) {
        // Input arrays representing x and y coordinates of the points
        int[] x_coords = { 1, 2, 3, 2, 4 };
        int[] y_coords = { 2, 3, 1, 2, 3 };

        // Calling the function to get the lexicographically smallest pair of points
        int[] result = findClosestPair(x_coords, y_coords);

        // Print the result (indices of the closest pair of points)
        System.out.println("The indices of the closest pair of points are: [" + result[0] + ", " + result[1] + "]");
    }

    // Finding the lexicographically closest pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        // Initializing variables to store the minimum distance and corresponding
        // indices
        int minDistance = Integer.MAX_VALUE; // Starting with maximum value as we want to find the minimum
        int[] closestPair = new int[2]; // Array to store the result pair of indices

        // Iterate through all pairs of points, ensuring j > i to avoid duplicate checks
        for (int i = 0; i < x_coords.length; i++) {
            for (int j = i + 1; j < x_coords.length; j++) {
                // Calculate the Manhattan distance between the points (i, j)
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If the calculated distance is smaller than the current minimum distance
                if (distance < minDistance) {
                    // Update the minimum distance and the closest pair of indices
                    minDistance = distance;
                    closestPair[0] = i; // Store the first index of the closest pair
                    closestPair[1] = j; // Store the second index of the closest pair
                } else if (distance == minDistance) {
                    // If the distance is the same, choose the lexicographically smaller pair
                    if (i < closestPair[0] || (i == closestPair[0] && j < closestPair[1])) {
                        closestPair[0] = i;
                        closestPair[1] = j;
                    }
                }
            }
        }

        // Return the closest pair of indices
        return closestPair;
    }
}
//Terminal output
// PS D:\DESKTOP\DSA_Assignment> & 'C:\Program Files\Eclipse
// Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe'
//The indices of the closest pair of points are:[0,3]