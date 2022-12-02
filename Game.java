import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class runs the poker game with its main(String[] args) method.
 * Starts the game and controls each round until a player loses.
 */
public class Game {
    private static boolean gameOver = false;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Card> faceUp = new ArrayList<>();
    private static int handSize = 2;
    private static Player winner = new Player("winner", 0, true);
    private static int rounds;
    private static Table newTable = new Table();
    public static String winnerName;
    public static String wayWon;

    /**
     * This method begins the game. Game continues until somebody wins.
     * @param players ArrayList containing all Player objects
     */
    public static synchronized void startGame(ArrayList<Player> players) throws IllegalMonitorStateException {
        //start new rounds until game is over
        setRound(1);
        startRound(rounds);
        //if game is over
        endGame();
    }

    /**
     * This method is called in the startGame() method to begin each round of the game.
     * Cards will be shuffled. Each player is dealt 2 cards and the first 3 face up cards are dealt.
     */
    public static synchronized void startRound(int round) throws IllegalMonitorStateException {
        if (round == 1) {
            //create new deck of cards and shuffle
            newTable = new Table();
            newTable.add52();
            newTable.shuffle();
            //add 2 cards to each player's hand
            for (Player person : players) {
                for (int i = 0; i < handSize; i++) {
                    person.addToHand(newTable.deal());
                }
            }
            //deal the first 3 face up cards (The Flop)
            faceUp = new ArrayList<Card>();
            int c = 0;
            while (c < 3) {
                faceUp.add(newTable.deal());
                c++;
            }
            //create a new Animation to use GUI and bet!
            for (Player p : getPlayers()) {
                if (!p.isComputer() && !p.isFold()) {
                    Animation animation = new Animation();
                    animation.main();
                }
            }
        }
        if (round == 2 || round == 3) {
            faceUp.add(newTable.deal());
            //create a new Animation to use GUI and bet!
            Animation animation = new Animation();
            animation.main();

        } else {
            roundWinner();
        }
    }

    /**
     * This method is used between rounds. It removes all cards from players hand. It also sets the pot is set to 0.
     * Finds if any player is out of chips, signaling the end of game.
     */
    public static void reset() {
        Bet.setBetPerPerson(0);
        Bet.setPot(0);
        int index = -1;
        for (Player p : getPlayers()) {
            p.setHand(new ArrayList<Card>());
            p.setChipsInPot(0);
            p.setFold(false);
            if (p.getChips() <= 0) {
                index = players.indexOf(p);
            }
        }
        if (index != -1) {
            players.remove(index);
            if (players.size() == 1) {
                gameOver = true;
            }
        }
        if (gameOver) {
            System.out.println(getPlayers().get(0).getName() + " won the game!");
            System.exit(0);
        }
        setRound(1);
        startRound(rounds);
    }

    /**
     * This method finds who won each round and distributes the chips in the pot to the winner.
     */
    public static void roundWinner() {
        boolean tie = false;
        Player temp = null;
        Player temp2 = null;
        for (Player person : players) {
            //if (person.getHand() > winner.gethand()
            if (person.isFold()) {

            } else if (temp == null || compareHands(temp, person) == -1) {
                temp = person;
                tie = false;
                temp2 = null;
            } else if (compareHands(temp, person) == 0) {
                tie = true;
                temp2 = person;
            }
        }
        int winnings;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        if (tie) {
            winnings = Bet.getPot() / 2;
            temp.setChips(temp.getChips() + winnings);
            temp2.setChips(temp2.getChips() + winnings);
            System.out.println("Tie");
        } else {
            winnings = Bet.getPot();
            temp.setChips(temp.getChips() + winnings);
            if (wayWon != null){
                System.out.println(temp.getName() + " won this round! with a " + wayWon);
            } else {
                System.out.println(temp.getName() + " won this round!");
            }
        }
        reset();
    }

    /**
     * Checks to see which player is winning based on the rules of texas holdem poker
     * @param player1 Player object
     * @param player2 Player object
     * @return 1 if player1 beats player2 -1 if player2 beats player1 and 0 if it is a tie
     */
    public static int compareHands(Player player1, Player player2) {
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards1.add(player1.getCard1());
        cards1.add(player1.getCard2());
        for (Card card : faceUp) {
            cards1.add(card);
        }
        cards2.add(player2.getCard1());
        cards2.add(player2.getCard2());
        for (Card card : faceUp) {
            cards2.add(card);
        }
        int heart1 = 0;
        int diamond1 = 0;
        int club1 = 0;
        int spade1 = 0;
        for (Card card : cards1) {
            if (card.getSuit() == Suit.Hearts) {
                heart1++;
            } else if (card.getSuit() == Suit.Diamonds) {
                diamond1++;
            } else if (card.getSuit() == Suit.Clubs) {
                club1++;
            } else if (card.getSuit() == Suit.Spades) {
                spade1++;
            }
        }
        int heart2 = 0;
        int diamond2 = 0;
        int club2 = 0;
        int spade2 = 0;
        for (Card card : cards1) {
            if (card.getSuit() == Suit.Hearts) {
                heart2++;
            } else if (card.getSuit() == Suit.Diamonds) {
                diamond2++;
            } else if (card.getSuit() == Suit.Clubs) {
                club2++;
            } else if (card.getSuit() == Suit.Spades) {
                spade2++;
            }
        }
        boolean possibleFlush1 = false;
        boolean possibleFlush2 = false;
        if (spade1 > 4 || club1 > 4 || heart1 > 4 || diamond1 > 4) {
            possibleFlush1 = true;
        }
        if (spade2 > 4 || club2 > 4 || heart2 > 4 || diamond2 > 4) {
            possibleFlush2 = true;
        }
        boolean royal1 = false;
        boolean royal2 = false;
        //royal flush check
        if (possibleFlush1) {
            boolean dAce = false;
            boolean dKing = false;
            boolean dQueen = false;
            boolean dJack = false;
            boolean dTen = false;
            boolean hAce = false;
            boolean hKing = false;
            boolean hQueen = false;
            boolean hJack = false;
            boolean hTen = false;
            boolean cAce = false;
            boolean cKing = false;
            boolean cQueen = false;
            boolean cJack = false;
            boolean cTen = false;
            boolean sAce = false;
            boolean sKing = false;
            boolean sQueen = false;
            boolean sJack = false;
            boolean sTen = false;
            for (Card card : cards1) {
                if (card.getSuit() == Suit.Diamonds) {
                    if (card.getRank() == 14) {
                        dAce = true;
                    }
                    if (card.getRank() == 13) {
                        dKing = true;
                    }
                    if (card.getRank() == 12) {
                        dQueen = true;
                    }
                    if (card.getRank() == 11) {
                        dJack = true;
                    }
                    if (card.getRank() == 10) {
                        dTen = true;
                    }
                }
                if (dAce && dKing && dQueen && dJack && dTen) {
                    royal1 = true;
                }
                if (card.getSuit() == Suit.Hearts) {
                    if (card.getRank() == 14) {
                        hAce = true;
                    }
                    if (card.getRank() == 13) {
                        hKing = true;
                    }
                    if (card.getRank() == 12) {
                        hQueen = true;
                    }
                    if (card.getRank() == 11) {
                        hJack = true;
                    }
                    if (card.getRank() == 10) {
                        hTen = true;
                    }
                }
                if (hAce && hKing && hQueen && hJack && hTen) {
                    royal1 = true;
                }
                if (card.getSuit() == Suit.Clubs) {
                    if (card.getRank() == 14) {
                        cAce = true;
                    }
                    if (card.getRank() == 13) {
                        cKing = true;
                    }
                    if (card.getRank() == 12) {
                        cQueen = true;
                    }
                    if (card.getRank() == 11) {
                        cJack = true;
                    }
                    if (card.getRank() == 10) {
                        cTen = true;
                    }
                }
                if (cAce && cKing && cQueen && cJack && cTen) {
                    royal1 = true;
                }
                if (card.getSuit() == Suit.Spades) {
                    if (card.getRank() == 14) {
                        sAce = true;
                    }
                    if (card.getRank() == 13) {
                        sKing = true;
                    }
                    if (card.getRank() == 12) {
                        sQueen = true;
                    }
                    if (card.getRank() == 11) {
                        sJack = true;
                    }
                    if (card.getRank() == 10) {
                        sTen = true;
                    }
                }
                if (sAce && sKing && sQueen && sJack && sTen) {
                    royal1 = true;
                }
            }
        }
        if (possibleFlush2) {
            boolean dAce = false;
            boolean dKing = false;
            boolean dQueen = false;
            boolean dJack = false;
            boolean dTen = false;
            boolean hAce = false;
            boolean hKing = false;
            boolean hQueen = false;
            boolean hJack = false;
            boolean hTen = false;
            boolean cAce = false;
            boolean cKing = false;
            boolean cQueen = false;
            boolean cJack = false;
            boolean cTen = false;
            boolean sAce = false;
            boolean sKing = false;
            boolean sQueen = false;
            boolean sJack = false;
            boolean sTen = false;
            for (Card card : cards2) {
                if (card.getSuit() == Suit.Diamonds) {
                    if (card.getRank() == 14) {
                        dAce = true;
                    }
                    if (card.getRank() == 13) {
                        dKing = true;
                    }
                    if (card.getRank() == 12) {
                        dQueen = true;
                    }
                    if (card.getRank() == 11) {
                        dJack = true;
                    }
                    if (card.getRank() == 10) {
                        dTen = true;
                    }
                }
                if (dAce && dKing && dQueen && dJack && dTen) {
                    royal2 = true;
                }
                if (card.getSuit() == Suit.Hearts) {
                    if (card.getRank() == 14) {
                        hAce = true;
                    }
                    if (card.getRank() == 13) {
                        hKing = true;
                    }
                    if (card.getRank() == 12) {
                        hQueen = true;
                    }
                    if (card.getRank() == 11) {
                        hJack = true;
                    }
                    if (card.getRank() == 10) {
                        hTen = true;
                    }
                }
                if (hAce && hKing && hQueen && hJack && hTen) {
                    royal2 = true;
                }
                if (card.getSuit() == Suit.Clubs) {
                    if (card.getRank() == 14) {
                        cAce = true;
                    }
                    if (card.getRank() == 13) {
                        cKing = true;
                    }
                    if (card.getRank() == 12) {
                        cQueen = true;
                    }
                    if (card.getRank() == 11) {
                        cJack = true;
                    }
                    if (card.getRank() == 10) {
                        cTen = true;
                    }
                }
                if (cAce && cKing && cQueen && cJack && cTen) {
                    royal2 = true;
                }
                if (card.getSuit() == Suit.Spades) {
                    if (card.getRank() == 14) {
                        sAce = true;
                    }
                    if (card.getRank() == 13) {
                        sKing = true;
                    }
                    if (card.getRank() == 12) {
                        sQueen = true;
                    }
                    if (card.getRank() == 11) {
                        sJack = true;
                    }
                    if (card.getRank() == 10) {
                        sTen = true;
                    }
                }
                if (sAce && sKing && sQueen && sJack && sTen) {
                    royal2 = true;
                }
            }
        }
        if (royal1 & !royal2) {
            wayWon = "Royal flush";
            return 1;
        }
        if (royal2 & !royal1) {
            wayWon = "Royal flush";
            return -1;
        }
        if (royal1 && royal2) {
            wayWon = "Royal flush tie";
            return 0;
        }
        //Straight Flush
        boolean straightFlush1 = false;
        boolean straightFlush2 = false;
        if (possibleFlush1) {
            for (Card card : cards1) {
                int c = 0;
                int count = 0;
                boolean plusOne = false;
                boolean plusTwo = false;
                boolean plusThree = false;
                boolean plusFour = false;
                boolean minusOne = false;
                boolean minusTwo = false;
                boolean minusThree = false;
                boolean minusFour = false;
                int dif = 0;
                while (c < cards1.size()) {
                    dif = card.getRank() - cards1.get(c).getRank();
                    if (cards1.get(c).getSuit().equals(card.getSuit()) && dif == 1) {
                        plusOne = true;
                    }
                    if (cards1.get(c).getSuit().equals(card.getSuit()) && dif == 2) {
                        plusTwo = true;
                    }
                    if (cards1.get(c).getSuit().equals(card.getSuit()) && dif == 3) {
                        plusThree = true;
                    }
                    if (cards1.get(c).getSuit().equals(card.getSuit()) && dif == 4) {
                        plusFour = true;
                    }
                    if (cards1.get(c).getSuit() == card.getSuit() && dif == -1) {
                        minusOne = true;
                    }
                    if (cards1.get(c).getSuit() == card.getSuit() && dif == -2) {
                        minusTwo = true;
                    }
                    if (cards1.get(c).getSuit() == card.getSuit() && dif == -3) {
                        minusThree = true;
                    }
                    if (cards1.get(c).getSuit() == card.getSuit() && dif == -4) {
                        minusFour = true;
                    }
                    c++;
                }
                if (plusOne) {
                    count++;
                    if (plusTwo) {
                        count++;
                        if (plusThree) {
                            count++;
                            if (plusFour) {
                                count++;
                            }
                        }
                    }
                }
                if (minusOne) {
                    count++;
                    if (minusTwo) {
                        count++;
                        if (minusThree) {
                            count++;
                            if (minusFour) {
                                count++;
                            }
                        }
                    }
                }
                if (count >= 4) {
                    straightFlush1 = true;
                }
            }
        }
        if (possibleFlush2) {
            for (Card card : cards2) {
                boolean stop = false;
                int count = 1;
                int rank = card.getRank() + 1;
                while (rank < 15 || !stop) {
                    if (cards2.contains(new Card(card.getSuit(), rank))) {
                        count++;
                        rank++;
                    } else {
                        stop = true;
                    }
                }
                stop = false;
                rank = card.getRank() - 1;
                while (rank > 1 || !stop) {
                    if (cards2.contains(new Card(card.getSuit(), rank))) {
                        count++;
                        rank--;
                    } else {
                        stop = true;
                    }
                }
                if (count >= 5) {
                    straightFlush2 = true;
                }
            }
        }
        if (straightFlush1 & !straightFlush2) {
            wayWon = "Straight flush" + player1.getName();
            return 1;
        }
        if (straightFlush2 & !straightFlush1) {
            wayWon = "Straight flush" + player2.getName();
            return -1;
        }
        if (straightFlush1 && straightFlush2) {
            wayWon = "Straight flushes";
            int high1 = 0;
            int high2 = 0;
            for (Card card : player1.getHand()) {
                if (card.getRank() > high1) {
                    high1 = card.getRank();
                }
            }
            for (Card card : player2.getHand()) {
                if (card.getRank() > high2) {
                    high2 = card.getRank();
                }
            }
            if (high1 > high2) {
                return 1;
            }
            if (high2 > high1) {
                return -1;
            }
            if (high1 == high2) {
                int total1 = player1.getCard1().getRank() + player1.getCard2().getRank();
                int total2 = player2.getCard1().getRank() + player2.getCard2().getRank();
                if (total1 == total2) {
                    return 0;
                } else if (total1 > total2) {
                    return 1;
                } else if (total2 > total1) {
                    return -1;
                }
            }
        }
        //four of a kind
        boolean fourKind1 = false;
        boolean fourKind2 = false;
        for (Card card : cards1) {
            int count = 0;
            for (Card c : cards1) {
                if (c.getRank() == card.getRank()) {
                    count++;
                }
            }
            if (count >= 4) {
                fourKind1 = true;
            }
        }
        for (Card card : cards2) {
            int count = 0;
            for (Card c : cards2) {
                if (c.getRank() == card.getRank()) {
                    count++;
                }
            }
            if (count >= 4) {
                fourKind2 = true;
            }
        }
        if (fourKind1 & !fourKind2) {
            wayWon = "Four of a kind";
            return 1;
        }
        if (fourKind2 & !fourKind1) {
            wayWon = "Four of a kind";
            return -1;
        }
        if (fourKind1 && fourKind2) {
            wayWon = "Four of a kind";
            int high1 = 0;
            int high2 = 0;
            for (Card card : cards1) {
                if (card.getRank() > high1) {
                    high1 = card.getRank();
                }
            }
            for (Card card : cards2) {
                if (card.getRank() > high2) {
                    high2 = card.getRank();
                }
            }
            if (high1 > high2) {
                return 1;
            }
            if (high2 > high1) {
                return -1;
            }
            if (high1 == high2) {
                return 0;
            }
        }
        //full house
        boolean three1 = false;
        boolean three2 = false;
        boolean full1 = false;
        boolean full2 = false;
        boolean pair1 = false;
        boolean pair2 = false;
        for (Card card : cards1) {
            int count = 0;
            for (Card c : cards1) {
                if (c.getRank() == card.getRank()) {
                    count++;
                }
            }
            if (count >= 3) {
                three1 = true;
            }
            if (count == 2) {
                pair1 = true;
            }
        }
        for (Card card : cards2) {
            int count = 0;
            for (Card c : cards2) {
                if (c.getRank() == card.getRank()) {
                    count++;
                }
            }
            if (count >= 3) {
                three2 = true;
            }
            if (count == 2 && three2) {
                pair2 = true;
            }
        }
        if (three1 && pair1) {
            full1 = true;
        }
        if (three2 && pair2) {
            full2 = true;
        }
        if (full1 && !full2) {
            wayWon = "Full house";
            return 1;
        }
        if (full2 && !full1) {
            wayWon = "Full house";
            return -1;
        }
        if (full1 && full2) {
            wayWon = "Full house";
            int high1 = 0;
            int high2 = 0;
            for (Card card : player1.getHand()) {
                if (card.getRank() > high1) {
                    high1 = card.getRank();
                }
            }
            for (Card card : player2.getHand()) {
                if (card.getRank() > high2) {
                    high2 = card.getRank();
                }
            }
            if (high1 > high2) {
                return 1;
            }
            if (high2 > high1) {
                return -1;
            }
            if (high1 == high2) {
                int total1 = player1.getCard1().getRank() + player1.getCard2().getRank();
                int total2 = player2.getCard1().getRank() + player2.getCard2().getRank();
                if (total1 == total2) {
                    return 0;
                } else if (total1 > total2) {
                    return 1;
                } else if (total2 > total1) {
                    return -1;
                }
            }
        }
        //flush check
        boolean flush1 = false;
        boolean flush2 = false;
        if (possibleFlush1) {
            for (Card card : cards1) {
                int count = 0;
                for (Card c : cards1) {
                    if (c.getSuit() == card.getSuit()) {
                        count++;
                    }
                }
                if (count >= 5) {
                    flush1 = true;
                }
            }
        }
        if (possibleFlush2) {
            for (Card card : cards2) {
                int count = 0;
                for (Card c : cards2) {
                    if (c.getSuit() == card.getSuit()) {
                        count++;
                    }
                }
                if (count >= 5) {
                    flush2 = true;
                }
            }
        }
        if (flush1 && !flush2) {
            wayWon = "Flush";
            return 1;
        } else if (flush2 && !flush1) {
            wayWon = "Flush";
            return -1;
        } else if (flush1 && flush2) {
            wayWon = "Flushes";
            return 0;
        }
        //straight check
        boolean straight1 = false;
        boolean straight2 = false;
        for (Card card : cards1) {
            int count = 1;
            int rank = card.getRank() + 1;
            boolean stop = false;
            while (rank > 15 & !stop) {
                if (cards1.contains(new Card(Suit.Spades, rank)) ||
                        cards1.contains(new Card(Suit.Clubs, rank)) ||
                        cards1.contains(new Card(Suit.Hearts, rank)) ||
                        cards1.contains(new Card(Suit.Diamonds, rank))) {
                    count++;
                    rank++;
                } else {
                    stop = true;
                }
            }
            stop = false;
            while (rank < 1 & !stop) {
                if (cards1.contains(new Card(Suit.Spades, rank)) ||
                        cards1.contains(new Card(Suit.Clubs, rank)) ||
                        cards1.contains(new Card(Suit.Hearts, rank)) ||
                        cards1.contains(new Card(Suit.Diamonds, rank))) {
                    count++;
                    rank++;
                } else {
                    stop = true;
                }
            }
            if (count >= 5) {
                straight1 = true;
            }
        }
        for (Card card : cards2) {
            int count = 1;
            int rank = card.getRank() + 1;
            boolean stop = false;
            while (rank > 15 & !stop) {
                if (cards2.contains(new Card(Suit.Spades, rank)) ||
                        cards2.contains(new Card(Suit.Clubs, rank)) ||
                        cards2.contains(new Card(Suit.Hearts, rank)) ||
                        cards2.contains(new Card(Suit.Diamonds, rank))) {
                    count++;
                    rank++;
                } else {
                    stop = true;
                }
            }
            stop = false;
            while (rank < 1 & !stop) {
                if (cards2.contains(new Card(Suit.Spades, rank)) ||
                        cards2.contains(new Card(Suit.Clubs, rank)) ||
                        cards2.contains(new Card(Suit.Hearts, rank)) ||
                        cards2.contains(new Card(Suit.Diamonds, rank))) {
                    count++;
                    rank++;
                } else {
                    stop = true;
                }
            }
            if (count >= 5) {
                straight2 = true;
            }
        }
        if (straight1 & !straight2) {
            wayWon = "Straight";
            return 1;
        }
        if (straight2 & !straight1) {
            wayWon = "Straight";
            return -1;
        }
        if (straight1 && straight2) {
            wayWon = "Straights";
            int high1 = 0;
            int high2 = 0;
            for (Card card : player1.getHand()) {
                if (card.getRank() > high1) {
                    high1 = card.getRank();
                }
            }
            for (Card card : player2.getHand()) {
                if (card.getRank() > high2) {
                    high2 = card.getRank();
                }
            }
            if (high1 > high2) {
                return 1;
            }
            if (high2 > high1) {
                return -1;
            }
            if (high1 == high2) {
                int total1 = player1.getCard1().getRank() + player1.getCard2().getRank();
                int total2 = player2.getCard1().getRank() + player2.getCard2().getRank();
                if (total1 == total2) {
                    return 0;
                } else if (total1 > total2) {
                    return 1;
                } else if (total2 > total1) {
                    return -1;
                }
            }
            return 0;
        }
        //three of a kind
        if (three1 & !three2) {
            wayWon = "Three of a kind";
            return 1;
        }
        if (three2 & !three1) {
            wayWon = "Three of a kind";
            return -1;
        }
        if (three1 && three2) {
            wayWon = "Three of a kind";
            int high1 = 0;
            int high2 = 0;
            for (Card card : player1.getHand()) {
                if (card.getRank() > high1) {
                    high1 = card.getRank();
                }
            }
            for (Card card : player2.getHand()) {
                if (card.getRank() > high2) {
                    high2 = card.getRank();
                }
            }
            if (high1 > high2) {
                return 1;
            }
            if (high2 > high1) {
                return -1;
            }
            if (high1 == high2) {
                int total1 = player1.getCard1().getRank() + player1.getCard2().getRank();
                int total2 = player2.getCard1().getRank() + player2.getCard2().getRank();
                if (total1 == total2) {
                    return 0;
                } else if (total1 > total2) {
                    return 1;
                } else if (total2 > total1) {
                    return -1;
                }
            }
        }
        //pair, two pair, and high card check
        int numPairs1 = 0;
        int numPairs2 = 0;
        for (Card card : cards1) {
            int count = 0;
            for (Card c : cards1) {
                if (c.getRank() == card.getRank()) {
                    count++;
                }
            }
            if (count >= 2) {
                numPairs1++;
            }
        }
        numPairs1 = numPairs1 / 2;
        for (Card card : cards2) {
            int count = 0;
            for (Card c : cards2) {
                if (c.getRank() == card.getRank()) {
                    count++;
                }
            }
            if (count >= 2) {
                numPairs2++;
            }
        }
        numPairs2 = numPairs2 / 2;
        if (numPairs1 > numPairs2) {
            wayWon = "2 card pair";
            return 1;
        } else if (numPairs2 > numPairs1) {
            wayWon = "2 card pair";
            return -1;
        }
        if (numPairs1 == numPairs2) {
            int high1 = 0;
            int high2 = 0;
            for (Card card : player1.getHand()) {
                if (card.getRank() > high1) {
                    high1 = card.getRank();
                }
            }
            for (Card card : player2.getHand()) {
                if (card.getRank() > high2) {
                    high2 = card.getRank();
                }
            }
            if (high1 > high2) {
                return 1;
            }
            if (high2 > high1) {
                return -1;
            }
            if (high1 == high2) {
                int total1 = player1.getCard1().getRank() + player1.getCard2().getRank();
                int total2 = player2.getCard1().getRank() + player2.getCard2().getRank();
                if (total1 == total2) {
                    return 0;
                } else if (total1 > total2) {
                    return 1;
                } else if (total2 > total1) {
                    return -1;
                }
            }
        }
        int high1 = 0;
        int high2 = 0;
        for (Card card : player1.getHand()) {
            if (card.getRank() > high1) {
                high1 = card.getRank();
            }
        }
        for (Card card : player2.getHand()) {
            if (card.getRank() > high2) {
                high2 = card.getRank();
            }
        }
        if (high1 > high2) {
            return 1;
        }
        if (high2 > high1) {
            return -1;
        }
        if (high1 == high2) {
            int total1 = player1.getCard1().getRank() + player1.getCard2().getRank();
            int total2 = player2.getCard1().getRank() + player2.getCard2().getRank();
            if (total1 == total2) {
                return 0;
            } else if (total1 > total2) {
                return 1;
            } else if (total2 > total1) {
                return -1;
            }
        }
        return 0;
    }
    /**
     * This method is called at the end of startGame() method if a player has won.
     * Winner is decided based on the amount of each player's chips and winner is announced to user.
     */
    public static void endGame() {
        //whoever still has chips at the end of game is the winner
        for (Player person : players) {
            if (person.getChips() > winner.getChips()) {
                winner = person;
            }
            //announce the winner
            System.out.print("The winner is: " + winner + "!");
        }
    }

    /**
     * this method is used to get all players.
     * @return an ArrayList of Player objects
     */
    public static ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This method is used to get all the face up cards.
     * @return An ArrayList of Card objects
     */
    public static ArrayList<Card> getFaceUp() {
        return faceUp;
    }
    /**
     * This method is used to find which round we are currently in.
     * @return An int corresponding to the round.
     */
    public static int getRound() {
        return rounds;
    }

    /**
     * This method updates the round to the int specified.
     * @param round int (the round number)
     */
    public static void setRound(int round) {
        Game.rounds = round;
    }

    /**
     * This method runs the entire program.
     * creates Player's and then calls to start the game.
     *
     * @param args
     */
    public static synchronized void main(String[] args) throws IllegalMonitorStateException {
        System.out.print("Welcome! Lets start the game!");
        System.out.println("Please enter your name.");
        //ask for the user's name
        Scanner in = new Scanner(System.in);
        String user = in.next();
        //add user and computer to players. Start out with 50 chips.
        players.add(new Player(user, 50, false));
        players.add(new Player("Computer", 50, true));
        //start the game
        Game.startGame(players);
    }
}
