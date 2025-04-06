package battleship;

/**
 * The Battleship class represents the largest ship in the game.
 * A battleship occupies 4 squares on the game board.
 * It is the most valuable and hardest to sink ship in the game.
 * 
 * @author Sarvesh Chidambaram
 */
// A battleship takes up 4 squares
public class Battleship extends Ship {
    
    /**
     * Creates a new battleship.
     * Initializes the ship with a length of 4 squares.
     */
    // Create a battleship (length 4)
    public Battleship() {
        super(4);
    }
    
    /**
     * Returns the type of this ship.
     * Used to identify the ship type in the game.
     * 
     * @return "battleship" as a String
     */
    // Return ship type name
    @Override
    public String getShipType() {
        return "battleship";
    }
} 