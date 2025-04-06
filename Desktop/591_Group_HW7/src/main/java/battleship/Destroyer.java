package battleship;

/**
 * The Destroyer class represents a small ship in the game.
 * A destroyer occupies 2 squares on the game board.
 * It is one of the smaller ships but larger than a submarine.
 * 
 * @author Sarvesh Chidambaram
 */
// A destroyer takes up 2 squares
public class Destroyer extends Ship {
    
    /**
     * Creates a new destroyer.
     * Initializes the ship with a length of 2 squares.
     */
    // Create a destroyer (length 2)
    public Destroyer() {
        super(2);
    }
    
    /**
     * Returns the type of this ship.
     * Used to identify the ship type in the game.
     * 
     * @return "destroyer" as a String
     */
    // Return ship type name
    @Override
    public String getShipType() {
        return "destroyer";
    }
} 