package battleship;

/**
 * The EmptySea class represents an empty part of the ocean.
 * It is used to fill squares where no ship is present.
 * This class is special because it can never be sunk and always returns false when shot at.
 * 
 * @author Sarvesh Chidambaram
 */
// Represents empty water (no ship)
public class EmptySea extends Ship {
    
    /**
     * Creates a new empty sea square.
     * Initializes it with a length of 1 square.
     */
    // Create empty sea (length 1)
    public EmptySea() {
        super(1);
    }
    
    /**
     * Returns the type of this "ship".
     * Used to identify empty squares in the game.
     * 
     * @return "empty" as a String
     */
    // Return ship type name
    @Override
    public String getShipType() {
        return "empty";
    }
    
    /**
     * Handles a shot at an empty sea square.
     * Always returns false since you can't hit empty water.
     * Marks the square as hit for display purposes.
     * 
     * @param row The row number that was shot (not used)
     * @param column The column number that was shot (not used)
     * @return false, since empty sea can't be hit
     */
    // Shot at empty sea always misses
    @Override
    public boolean shootAt(int row, int column) {
        // Mark as "hit" even though it's a miss
        getHit()[0] = true;
        return false;
    }
    
    /**
     * Checks if the empty sea is sunk.
     * Empty sea can never be sunk since it's not a real ship.
     * 
     * @return false, since empty sea can't be sunk
     */
    // Empty sea can't be sunk
    @Override
    public boolean isSunk() {
        return false;
    }
    
    /**
     * Returns how to display this empty sea square.
     * Shows '-' when shot at, '.' when not shot at.
     * 
     * @return "-" if shot at, "." if not shot at
     */
    // Show as "-" when shot at
    @Override
    public String toString() {
        return getHit()[0] ? "-" : ".";
    }
} 