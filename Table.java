import java.util.ArrayList;
import java.util.Random;
public class Table {
    private ArrayList<Card> cards;
    private ArrayList<Card> faceUp;

    public Table() {
        cards = new ArrayList<>();
    }

    public void add52() {
        for (Suit s : Suit.values()) {
            for(int i = 2; i <= 14; i++) {
                cards.add(new Card(s, i));
            }
        }
    }

    public Card deal() {
        Card top = cards.get(0);
        cards.remove(0);
        return top;
    }
    public ArrayList<Card> flop(){
        for(int i=0; i<3; i++){
        faceUp.add(deal());
        }
        return(faceUp);
    }

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
