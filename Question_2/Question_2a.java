package Question_2;

import java.util.Arrays;
public class Question_2a {
    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        int[] rewards = new int[n];
        Arrays.fill(rewards, 1); // Initialize all rewards to 1

        // Left-to-Right Pass
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Right-to-Left Pass
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Compute total rewards
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }

        return totalRewards;
    }

    public static void main(String[] args) {
        // Test Cases
        System.out.println(minRewards(new int[] { 1, 0, 2 }));
        System.out.println(minRewards(new int[] { 1, 2, 2 }));
    }
}
//Terminal output
// PS D:\DESKTOP\DSA_Assignment> & 'C:\Program Files\Eclipse
// Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe'
// '-XX:+ShowCodeDetailsInExceptionMessages' '-cp'
// 'C:\Users\asus\AppData\Roaming\Code\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin'
// 'Question_2.Question_2a'
//5 4