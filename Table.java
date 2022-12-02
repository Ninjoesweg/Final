import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to create a deck of cards, shuffle the cards, and deal the cards.
 */
public class Table {
    private static ArrayList<Card> cards;

    /**
     * Table constructor, creates a Table and an ArrayList to be filled with Card objects later.
     */
    public Table() {
        cards = new ArrayList<>();
    }

    /**
     * This method creates a deck by adding 52 Cards to cards ArrayList
     */
    public void add52() {
        for (Suit s : Suit.values()) {
            for (int i = 2; i <= 14; i++) {
                cards.add(new Card(s, i));
            }
        }
    }

    /**
     * This method is called to deal a card from the top of the deck
     *
     * @return Card at top of deck
     */
    public Card deal() {
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

    /**
     * This method shuffles the Card objects in the cards ArrayList
     */
    public void shuffle() {
        Random rng = new Random();
        ArrayList<Card> shuffled = new ArrayList<>();
        while (cards.size() > 0) {
            int index = rng.nextInt(cards.size());
            Card c = cards.get(index);
            cards.remove(index);
            shuffled.add(c);
        }
        cards = shuffled;
    }
}
