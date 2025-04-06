package battleship;

/**
 * The Ship class is an abstract base class for all types of ships in the game.
 * It defines the common behavior and properties that all ships share.
 * Each ship has a length, position, orientation, and tracks which parts have been hit.
 * 
 * @author Vi Vo
 */
public abstract class Ship {
    // Row where the bow (front) is located
    private int bowRow;
    
    // Column where the bow (front) is located
    private int bowColumn;
    
    // Number of squares the ship takes up
    private int length;
    
    // True if ship is horizontal (facing east)
    // False if ship is vertical (facing south)
    private boolean horizontal;
    
    // Array to track hits on each part of ship
    private boolean[] hit;

    /**
     * Creates a new ship with the specified length.
     * Initializes the hit tracking array for the ship.
     * 
     * @param length The number of squares the ship occupies
     */
    public Ship(int length) {
        // Set the ship's length
        this.length = length;
        // Create array to track hits
        this.hit = new boolean[length];
        // Initialize all parts as not hit
        for (int i = 0; i < length; i++) {
            hit[i] = false;
        }
    }

    /**
     * Gets the length of the ship.
     * 
     * @return The number of squares the ship occupies
     */
    public int getLength() { return length; }

    /**
     * Gets the row number where the bow (front) of the ship is located.
     * 
     * @return The row number of the ship's bow
     */
    public int getBowRow() { return bowRow; }

    /**
     * Gets the column number where the bow (front) of the ship is located.
     * 
     * @return The column number of the ship's bow
     */
    public int getBowColumn() { return bowColumn; }

    /**
     * Gets the array tracking which parts of the ship have been hit.
     * 
     * @return An array where true means that part has been hit
     */
    public boolean[] getHit() { return hit; }

    /**
     * Checks if the ship is placed horizontally.
     * 
     * @return true if the ship is horizontal, false if vertical
     */
    public boolean isHorizontal() { return horizontal; }

    /**
     * Sets the row number for the ship's bow.
     * 
     * @param row The new row number for the bow
     */
    public void setBowRow(int row) { this.bowRow = row; }

    /**
     * Sets the column number for the ship's bow.
     * 
     * @param column The new column number for the bow
     */
    public void setBowColumn(int column) { this.bowColumn = column; }

    /**
     * Sets the orientation of the ship.
     * 
     * @param horizontal true for horizontal placement, false for vertical
     */
    public void setHorizontal(boolean horizontal) { this.horizontal = horizontal; }

    /**
     * Returns the type of ship (to be implemented by subclasses).
     * 
     * @return A string representing the type of ship
     */
    public abstract String getShipType();

    /**
     * Checks if it's okay to place the ship at the given location.
     * Verifies the ship won't go off the board or overlap with other ships.
     * 
     * @param row The starting row number
     * @param column The starting column number
     * @param horizontal The desired orientation
     * @param ocean The ocean to place the ship in
     * @return true if the placement is valid, false otherwise
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        // Check if starting position is off the board
        if (row < 0 || row >= 10 || column < 0 || column >= 10) return false;

        // Check if ship would go off the board
        if (horizontal) {
            // Check left edge (ship extends left from bow)
            if (column < 0 || column + length - 1 >= 10) return false;
        } else {
            // Check top edge (ship extends up from bow)
            if (row < 0 || row + length - 1 >= 10) return false;
        }

        // Check one square around the ship for other ships
        int rowStart = Math.max(0, row - 1);
        int rowEnd = Math.min(9, horizontal ? row + 1 : row + length);
        int colStart = Math.max(0, column - 1);
        int colEnd = Math.min(9, horizontal ? column + length : column + 1);

        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = colStart; c <= colEnd; c++) {
                if (ocean.isOccupied(r, c)) return false;
            }
        }

        return true;
    }

    /**
     * Places the ship at the specified location in the ocean.
     * Updates the ship's position and orientation, and marks its location in the ocean.
     * 
     * @param row The starting row number
     * @param column The starting column number
     * @param horizontal The desired orientation
     * @param ocean The ocean to place the ship in
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        // For horizontal ships, bow is at the right end
        // For vertical ships, bow is at the bottom
        if (horizontal) {
            this.bowRow = row;
            this.bowColumn = column + length - 1;  // Bow at right end
        } else {
            this.bowRow = row + length - 1;  // Bow at bottom
            this.bowColumn = column;
        }
        this.horizontal = horizontal;

        // Place ship in the ocean array
        Ship[][] ships = ocean.getShipArray();
        if (horizontal) {
            // Place from left to right (bow at right)
            for (int i = 0; i < length; i++) {
                ships[row][column + i] = this;
            }
        } else {
            // Place from top to bottom (bow at bottom)
            for (int i = 0; i < length; i++) {
                ships[row + i][column] = this;
            }
        }
    }

    /**
     * Handles a shot at this ship.
     * Marks the appropriate part of the ship as hit.
     * 
     * @param row The row number that was shot
     * @param column The column number that was shot
     * @return true if the shot hit the ship, false otherwise
     */
    public boolean shootAt(int row, int column) {
        // Can't hit a sunk ship
        if (isSunk()) return false;

        // Find which part of the ship was hit
        int index;
        if (horizontal) {
            // For horizontal ships, count from left to right (bow at right)
            index = bowColumn - column;
        } else {
            // For vertical ships, count from top to bottom (bow at bottom)
            index = bowRow - row;
        }

        // If valid part of ship, mark as hit
        if (index >= 0 && index < length) {
            hit[index] = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the ship has been sunk (all parts hit).
     * 
     * @return true if all parts of the ship have been hit, false otherwise
     */
    public boolean isSunk() {
        // Ship is sunk if all parts are hit
        for (boolean h : hit) {
            if (!h) return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the ship's state.
     * 'S' for unhit, 'x' for hit but not sunk, 's' for sunk.
     * 
     * @return A string representing the ship's current state
     */
    @Override
    public String toString() {
        // 'S' for unhit, 'x' for hit but not sunk, 's' for sunk
        if (isSunk()) return "s";
        
        // Check if any part is hit
        boolean hasHit = false;
        for (boolean h : hit) {
            if (h) {
                hasHit = true;
                break;
            }
        }
        return hasHit ? "x" : "S";
    }
} 