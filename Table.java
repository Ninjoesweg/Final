import java.util.ArrayList;
import java.util.Random;
public class Table {
    private ArrayList<Card> cards;
    private ArrayList<Card> faceUp;

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
     * This method is called to deal the top card in the deck
     * @return Card at top of deck
     */
    public Card deal() {
        Card top = cards.get(0);
        cards.remove(0);
        return top;
    }

    /**
     * This method deals the first 3 faceup cards (the flop)
     * @return an ArrayList of Card objects
     */
    public ArrayList<Card> flop(){
        for(int i=0; i<3; i++){
        faceUp.add(deal());
        }
        return(faceUp);
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
