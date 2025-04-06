package battleship;

import java.util.Random;

/**
 * The Ocean class represents the game board in Battleship.
 * It manages the placement of ships, tracks game statistics, and handles player interactions.
 * The ocean is a 10x10 grid where ships can be placed and shots can be fired.
 * 
 * @author Vi Vo
 */
public class Ocean {
    // Size of the game board
    private static final int BOARD_SIZE = 10;
    
    // Number of each type of ship
    private static final int NUM_BATTLESHIPS = 1;  // Length 4
    private static final int NUM_CRUISERS = 2;     // Length 3
    private static final int NUM_DESTROYERS = 3;   // Length 2
    private static final int NUM_SUBMARINES = 4;   // Length 1

    // The game board - a 10x10 array of ships
    private Ship[][] ships = new Ship[BOARD_SIZE][BOARD_SIZE];
    
    // Game statistics
    private int shotsFired = 0;    // Total shots taken
    private int hitCount = 0;      // Number of hits
    private int shipsSunk = 0;     // Number of ships sunk

    /**
     * Creates a new empty ocean.
     * Initializes a 10x10 grid filled with empty sea squares.
     * Sets up the game statistics (shots fired, hits, ships sunk) to zero.
     */
    public Ocean() {
        // Fill the ocean with empty sea
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ships[i][j] = new EmptySea();
            }
        }
    }

    /**
     * Randomly places all ships on the ocean.
     * Places one battleship, two cruisers, three destroyers, and four submarines.
     * Ensures ships don't overlap and are within the ocean boundaries.
     */
    public void placeAllShipsRandomly() {
        Random random = new Random();

        // Place one battleship
        placeShipsOfType(new Battleship(), NUM_BATTLESHIPS, random);
        
        // Place two cruisers
        placeShipsOfType(new Cruiser(), NUM_CRUISERS, random);
        
        // Place three destroyers
        placeShipsOfType(new Destroyer(), NUM_DESTROYERS, random);
        
        // Place four submarines
        placeShipsOfType(new Submarine(), NUM_SUBMARINES, random);
    }

    /**
     * Helper method to place multiple ships of the same type
     */
    private void placeShipsOfType(Ship prototype, int count, Random random) {
        for (int i = 0; i < count; i++) {
            Ship ship = null;
            
            // Create the right type of ship
            switch (prototype.getShipType()) {
                case "battleship": ship = new Battleship(); break;
                case "cruiser": ship = new Cruiser(); break;
                case "destroyer": ship = new Destroyer(); break;
                case "submarine": ship = new Submarine(); break;
            }
            
            boolean placed = false;
            while (!placed) {
                // Pick random position and orientation
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);
                boolean horizontal = random.nextBoolean();
                
                // Try to place the ship
                if (ship.okToPlaceShipAt(row, col, horizontal, this)) {
                    ship.placeShipAt(row, col, horizontal, this);
                    placed = true;
                }
            }
        }
    }

    /**
     * Checks if a specific square in the ocean contains a ship.
     * 
     * @param row The row number (0-9) to check
     * @param column The column number (0-9) to check
     * @return true if there's a ship at the specified location, false if it's empty sea
     */
    public boolean isOccupied(int row, int column) {
        return !ships[row][column].getShipType().equals("empty");
    }

    /**
     * Fires a shot at the specified location in the ocean.
     * Updates game statistics and checks if a ship was hit or sunk.
     * 
     * @param row The row number (0-9) to shoot at
     * @param column The column number (0-9) to shoot at
     * @return true if a ship was hit, false if the shot missed
     */
    public boolean shootAt(int row, int column) {
        // Count this shot
        shotsFired++;
        
        Ship ship = ships[row][column];
        
        // Try to hit the ship
        boolean hit = ship.shootAt(row, column);
        
        // Update game statistics
        if (hit) {
            hitCount++;
            if (ship.isSunk()) {
                shipsSunk++;
                System.out.println("You just sank a " + ship.getShipType() + "!");
            }
        }
        
        return hit;
    }

    /**
     * Displays the current state of the ocean to the player.
     * Shows hits ('X'), misses ('-'), sunk ships ('S'), and unshot squares ('.').
     * Includes row and column numbers for easy reference.
     */
    public void print() {
        // Print column numbers
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        
        // Print each row
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Print row number
            System.out.print(i + " ");
            
            // Print each cell
            for (int j = 0; j < BOARD_SIZE; j++) {
                Ship ship = ships[i][j];
                
                // Show appropriate symbol based on ship state
                if (ship.getHit()[getPartIndex(ship, i, j)]) {
                    if (ship.isSunk()) {
                        System.out.print("S ");  // Sunk
                    } else if (!ship.getShipType().equals("empty")) {
                        System.out.print("X ");  // Hit
                    } else {
                        System.out.print("- ");  // Miss
                    }
                } else {
                    System.out.print(". ");  // Not shot at
                }
            }
            System.out.println();
        }
    }

    /**
     * Displays the ocean with all ships visible (for debugging).
     * Shows the location of all ships using their first letter (B, C, D, S).
     * Also displays a summary of ships to find.
     */
    public void printWithShips() {
        System.out.println("\nShip Positions (for debugging):");
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                // Show first letter of ship type
                String type = ships[i][j].getShipType();
                switch (type) {
                    case "battleship": System.out.print("B "); break;
                    case "cruiser": System.out.print("C "); break;
                    case "destroyer": System.out.print("D "); break;
                    case "submarine": System.out.print("S "); break;
                    default: System.out.print(". ");
                }
            }
            System.out.println();
        }
        
        // Show ship counts
        System.out.println("\nShips to find:");
        System.out.println("1 Battleship (B) - length 4");
        System.out.println("2 Cruisers (C) - length 3");
        System.out.println("3 Destroyers (D) - length 2");
        System.out.println("4 Submarines (S) - length 1");
        System.out.println();
    }

    /**
     * Calculates which part of a ship is at a specific location.
     * Used internally to determine which part of a ship was hit.
     * 
     * @param ship The ship to check
     * @param row The row number to check
     * @param col The column number to check
     * @return The index of the ship part at the specified location
     */
    private int getPartIndex(Ship ship, int row, int col) {
        if (ship.getShipType().equals("empty")) {
            return 0;  // EmptySea is always length 1
        }
        
        // Calculate index based on ship orientation
        if (ship.isHorizontal()) {
            return ship.getBowColumn() - col;  // Count from right (bow) to left
        } else {
            return ship.getBowRow() - row;     // Count from bottom (bow) to top
        }
    }

    /**
     * Checks if the game is over (all ships have been sunk).
     * 
     * @return true if all ships are sunk, false otherwise
     */
    public boolean isGameOver() { return shipsSunk == NUM_BATTLESHIPS + NUM_CRUISERS + NUM_DESTROYERS + NUM_SUBMARINES; }

    /**
     * Gets the total number of shots fired in the game.
     * 
     * @return The number of shots fired
     */
    public int getShotsFired() { return shotsFired; }

    /**
     * Gets the total number of successful hits in the game.
     * 
     * @return The number of hits
     */
    public int getHitCount() { return hitCount; }

    /**
     * Gets the number of ships that have been sunk.
     * 
     * @return The number of sunk ships
     */
    public int getShipsSunk() { return shipsSunk; }

    /**
     * Gets the 2D array representing the ocean grid.
     * 
     * @return The array of ships in the ocean
     */
    public Ship[][] getShipArray() { return ships; }
} 