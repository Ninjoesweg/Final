public class Card {
    private Suit suit;
    private int rank;

    public Card(Suit s, int i) {
        suit = s;
        rank = i;
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
}
