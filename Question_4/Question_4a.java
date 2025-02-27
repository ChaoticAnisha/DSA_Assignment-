// // Step 1: Create necessary classes
// class Tweet {
//     int userId;
//     int tweetId;
//     String tweetDate;
//     String tweet;
//     // Constructor and getters/setters
// }

// class HashtagCount implements Comparable<HashtagCount> {
//     String hashtag;
//     int count;
//     // Constructor and compareTo method
// }

// // Step 2: Database connection
// public List<Tweet> getTweetsFromDB() {
//     List<Tweet> tweets = new ArrayList<>();
//     try (Connection conn = DriverManager.getConnection(url, user, password)) {
//         String sql = "SELECT * FROM tweets";
//         // Execute query and populate tweets list
//     }
//     return tweets;
// }

// // Step 3: Main processing method
// public List<HashtagCount> getTop3TrendingHashtags(List<Tweet> tweets) {
//     Map<String, Integer> hashtagCountMap = new HashMap<>();

//     // Step 4: Filter February 2024 tweets and process
//     for (Tweet tweet : tweets) {
//         if (tweet.tweetDate.startsWith("2024-02")) {
//             // Step 5: Extract hashtags
//             Set<String> tweetHashtags = extractHashtags(tweet.tweet);

//             // Step 6: Update counts
//             for (String hashtag : tweetHashtags) {
//                 hashtagCountMap.merge(hashtag, 1, Integer::sum);
//             }
//         }
//     }

//     // Step 7: Convert to list and sort
//     List<HashtagCount> result = hashtagCountMap.entrySet().stream()
//             .map(e -> new HashtagCount(e.getKey(), e.getValue()))
//             .sorted()
//             .collect(Collectors.toList());

//     // Step 8: Return top 3
//     return result.subList(0, Math.min(3, result.size()));
// }