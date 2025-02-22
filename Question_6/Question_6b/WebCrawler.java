package Question_6.Question_6b;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WebCrawler {
    private static final int MAX_THREADS = 10; // Maximum number of threads in the pool
    private static final int MAX_URLS = 100; // Maximum number of URLs to crawl
    private final ExecutorService executorService; // Thread pool executor
    private final Set<String> visitedUrls; // Set to keep track of visited URLs
    private final LinkedBlockingQueue<String> urlQueue; // Queue to manage URLs to be crawled

    public WebCrawler() {
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS); // Initialize thread pool
        this.visitedUrls = new HashSet<>(); // Initialize visited URLs set
        this.urlQueue = new LinkedBlockingQueue<>(); // Initialize URL queue
    }

    // Method to start crawling from a given URL
    public void startCrawling(String startUrl) {
        urlQueue.add(startUrl); // Add the starting URL to the queue
        while (!urlQueue.isEmpty() && visitedUrls.size() < MAX_URLS) {
            String url = urlQueue.poll(); // Get the next URL from the queue
            if (url != null && !visitedUrls.contains(url)) {
                visitedUrls.add(url); // Mark the URL as visited
                executorService.submit(new CrawlerTask(url)); // Submit a new crawling task to the thread pool
            }
        }
        shutdownAndAwaitTermination(); // Shutdown the executor service after crawling
    }

    // Method to shutdown the executor service gracefully
    private void shutdownAndAwaitTermination() {
        executorService.shutdown(); // Initiate an orderly shutdown
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // Force shutdown if tasks are not finished
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor service did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow(); // Force shutdown on interruption
            Thread.currentThread().interrupt();
        }
    }

    // Inner class representing a crawling task
    private class CrawlerTask implements Runnable {
        private final String url;

        public CrawlerTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                String content = fetchContent(url); // Fetch the content of the URL
                System.out.println("Crawled URL: " + url);
                // Process the content (e.g., extract URLs, save data)
                // For simplicity, we just print the content length
                System.out.println("Content length: " + content.length());
                // Extract URLs and add them to the queue
                Set<String> extractedUrls = extractUrls(content);
                for (String extractedUrl : extractedUrls) {
                    if (!visitedUrls.contains(extractedUrl)) {
                        urlQueue.add(extractedUrl); // Add new URLs to the queue
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to crawl URL: " + url);
                e.printStackTrace();
            }
        }

        // Method to fetch the content of a URL
        private String fetchContent(String urlString) throws Exception {
            StringBuilder content = new StringBuilder();
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
            }
            return content.toString();
        }

        // Method to extract URLs from the content using a simple regex
        private Set<String> extractUrls(String content) {
            Set<String> urls = new HashSet<>();
            String regex = "http[s]?://\\S+";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                urls.add(matcher.group());
            }
            return urls;
        }
    }

    // Main method to start the web crawler
    public static void main(String[] args) {
        String startUrl = "http://example.com"; // Replace with the starting URL
        WebCrawler crawler = new WebCrawler();
        crawler.startCrawling(startUrl); // Start crawling from the given URL
    }
}
// Terminal Output:
// PS D:\DESKTOP\DSA_Assignment\Question_6\question6b> javac -d . WebCrawler.java Question_6b.java
// PS D:\DESKTOP\DSA_Assignment\Question_6\question6b> javaQuestion_6.question6b.WebCrawler^C
//PS D:\DESKTOP\DSA_Assignment\Question_6\question6b> java Question_6.question6b.WebCrawler
//Crawled URL:http:// example.com
//Content length:1210