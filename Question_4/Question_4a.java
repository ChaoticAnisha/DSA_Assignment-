public class Question_4a {

    // Inner class to represent a Tweet
    static class Tweet {
        int userId; // ID of the user who posted the tweet
        int tweetId; // Unique ID of the tweet
        int year; // Year the tweet was posted
        int month; // Month the tweet was posted
        int day; // Day the tweet was posted
        String tweet; // Content of the tweet

        // Constructor to initialize a Tweet object
        public Tweet(int userId, int tweetId, int year, int month, int day, String tweet) {
            this.userId = userId;
            this.tweetId = tweetId;
            this.year = year;
            this.month = month;
            this.day = day;
            this.tweet = tweet;
        }
    }

    // Method to get trending hashtags from an array of tweets
    public static String[][] getTrendingHashtags(Tweet[] tweets) {
        // Array to hold tweets from February 2024
        Tweet[] februaryTweets = new Tweet[tweets.length];
        int februaryCount = 0; // Counter for February tweets

        // Filter tweets for February 2024
        for (Tweet tweet : tweets) {
            if (tweet.month == 2 && tweet.year == 2024) {
                februaryTweets[februaryCount++] = tweet;
            }
        }

        // Arrays to hold hashtags and their counts
        String[] hashtags = new String[100];
        int[] counts = new int[100];
        int hashtagCount = 0; // Counter for unique hashtags

        // Count occurrences of each hashtag
        for (int i = 0; i < februaryCount; i++) {
            String[] tweetHashtags = extractHashtags(februaryTweets[i].tweet);
            for (String hashtag : tweetHashtags) {
                if (hashtag != null) {
                    int index = findHashtag(hashtags, hashtag, hashtagCount);
                    if (index == -1) {
                        // New hashtag found
                        hashtags[hashtagCount] = hashtag;
                        counts[hashtagCount] = 1;
                        hashtagCount++;
                    } else {
                        // Increment count for existing hashtag
                        counts[index]++;
                    }
                }
            }
        }

        // Sort hashtags by count and alphabetically
        sortHashtags(hashtags, counts, hashtagCount);

        // Prepare the result array for the top 3 hashtags
        String[][] result = new String[3][2];
        for (int i = 0; i < 3 && i < hashtagCount; i++) {
            result[i][0] = hashtags[i]; // Hashtag
            result[i][1] = String.valueOf(counts[i]); // Count
        }
        return result; // Return the trending hashtags
    }

    // Method to extract hashtags from a tweet
    private static String[] extractHashtags(String text) {
        String[] words = text.split(" "); // Split the tweet into words
        String[] hashtags = new String[words.length]; // Array to hold hashtags
        int count = 0; // Counter for hashtags
        for (String word : words) {
            if (word.startsWith("#")) { // Check if the word is a hashtag
                hashtags[count++] = word; // Add to hashtags array
            }
        }
        return hashtags; // Return the array of hashtags
    }

    // Method to find the index of a hashtag in the array
    private static int findHashtag(String[] hashtags, String hashtag, int count) {
        for (int i = 0; i < count; i++) {
            if (hashtags[i].equals(hashtag)) {
                return i; // Return index if found
            }
        }
        return -1; // Return -1 if not found
    }

    // Method to sort hashtags based on their counts
    private static void sortHashtags(String[] hashtags, int[] counts, int count) {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                // Sort by count, then alphabetically
                if (counts[j] < counts[j + 1]
                        || (counts[j] == counts[j + 1] && hashtags[j].compareTo(hashtags[j + 1]) > 0)) {
                    // Swap counts
                    int tempCount = counts[j];
                    counts[j] = counts[j + 1];
                    counts[j + 1] = tempCount;

                    // Swap hashtags
                    String tempHashtag = hashtags[j];
                    hashtags[j] = hashtags[j + 1];
                    hashtags[j + 1] = tempHashtag;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Sample tweets for testing
        Tweet[] tweets = new Tweet[] {
                new Tweet(135, 13, 2024, 2, 1, "Enjoying a great start to the day. #HappyDay #morningvibes"),
                new Tweet(136, 14, 2024, 2, 2, "Another #HappyDay with good vibes! #FeelGood"),
                new Tweet(137, 15, 2024, 2, 3, "Productivity hacks! #workLife #ProductiveDay"),
                new Tweet(138, 16, 2024, 2, 4, "Exploring new tech frontiers. #TechLife #Innovation"),
                new Tweet(139, 17, 2024, 2, 5, "Gratitude for today's moments. #HappyDay #Thankful"),
                new Tweet(140, 18, 2024, 2, 7, "Innovation drives us. #TechLife #FutureTech"),
                new Tweet(141, 19, 2024, 2, 9, "Connecting with nature's serenity. #Nature #Peaceful")
        };

        // Get trending hashtags from the sample tweets
        String[][] trendingHashtags = getTrendingHashtags(tweets);
        System.out.println("Output:");
        System.out.println("+---------------+-------+");
        System.out.println("| hashtag       | count |");
        System.out.println("+---------------+-------+");
        for (int i = 0; i < trendingHashtags.length && trendingHashtags[i][0] != null; i++) {
            System.out.printf("| %-13s | %-5s |\n", trendingHashtags[i][0], trendingHashtags[i][1]);
        }
        System.out.println("+---------------+-------+");
    }
}

// Output:
// +---------------+-------+
// | hashtag | count |
// +---------------+-------+
// | #HappyDay | 3 |
// | #TechLife | 2 |
// | #FeelGood | 1 |
// +---------------+-------+