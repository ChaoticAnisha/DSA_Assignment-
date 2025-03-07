package Question_6;

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
            while (current % 2 != 0) { // Wait until it's ZeroThread's turn
                wait();
            }
            printer.printZero(); // Print 0
            current++; // Move to the next state
            notifyAll(); // Notify other waiting threads
        }
    }

    // Method for EvenThread to print even numbers
    public synchronized void printEven() throws InterruptedException {
        for (int i = 2; i <= n; i += 2) { // Loop to print even numbers
            while (current % 4 != 3) { // Wait until it's EvenThread's turn
                wait();
            }
            printer.printEven(i); // Print even number
            current++; // Move to the next state
            notifyAll(); // Notify other waiting threads
        }
    }

    // Method for OddThread to print odd numbers
    public synchronized void printOdd() throws InterruptedException {
        for (int i = 1; i <= n; i += 2) { // Loop to print odd numbers
            while (current % 4 != 1) { // Wait until it's OddThread's turn
                wait();
            }
            printer.printOdd(i); // Print odd number
            current++; // Move to the next state
            notifyAll(); // Notify other waiting threads
        }
    }
}

// Main class to run the program
public class Question_6a {
    public static void main(String[] args) {
        int n = 5; // Example input
        NumberPrinter printer = new NumberPrinter(); // Create a NumberPrinter instance
        ThreadController controller = new ThreadController(n, printer); // Create a ThreadController instance

        // Create and start ZeroThread
        Thread zeroThread = new Thread(() -> {
            try {
                controller.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create and start EvenThread
        Thread evenThread = new Thread(() -> {
            try {
                controller.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create and start OddThread
        Thread oddThread = new Thread(() -> {
            try {
                controller.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start all threads
        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}
//Terminal output
// PS D:\DESKTOP\DSA_Assignment>  & 'C:\Program Files\Eclipse Adoptium\jdk-17.0.11.9-hotspot\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\asus\AppData\Roaming\Code\User\workspaceStorage\ceb9750205844422ad612c526b471fbf\redhat.java\jdt_ws\DSA_Assignment_ecefc3e6\bin' 'Question_6.Question_6a' 
//0102030405