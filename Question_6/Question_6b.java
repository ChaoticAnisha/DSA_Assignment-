package Question_6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Class responsible for printing numbers
class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}

// Class that controls the threads and their synchronization
class ThreadController {
    private int n; // Number of times to print '0' and the range for even/odd numbers
    private int current = 0; // Controls which number to print next
    private final NumberPrinter printer; // Printer instance to print numbers

    public ThreadController(int n, NumberPrinter printer) {
        this.n = n;
        this.printer = printer;
    }

    // Method for ZeroThread to print '0'
    public synchronized void printZero() throws InterruptedException {
        for (int i = 0; i < n; i++) { // Loop to print '0' n times
            while (current % 3 != 0) { // Wait until it's ZeroThread's turn
                wait();
            }
            printer.printZero(); // Print 0
            current++; // Move to the next state
            notifyAll(); // Notify other waiting threads
        }
    }

    // Method for EvenThread to print even numbers
    public synchronized void printEven() throws InterruptedException {
        for (int i = 2; i <= n; i += 2) { // Print even numbers
            while (current % 3 != 2) { // Wait until it's EvenThread's turn
                wait();
            }
            printer.printEven(i); // Print even number
            current++; // Move to next state
            notifyAll(); // Notify other threads
        }
    }

    // Method for OddThread to print odd numbers
    public synchronized void printOdd() throws InterruptedException {
        for (int i = 1; i <= n; i += 2) { // Print odd numbers
            while (current % 3 != 1) { // Wait until it's OddThread's turn
                wait();
            }
            printer.printOdd(i); // Print odd number
            current++; // Move to next state
            notifyAll(); // Notify other threads
        }
    }
}

// Main class for the multithreaded web crawler
public class Question_6b {
    // Queue to store URLs to be crawled
    private final ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<>();
    // ExecutorService to manage the thread pool
    private final ExecutorService executorService;

    // Constructor to initialize the thread pool with a specified number of threads
    public Question_6b(int numberOfThreads) {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    // Method to add a URL to the queue
    public void addUrl(String url) {
        urlQueue.add(url);
    }

    // Method to start crawling the URLs in the queue
    public void crawl() {
        // Continue until there are no more URLs in the queue
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll(); // Retrieve and remove the head of the queue
            if (url != null) {
                // Submit a task to fetch the page asynchronously
                Future<?> future = executorService.submit(() -> fetchPage(url));
                // Handle future if needed (e.g., check for exceptions)
            }
        }
        executorService.shutdown(); // Shutdown the executor service after all tasks are submitted
    }

    // Method to fetch the content of a web page
    private void fetchPage(String urlString) {
        try {
            URL url = new URL(urlString); // Create a URL object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Open a connection
            connection.setRequestMethod("GET"); // Set the request method to GET
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Read the
                                                                                                        // response
            String inputLine;
            StringBuilder content = new StringBuilder(); // StringBuilder to hold the content
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine); // Append each line of the response
            }
            in.close(); // Close the BufferedReader
            // Process the content (e.g., extract data)
            System.out.println("Fetched content from: " + urlString); // Output the fetched URL
        } catch (Exception e) {
            // Handle any exceptions that occur during the fetching process
            System.err.println("Error fetching URL: " + urlString + " - " + e.getMessage());
        }
    }

    // Main method to run the crawler
    public static void main(String[] args) {
        Question_6b crawler = new Question_6b(5); // Create a crawler with 5 threads
        crawler.addUrl("http://example.com"); // Add example URLs to the queue
        crawler.addUrl("http://example.org");
        crawler.crawl(); // Start the crawling process
    }
}

//Terminal Output 
// PS D:\DESKTOP\DSA_Assignment> & 'C:\Program Files\Eclipse
// Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe'
// '-XX:+ShowCodeDetailsInExceptionMessages' '-cp'
// 'C:\Users\asus\AppData\Roaming\Cursor\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin'
// 'Question_6.Question_6b'
//Fetched content from:http:// example.com
//Fetched content from:http:// example.org
