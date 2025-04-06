package battleship;

/**
 * The Submarine class represents the smallest ship in the game.
 * A submarine occupies 1 square on the game board.
 * It is the smallest and most maneuverable ship in the game.
 * 
 * @author Sarvesh Chidambaram
 */
public class Submarine extends Ship {
    
    /**
     * Creates a new submarine.
     * Initializes the ship with a length of 1 square.
     */
    public Submarine() {
        super(1);
    }
    
    /**
     * Returns the type of this ship.
     * Used to identify the ship type in the game.
     * 
     * @return "submarine" as a String
     */
    @Override
    public String getShipType() {
        return "submarine";
    }
} 