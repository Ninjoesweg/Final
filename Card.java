public class Card {
    private final Suit suit;
    private final int value;


    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() { return suit; }

    public int getValue() { return value; }

    public String toString() {
        String printVal;
        if (value == 11) {
            printVal = "Jack";
        } else if (value == 12) {
            printVal = "Queen";
        } else if (value == 13) {
            printVal = "King";
        } else if (value == 14) {
            printVal = "Ace";
        } else {
            printVal = "" + value;
        }
        String result = printVal + " of " + suit;
        return result;
    }
}
