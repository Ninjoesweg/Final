/**
 * This class is used to create Card Objects and also to find the file pathways to Cards corresponding Image.
 */
public class Card {
    private Suit suit;
    private int rank;

    /**
     * Card constructor creates a Card Object
     *
     * @param s Suit enum
     * @param i int rank
     */
    public Card(Suit s, int i) {
        suit = s;
        rank = i;
    }

    /**
     * This method is used to get the rank of a Card.
     *
     * @return int 1-13 (Ace-King) of Card
     */
    public int getRank() {
        return rank;
    }

    /**
     * This method returns the Suit of a Card.
     *
     * @return suit enum
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * This method is used to find a file pathway to a Card's corresponding Image.
     * This method is called in the Animation Class, cardPicture() method.
     *
     * @return A String of the file pathway.
     */
    public String toImageFileName() {
        String fileName = "";
        if (getSuit() == Suit.Clubs) {
            if (getRank() == 14) {
                fileName = "Images/C1.png";
            } else if (getRank() == 2) {
                fileName = "Images/C2.png";
            } else if (getRank() == 3) {
                fileName = "Images/C3.png";
            } else if (getRank() == 4) {
                fileName = "Images/C4.png";
            } else if (getRank() == 5) {
                fileName = "Images/C5.png";
            } else if (getRank() == 6) {
                fileName = "Images/C6.png";
            } else if (getRank() == 7) {
                fileName = "Images/C7.png";
            } else if (getRank() == 8) {
                fileName = "Images/C8.png";
            } else if (getRank() == 9) {
                fileName = "Images/C9.png";
            } else if (getRank() == 10) {
                fileName = "Images/C10.png";
            } else if (getRank() == 11) {
                fileName = "Images/CJack.png";
            } else if (getRank() == 12) {
                fileName = "Images/CQueen.png";
            } else {
                fileName = "Images/CKing.png";
            }
        } else if (getSuit() == Suit.Diamonds) {
            if (getRank() == 14) {
                fileName = "Images/D1.png";
            } else if (getRank() == 2) {
                fileName = "Images/D2.png";
            } else if (getRank() == 3) {
                fileName = "Images/D3.png";
            } else if (getRank() == 4) {
                fileName = "Images/D4.png";
            } else if (getRank() == 5) {
                fileName = "Images/D5.png";
            } else if (getRank() == 6) {
                fileName = "Images/D6.png";
            } else if (getRank() == 7) {
                fileName = "Images/D7.png";
            } else if (getRank() == 8) {
                fileName = "Images/D8.png";
            } else if (getRank() == 9) {
                fileName = "Images/D9.png";
            } else if (getRank() == 10) {
                fileName = "Images/D10.png";
            } else if (getRank() == 11) {
                fileName = "Images/DJack.png";
            } else if (getRank() == 12) {
                fileName = "Images/DQueen.png";
            } else {
                fileName = "Images/DKing.png";
            }
        } else if (getSuit() == Suit.Hearts) {
            if (getRank() == 14) {
                fileName = "Images/H1.png";
            } else if (getRank() == 2) {
                fileName = "Images/H2.png";
            } else if (getRank() == 3) {
                fileName = "Images/H3.png";
            } else if (getRank() == 4) {
                fileName = "Images/H4.png";
            } else if (getRank() == 5) {
                fileName = "Images/H5.png";
            } else if (getRank() == 6) {
                fileName = "Images/H6.png";
            } else if (getRank() == 7) {
                fileName = "Images/H7.png";
            } else if (getRank() == 8) {
                fileName = "Images/H8.png";
            } else if (getRank() == 9) {
                fileName = "Images/H9.png";
            } else if (getRank() == 10) {
                fileName = "Images/H10.png";
            } else if (getRank() == 11) {
                fileName = "Images/HJack.png";
            } else if (getRank() == 12) {
                fileName = "Images/HQueen.png";
            } else {
                fileName = "Images/HKing.png";
            }
        } else {
            if (getRank() == 14) {
                fileName = "Images/S1.png";
            } else if (getRank() == 2) {
                fileName = "Images/S2.png";
            } else if (getRank() == 3) {
                fileName = "Images/S3.png";
            } else if (getRank() == 4) {
                fileName = "Images/S4.png";
            } else if (getRank() == 5) {
                fileName = "Images/S5.png";
            } else if (getRank() == 6) {
                fileName = "Images/S6.png";
            } else if (getRank() == 7) {
                fileName = "Images/S7.png";
            } else if (getRank() == 8) {
                fileName = "Images/S8.png";
            } else if (getRank() == 9) {
                fileName = "Images/S9.png";
            } else if (getRank() == 10) {
                fileName = "Images/S10.png";
            } else if (getRank() == 11) {
                fileName = "Images/SJack.png";
            } else if (getRank() == 12) {
                fileName = "Images/SQueen.png";
            } else {
                fileName = "Images/SKing.png";
            }
        }
        return fileName;
    }
}

