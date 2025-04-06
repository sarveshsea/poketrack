package battleship;

/**
 * The Cruiser class represents a medium-sized ship in the game.
 * A cruiser occupies 3 squares on the game board.
 * It is one of the larger ships but not as large as a battleship.
 * 
 * @author Sarvesh Chidambaram
 */
// A cruiser takes up 3 squares
public class Cruiser extends Ship {
    
    /**
     * Creates a new cruiser.
     * Initializes the ship with a length of 3 squares.
     */
    // Create a cruiser (length 3)
    public Cruiser() {
        super(3);
    }
    
    /**
     * Returns the type of this ship.
     * Used to identify the ship type in the game.
     * 
     * @return "cruiser" as a String
     */
    // Return ship type name
    @Override
    public String getShipType() {
        return "cruiser";
    }
} 