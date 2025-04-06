package battleship;

import java.util.Scanner;

/**
 * The BattleshipGame class is the main class that runs the game.
 * It handles the game flow, user interaction, and manages the game state.
 * This class creates the ocean, places the ships, and processes player moves.
 * 
 * @author Vi Vo
 */
public class BattleshipGame {
    // The game board
    private Ocean ocean;
    // For reading user input
    private Scanner scanner;

    /**
     * Creates a new Battleship game.
     * Initializes the ocean and sets up the input scanner for player interaction.
     */
    public BattleshipGame() {
        // Initialize the game board
        ocean = new Ocean();
        // Set up input scanner
        scanner = new Scanner(System.in);
    }

    /**
     * Gets a valid number input from the player.
     * Gets a valid number from the user between min and max
     */
    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            // Show the prompt
            System.out.print(prompt);
            
            try {
                // Try to read a number
                String input = scanner.nextLine().trim();
                int number = Integer.parseInt(input);
                
                // Check if number is valid
                if (number >= min && number <= max) {
                    return number;
                }
                
                // Number was out of range
                System.out.println("Please enter a number between " + min + " and " + max);
                
            } catch (NumberFormatException e) {
                // Input wasn't a number
                System.out.println("Please enter a valid number");
            }
        }
    }

    /**
     * Runs one complete game
     */
    public void play() {
        // Show welcome message
        System.out.println("Welcome to Battleship!");
        System.out.println("Try to sink all ships!");
        System.out.println("Enter numbers 0-9 for rows and columns");
        System.out.println("'-' = miss, 'X' = hit, 'S' = sunk\n");
        
        // Set up the game board
        ocean.placeAllShipsRandomly();
        
        // Show ship positions (for debugging)
        ocean.printWithShips();
        
        // Show empty board to start
        ocean.print();

        // Keep playing until all ships are sunk
        while (!ocean.isGameOver()) {
            // Get the player's shot coordinates
            int row = getIntInput("Enter row (0-9): ", 0, 9);
            int column = getIntInput("Enter column (0-9): ", 0, 9);

            // Take the shot and show result
            boolean hit = ocean.shootAt(row, column);
            System.out.println(hit ? "Hit!" : "Miss!");
            System.out.println();

            // Show updated board
            ocean.print();
            
            // Show current game statistics
            System.out.println("\nCurrent Stats:");
            System.out.println("Shots: " + ocean.getShotsFired());
            System.out.println("Hits: " + ocean.getHitCount());
            System.out.println("Ships Sunk: " + ocean.getShipsSunk());
            System.out.println();
        }

        // Show final results
        System.out.println("Game Over! All ships have been sunk!");
        System.out.println("You took " + ocean.getShotsFired() + " shots");
        
        // Calculate and show accuracy
        int accuracy = (ocean.getHitCount() * 100) / ocean.getShotsFired();
        System.out.println("Hit ratio: " + accuracy + "%");
    }

    /**
     * Starts the game
     */
    public static void main(String[] args) {
        // Create and start a new game
        BattleshipGame game = new BattleshipGame();
        game.play();
    }
} 