import java.util.ArrayList;
import java.util.Random;
public class Table {
    private ArrayList<Card> cards;


    public Table() {
        cards = new ArrayList<>();
    }

    /**
     * This method creates a deck by adding 52 Cards to cards ArrayList
     */
    public void add52() {
        for (Suit s : Suit.values()) {
            for(int i = 2; i <= 14; i++) {
                cards.add(new Card(s, i));
            }
        }
    }

    /**
     * This method is called to deal a card from the deck
     * @return Card at random
     */
    public Card deal() {
        Random rand = new Random();
        int r = rand.nextInt(52);
        Card card = cards.get(r);
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
