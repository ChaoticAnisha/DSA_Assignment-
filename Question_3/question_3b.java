package Question_3; // Package declaration

import java.util.*; // Importing utility classes
import javax.swing.*; // Importing Swing components
import java.awt.*; // Importing AWT components
import java.awt.event.*; // Importing AWT event classes

public class question_3b extends JPanel implements ActionListener { // Main class extending JPanel and implementing
                                                                    // ActionListener
    private final int BOARD_WIDTH = 10; // Width of the game board
    private final int BOARD_HEIGHT = 20; // Height of the game board
    private final int BLOCK_SIZE = 30; // Size of each block
    private final int PREVIEW_SIZE = 4; // Size of the preview panel

    // Core game components
    private javax.swing.Timer timer; // Timer for game updates
    private Block currentBlock; // Current block in play
    private Queue<Block> blockQueue; // Queue for upcoming blocks
    private Stack<int[]> boardStack; // Stack to manage board rows
    private int[][] board; // 2D array representing the game board
    private boolean gameOver; // Flag to check if the game is over
    private int score; // Player's score
    private int level; // Current level

    // Preview panel for next block
    private JPanel previewPanel; // Panel to preview the next block

    // Color mapping for different block types
    private static final Color[] BLOCK_COLORS = {
            Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.RED,
            Color.GREEN, Color.ORANGE, Color.BLUE
    };

    public question_3b() { // Constructor
        setLayout(new BorderLayout()); // Setting layout to BorderLayout

        // Main game panel
        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { // Overriding paintComponent to draw the game
                super.paintComponent(g); // Calling superclass method
                drawGame(g); // Custom method to draw the game
            }
        };
        gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE)); // Setting size
                                                                                                        // of game panel
        gamePanel.setBackground(Color.BLACK); // Setting background color of game panel

        // Preview panel
        previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { // Overriding paintComponent to draw the preview
                super.paintComponent(g); // Calling superclass method
                drawPreview(g); // Custom method to draw the preview
            }
        };
        previewPanel.setPreferredSize(new Dimension(PREVIEW_SIZE * BLOCK_SIZE, PREVIEW_SIZE * BLOCK_SIZE)); // Setting
                                                                                                            // size of
                                                                                                            // preview
                                                                                                            // panel
        previewPanel.setBackground(Color.DARK_GRAY); // Setting background color of preview panel

        // Side panel for preview and controls
        JPanel sidePanel = new JPanel(); // Creating side panel
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // Setting layout to BoxLayout
        sidePanel.add(new JLabel("Next Block:")); // Adding label to side panel
        sidePanel.add(previewPanel); // Adding preview panel to side panel

        // Add control buttons
        JPanel buttonPanel = new JPanel(); // Creating button panel
        JButton leftButton = new JButton("←"); // Creating left button
        JButton rightButton = new JButton("→"); // Creating right button
        JButton rotateButton = new JButton("Rotate"); // Creating rotate button
        JButton dropButton = new JButton("Drop"); // Creating drop button

        leftButton.addActionListener(e -> moveBlock(-1)); // Adding action listener to left button
        rightButton.addActionListener(e -> moveBlock(1)); // Adding action listener to right button
        rotateButton.addActionListener(e -> rotateBlock()); // Adding action listener to rotate button
        dropButton.addActionListener(e -> dropBlock()); // Adding action listener to drop button

        buttonPanel.add(leftButton); // Adding left button to button panel
        buttonPanel.add(rightButton); // Adding right button to button panel
        buttonPanel.add(rotateButton); // Adding rotate button to button panel
        buttonPanel.add(dropButton); // Adding drop button to button panel
        sidePanel.add(buttonPanel); // Adding button panel to side panel

        add(gamePanel, BorderLayout.CENTER); // Adding game panel to main panel
        add(sidePanel, BorderLayout.EAST); // Adding side panel to main panel

        setFocusable(true); // Making the panel focusable
        addKeyListener(new KeyAdapter() { // Adding key listener for keyboard controls
            @Override
            public void keyPressed(KeyEvent e) { // Overriding keyPressed method
                if (gameOver) { // If game is over
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) { // If space key is pressed
                        initializeGame(); // Reinitialize the game
                        repaint(); // Repaint the panel
                        return; // Return from method
                    }
                }

                switch (e.getKeyCode()) { // Switch case for different key events
                    case KeyEvent.VK_LEFT:
                        moveBlock(-1); // Move block left
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveBlock(1); // Move block right
                        break;
                    case KeyEvent.VK_UP:
                        rotateBlock(); // Rotate block
                        break;
                    case KeyEvent.VK_DOWN:
                        dropBlock(); // Drop block
                        break;
                }
            }
        });

        blockQueue = new LinkedList<>(); // Initializing block queue
        boardStack = new Stack<>(); // Initializing board stack
        initializeGame(); // Initializing the game
    }

    private void initializeGame() { // Method to initialize the game
        if (timer != null) { // If timer is not null
            timer.stop(); // Stop the timer
        }

        board = new int[BOARD_HEIGHT][BOARD_WIDTH]; // Initializing the board
        score = 0; // Initializing score
        level = 1; // Initializing level
        gameOver = false; // Initializing game over flag
        blockQueue.clear(); // Clearing block queue
        boardStack.clear(); // Clearing board stack

        // Initialize board stack
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            boardStack.push(new int[BOARD_WIDTH]); // Pushing empty rows to board stack
        }

        // Fill queue with initial blocks
        for (int i = 0; i < 3; i++) {
            blockQueue.offer(new Block()); // Adding new blocks to the queue
        }

        currentBlock = blockQueue.poll(); // Getting the first block from the queue
        blockQueue.offer(new Block()); // Adding a new block to the queue

        // Start timer with initial delay based on level
        timer = new javax.swing.Timer(1000 / level, this); // Initializing timer
        timer.start(); // Starting the timer
    }

    private void moveBlock(int dx) { // Method to move the block
        if (!gameOver && currentBlock.move(dx, 0, board)) { // If game is not over and block can move
            repaint(); // Repaint the panel
        }
    }

    private void rotateBlock() { // Method to rotate the block
        if (!gameOver) { // If game is not over
            currentBlock.rotate(board); // Rotate the block
            repaint(); // Repaint the panel
        }
    }

    private void dropBlock() { // Method to drop the block
        if (!gameOver) { // If game is not over
            while (currentBlock.move(0, 1, board)) {
            } // Move block down until it can't move
            placeBlock(); // Place the block on the board
            checkRows(); // Check for full rows
            getNextBlock(); // Get the next block
            repaint(); // Repaint the panel
        }
    }

    private void getNextBlock() { // Method to get the next block
        currentBlock = blockQueue.poll(); // Get the next block from the queue
        blockQueue.offer(new Block()); // Add a new block to the queue

        if (!currentBlock.isValidPosition(board)) { // If the new block can't be placed
            gameOver = true; // Set game over flag
            timer.stop(); // Stop the timer
            showGameOver(); // Show game over message
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Method called by the timer
        if (!gameOver) { // If game is not over
            if (!currentBlock.move(0, 1, board)) { // If block can't move down
                placeBlock(); // Place the block on the board
                checkRows(); // Check for full rows
                getNextBlock(); // Get the next block
            }
            repaint(); // Repaint the panel
        }
    }

    private void placeBlock() { // Method to place the block on the board
        int[][] shape = currentBlock.getShape(); // Get the shape of the block
        int x = currentBlock.getX(); // Get the x position of the block
        int y = currentBlock.getY(); // Get the y position of the block

        for (int i = 0; i < shape.length; i++) { // Loop through the shape
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) { // If the cell is not empty
                    board[y + i][x + j] = currentBlock.getColorIndex() + 1; // Place the block on the board
                    int[] row = boardStack.get(y + i); // Get the row from the stack
                    row[x + j] = currentBlock.getColorIndex() + 1; // Place the block on the row
                }
            }
        }
    }

    private void checkRows() { // Method to check for full rows
        int rowsCleared = 0; // Initialize rows cleared

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) { // Loop through the rows from bottom to top
            boolean fullRow = true; // Initialize full row flag
            for (int j = 0; j < BOARD_WIDTH; j++) { // Loop through the columns
                if (board[i][j] == 0) { // If the cell is empty
                    fullRow = false; // Set full row flag to false
                    break; // Break the loop
                }
            }

            if (fullRow) { // If the row is full
                rowsCleared++; // Increment rows cleared
                boardStack.remove(i); // Remove the row from the stack
                boardStack.push(new int[BOARD_WIDTH]); // Add an empty row to the stack

                // Update board array to match stack
                for (int k = 0; k < BOARD_HEIGHT; k++) {
                    board[k] = boardStack.get(k).clone(); // Update the board array
                }
            }
        }

        // Update score and level
        if (rowsCleared > 0) { // If any rows were cleared
            score += rowsCleared * rowsCleared * 100; // Update the score
            level = 1 + score / 1000; // Update the level
            timer.setDelay(Math.max(100, 1000 / level)); // Update the timer delay
        }
    }

    private void drawGame(Graphics g) { // Method to draw the game
        // Draw board
        for (int i = 0; i < BOARD_HEIGHT; i++) { // Loop through the rows
            for (int j = 0; j < BOARD_WIDTH; j++) { // Loop through the columns
                if (board[i][j] != 0) { // If the cell is not empty
                    g.setColor(BLOCK_COLORS[board[i][j] - 1]); // Set the color
                    g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Fill the cell
                    g.setColor(Color.BLACK); // Set the color to black
                    g.drawRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Draw the cell border
                } else {
                    g.setColor(Color.DARK_GRAY); // Set the color to dark gray
                    g.drawRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Draw the cell border
                }
            }
        }

        // Draw current block
        if (currentBlock != null && !gameOver) { // If there is a current block and game is not over
            int[][] shape = currentBlock.getShape(); // Get the shape of the block
            int x = currentBlock.getX(); // Get the x position of the block
            int y = currentBlock.getY(); // Get the y position of the block
            g.setColor(BLOCK_COLORS[currentBlock.getColorIndex()]); // Set the color

            for (int i = 0; i < shape.length; i++) { // Loop through the shape
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) { // If the cell is not empty
                        g.fillRect((x + j) * BLOCK_SIZE, (y + i) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Fill the cell
                        g.setColor(Color.BLACK); // Set the color to black
                        g.drawRect((x + j) * BLOCK_SIZE, (y + i) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Draw the cell
                                                                                                        // border
                        g.setColor(BLOCK_COLORS[currentBlock.getColorIndex()]); // Set the color back
                    }
                }
            }
        }

        // Draw score and level
        g.setColor(Color.WHITE); // Set the color to white
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font
        g.drawString("Score: " + score, 10, 25); // Draw the score
        g.drawString("Level: " + level, 10, 50); // Draw the level
    }

    private void drawPreview(Graphics g) { // Method to draw the preview
        Block nextBlock = blockQueue.peek(); // Get the next block from the queue
        if (nextBlock != null) { // If there is a next block
            int[][] shape = nextBlock.getShape(); // Get the shape of the block
            g.setColor(BLOCK_COLORS[nextBlock.getColorIndex()]); // Set the color

            int startX = (PREVIEW_SIZE - shape[0].length) * BLOCK_SIZE / 2; // Calculate the starting x position
            int startY = (PREVIEW_SIZE - shape.length) * BLOCK_SIZE / 2; // Calculate the starting y position

            for (int i = 0; i < shape.length; i++) { // Loop through the shape
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) { // If the cell is not empty
                        g.fillRect(startX + j * BLOCK_SIZE, startY + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Fill
                                                                                                              // the
                                                                                                              // cell
                        g.setColor(Color.BLACK); // Set the color to black
                        g.drawRect(startX + j * BLOCK_SIZE, startY + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE); // Draw
                                                                                                              // the
                                                                                                              // cell
                                                                                                              // border
                        g.setColor(BLOCK_COLORS[nextBlock.getColorIndex()]); // Set the color back
                    }
                }
            }
        }
    }

    private void showGameOver() { // Method to show game over message
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Game Over! Score: " + score + "\nLevel: " + level + "\nPlay again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) { // If player chooses to play again
            initializeGame(); // Reinitialize the game
        }
    }

    public static void main(String[] args) { // Main method
        SwingUtilities.invokeLater(() -> { // Run the game on the event dispatch thread
            JFrame frame = new JFrame("Tetris"); // Create a new frame
            question_3b game = new question_3b(); // Create a new game instance
            frame.add(game); // Add the game to the frame
            frame.pack(); // Pack the frame
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
            frame.setVisible(true); // Make the frame visible
        });
    }
}

class Block { // Block class
    private int[][] shape; // Shape of the block
    private int x, y; // Position of the block
    private int colorIndex; // Color index of the block

    private static final int[][][] SHAPES = { // Array of shapes
            { { 1, 1, 1, 1 } }, // I shape
            { { 1, 1 }, { 1, 1 } }, // Square shape
            { { 0, 1, 0 }, { 1, 1, 1 } }, // T shape
            { { 1, 1, 0 }, { 0, 1, 1 } }, // Z shape
            { { 0, 1, 1 }, { 1, 1, 0 } }, // S shape
            { { 1, 0, 0 }, { 1, 1, 1 } }, // L shape
            { { 0, 0, 1 }, { 1, 1, 1 } } // J shape
    };

    public Block() { // Constructor
        colorIndex = new Random().nextInt(SHAPES.length); // Randomly select a shape
        shape = SHAPES[colorIndex]; // Set the shape
        x = 4; // Set the initial x position
        y = 0; // Set the initial y position
    }

    public boolean move(int dx, int dy, int[][] board) { // Method to move the block
        if (canMove(dx, dy, board)) { // If the block can move
            x += dx; // Update x position
            y += dy; // Update y position
            return true; // Return true
        }
        return false;
    }

    private boolean canMove(int dx, int dy, int[][] board) { // Method to check if the block can move
        for (int i = 0; i < shape.length; i++) { // Loop through the shape
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) { // If the cell is not empty
                    int newX = x + j + dx; // Calculate new x position
                    int newY = y + i + dy; // Calculate new y position

                    if (newX < 0 || newX >= board[0].length || // If new position is out of bounds
                            newY >= board.length ||
                            (newY >= 0 && board[newY][newX] != 0)) { // If new position is occupied
                        return false; // Return false
                    }
                }
            }
        }
        return true; // Return true
    }

    public void rotate(int[][] board) { // Method to rotate the block
        int[][] rotated = new int[shape[0].length][shape.length]; // Create new array for rotated shape

        for (int i = 0; i < shape.length; i++) { // Loop through the shape
            for (int j = 0; j < shape[i].length; j++) {
                rotated[j][shape.length - 1 - i] = shape[i][j]; // Rotate the shape
            }
        }

        int[][] oldShape = shape; // Save old shape
        shape = rotated; // Set new shape

        if (!isValidPosition(board)) { // If new position is not valid
            shape = oldShape; // Revert to old shape
        }
    }

    public boolean isValidPosition(int[][] board) { // Method to check if the position is valid
        return canMove(0, 0, board); // Check if the block can move to the current position
    }

    public int[][] getShape() { // Method to get the shape
        return shape; // Return the shape
    }

    public int getX() { // Method to get the x position
        return x; // Return the x position
    }

    public int getY() { // Method to get the y position
        return y; // Return the y position
    }

    public int getColorIndex() { // Method to get the color index
        return colorIndex; // Return the color index
    }
}
