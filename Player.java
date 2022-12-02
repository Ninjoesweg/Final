import java.util.ArrayList;

/**
 * This class creates a Player. PLayer has 2 Card objects in their hand.
 * Player starts with 50 chips.
 */
public class Player {
    private ArrayList<Card> hand;
    private int chips;
    private String name;
    private boolean computer;
    private int chipsInPot;
    private boolean fold;

    /**
     * Player constructor creates a Player object.
     *
     * @param name     String, the name of the player
     * @param chips    int, the number of chips a player has.
     * @param computer boolean, whether the Player is the user or a computer.
     */
    public Player(String name, int chips, boolean computer) {
        this.name = name;
        this.chips = chips;
        this.hand = new ArrayList<>();
        this.chipsInPot = 0;
        this.fold = false;
        this.computer = computer;
    }

    /**
     * This method add a Card to a players hand.
     *
     * @param x A Card object
     */
    public void addToHand(Card x) {
        this.hand.add(x);
    }

    /**
     * This method returns a player's hand
     *
     * @return ArrayList<Card> hand
     */
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * This method returns the number of chips a player has.
     *
     * @return int number of chips
     */
    public int getChips() {
        return this.chips;
    }

    /**
     * This method is used to get a player's name
     *
     * @return String player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method is used to get the number of chips in the pot.
     *
     * @return int the number of chips
     */
    public int getChipsInPot() {
        return this.chipsInPot;
    }

    /**
     * This method gets the 1st card in a player's hand.
     *
     * @return A Card object within the hand Arraylist.
     */
    public Card getCard1() {
        return this.hand.get(0);
    }

    /**
     * This method gets the 2nd card in a player's hand.
     *
     * @return A Card object within the hand Arraylist.
     */
    public Card getCard2() {
        return this.hand.get(1);
    }

    /**
     * This method is used to find a Player's fold status.
     *
     * @return boolean, if true player has folded, if false player has not folded
     */
    public boolean isFold() {
        return fold;
    }

    /**
     * This method is used to update the amount of chips a Player has.
     *
     * @param chips int the Player's new amount of chips
     */
    public void setChips(int chips) {
        this.chips = chips;
    }

    /**
     * This method is used to update a Player's fold status.
     *
     * @param fold boolean, if true Player's fold variable is set to true, otherwise it is set to false
     */
    public void setFold(boolean fold) {
        this.fold = fold;
    }

    /**
     * This method updates a Player's hand
     *
     * @param hand, An ArrayList of Card objects that will be Player's new hand.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * This method sets the amount of chips in the pot for a Player.
     *
     * @param chipsInPot int, the number of chips player wishes to put into pot.
     */
    public void setChipsInPot(int chipsInPot) {
        this.chipsInPot = chipsInPot;
    }

    /**
     * This method is used to tell whether a player is the user or if the player is the computer.
     *
     * @return boolean true if computer/ false if user
     */
    public boolean isComputer() {
        if (getName().equalsIgnoreCase("Computer")) {
            computer = true;
        }
        return computer;
    }
}

