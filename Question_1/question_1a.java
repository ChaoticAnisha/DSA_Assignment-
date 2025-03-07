public class question_1a {
    public static int findCriticalTemperature(int k, int n) {
        // Created DP table where dp[i][j] represents the maximum number of temperature
        // levels
        // that can be checked with i samples and j attempts.
        int[][] dp = new int[k + 1][n + 1];

        // Initialize attempts
        int attempts = 0;

        // Iterate until we can find the critical temperature for all levels
        while (dp[k][attempts] < n) {
            attempts++;
            for (int samples = 1; samples <= k; samples++) {
                // Recurrence relation:
                // 1 + (dp[samples - 1][attempts - 1] (breaks) + dp[samples][attempts - 1] (does
                // not break))
                dp[samples][attempts] = 1 + dp[samples - 1][attempts - 1] + dp[samples][attempts - 1];
            }
        }

        return attempts;
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println(findCriticalTemperature(1, 2));
        System.out.println(findCriticalTemperature(2, 6));
        System.out.println(findCriticalTemperature(3, 14));
    }
}

//Terminal output
// PS D:\DESKTOP\DSA_Assignment> & 'C:\Program Files\Eclipse
// Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe'
// '-XX:+ShowCodeDetailsInExceptionMessages' '-cp'
// 'C:\Users\asus\AppData\Roaming\Code\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin'
// 'question_1a'
//2 3 4
