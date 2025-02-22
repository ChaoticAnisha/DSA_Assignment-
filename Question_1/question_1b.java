import java.util.PriorityQueue;

public class question_1b {
    // Function to find the kth smallest combined return
    public static int findKthSmallestProduct(int[] returns1, int[] returns2, int k) {
        // Min heap to store and retrieve smallest products in order
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        // Initialize heap with the first elements from returns1 paired with the first
        // element of returns2
        for (int i = 0; i < returns1.length; i++) {
            minHeap.offer(new int[] { returns1[i] * returns2[0], i, 0 }); // Store product, index from returns1, index
                                                                          // from returns2
        }

        // Extract the kth smallest product
        int count = 0, result = 0;
        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll(); // Get the smallest product from heap
            result = curr[0]; // Store the current smallest product
            count++;

            if (count == k) // If we've found the kth smallest product, exit loop
                break;

            int i = curr[1], j = curr[2]; // Get indices of elements forming the product
            if (j + 1 < returns2.length) { // If there's another element in returns2, push next product
                minHeap.offer(new int[] { returns1[i] * returns2[j + 1], i, j + 1 });
            }
        }

        return result; // Return the kth smallest product
    }

    public static void main(String[] args) {
        // Test cases for findKthSmallestProduct
        System.out.println(findKthSmallestProduct(new int[] { 2, 5 }, new int[] { 3, 4 }, 2)); 
        System.out.println(findKthSmallestProduct(new int[] { -4, -2, 0, 3 }, new int[] { 2, 4 }, 6)); 
                                                                                                       
    }
}
//Terminal output
// PS D:\DESKTOP\DSA_Assignment> & 'C:\Program Files\Eclipse
// Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe'
// '-XX:+ShowCodeDetailsInExceptionMessages' '-cp'
// 'C:\Users\asus\AppData\Roaming\Code\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin'
// 'question_1b'
//8 0

