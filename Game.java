import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Handler;

public class Game {
    //private static Card
    private static boolean gameOver = false;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Card> faceUp = new ArrayList<>();
    private static int handSize = 2;
    private static Player winner = new Player("winner", 0);

    /**
     * This method begins the game. Game continues until somebody wins.
     * @param players ArrayList containing all Player objects
     */
    public static void startGame(ArrayList<Player> players){
        //start new rounds until game is over
        while(gameOver == false){
            startRound();
            //reset all cards in hand and on table between rounds
            reset();
        }
        //if game is over
        endGame();
    }

    /**
     * This method is called in the startGame() method to begin each round of the game.
     * Cards will be shuffled. Each player is dealt 2 cards and the first 3 face up cards are dealt.
     */
    public static void startRound(){
        //create new deck of cards and shuffle
        Table newTable = new Table();
        newTable.add52();
        newTable.shuffle();
        //different Players will start each round, so we shift all players one spot over
        Player lastPlayer = players.get(players.size() - 1);
        for (int i = players.size(); i > 0; i--) {
            players.set(i, players.get(i - 1));
        }
        //lastPlayer gets moved to the first position
        players.set(0, lastPlayer);

        //add 2 cards to each player's hand
        for(Player person : players) {
            for (int i = 0; i < handSize; i++) {
                person.addToHand(newTable.deal());
            }
        }
        //bet
        playerDecision();
        //deal the first 3 face up cards (The Flop)
        int c = 0;
        while(c > 3){
            faceUp.add(newTable.deal());
        }
        //bet
        playerDecision();
        //add another card to faceUp Arraylist
        faceUp.add(newTable.deal());
        //bet
        playerDecision();
        //add last card to face UpArraylist
        faceUp.add(newTable.deal());
        //last bets for this round
        playerDecision();
        //winner gets all chips in pot
        roundWinner();
    }
    /**
     * This method is called in the startRound() method. It begins the decision-making process for each player.
     * Prints out to user the face up cards and the user's hand.
     * Players will decide whether they want to bet/call/raise/etc.
     */
    public static void playerDecision() {
        //show the face up cards (flop)
        System.out.println("Face up cards: " + faceUp);
        //Show your hand
        for(Player person : players){
            if(!person.isComputer()){
                System.out.println("Your cards: " + person.getHand());
            }
        }
        //each player begins their own bets for the round
        for (Player person : players) {
            if(person.isComputer() == false){
                Bet.decisionMenu(person);
            }
            else if(person.isComputer()){
                //person.autoBet();
            }
        }
    }


    public static void reset(){

    }
    public static void move(boolean b){

    }
    public static void roundWinner(){

        Player temp = null;
        for(Player person : players){
            //if (person.getHand() > winner.gethand()
            if(temp == null || compareHands(temp, person) == 0 ){
                temp = person;
            }
        }
    }
    public static int compareHands(Player player1, Player player2){
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards1.add(player1.getCard1());
        cards1.add(player1.getCard2());
        for (Card card: faceUp) {
            cards1.add(card);
        }
        cards2.add(player2.getCard1());
        cards2.add(player2.getCard2());
        for (Card card: faceUp) {
            cards2.add(card);
        }
        //check for royal flush
        int heart1 = 0;
        int diamond1 = 0;
        int club1 = 0;
        int spade1 = 0;
        for (Card card: cards1) {
            if(card.getSuit() == Suit.Hearts){
                heart1++;
            }
            else if(card.getSuit() == Suit.Diamonds){
                diamond1++;
            }
            else if(card.getSuit() == Suit.Clubs){
                club1++;
            }
            else if(card.getSuit() == Suit.Spades){
                spade1++;
            }
        }
        int heart2 = 0;
        int diamond2 = 0;
        int club2 = 0;
        int spade2 = 0;
        for (Card card: cards1) {
            if(card.getSuit() == Suit.Hearts){
                heart2++;
            }
            else if(card.getSuit() == Suit.Diamonds){
                diamond2++;
            }
            else if(card.getSuit() == Suit.Clubs){
                club2++;
            }
            else if(card.getSuit() == Suit.Spades){
                spade2++;
            }
        }
        boolean royal1 = false;
        boolean royal2 = false;
        if(spade1 > 4 || club1 > 4 || heart1 > 4 || diamond1 > 4){
            if(cards1.contains(new Card(Suit.Spades, 14)) &&
                    cards1.contains(new Card(Suit.Spades, 13)) &&
                    cards1.contains(new Card(Suit.Spades, 12)) &&
                    cards1.contains(new Card(Suit.Spades, 11)) &&
                    cards1.contains(new Card(Suit.Spades, 10))){
                royal1 = true;
            }
            if(cards1.contains(new Card(Suit.Clubs, 14)) &&
                    cards1.contains(new Card(Suit.Clubs, 13)) &&
                    cards1.contains(new Card(Suit.Clubs, 12)) &&
                    cards1.contains(new Card(Suit.Clubs, 11)) &&
                    cards1.contains(new Card(Suit.Clubs, 10))){
                royal1 = true;
            }
            if(cards1.contains(new Card(Suit.Hearts, 14)) &&
                    cards1.contains(new Card(Suit.Hearts, 13)) &&
                    cards1.contains(new Card(Suit.Hearts, 12)) &&
                    cards1.contains(new Card(Suit.Hearts, 11)) &&
                    cards1.contains(new Card(Suit.Hearts, 10))){
                royal1 = true;
            }
            if(cards1.contains(new Card(Suit.Diamonds, 14)) &&
                    cards1.contains(new Card(Suit.Diamonds, 13)) &&
                    cards1.contains(new Card(Suit.Diamonds, 12)) &&
                    cards1.contains(new Card(Suit.Diamonds, 11)) &&
                    cards1.contains(new Card(Suit.Diamonds, 10))){
                royal1 = true;
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
     * This method runs the entire program.
     * creates Player's and then calls to start the game.
     * @param args
     */
    public static void main(String[] args){
        System.out.print("Welcome! Lets start the game!");
        System.out.println("Please enter your name.");
        //ask for the user's name
        Scanner in = new Scanner(System.in);
        String user = in.next();
        //add user and computer to players. Start out with 50 chips.
        players.add(new Player(user, 50));
        players.add(new Player("Computer",50));
        //start the game
        Game.startGame(players);
    }
}
