import java.util.ArrayList;
import java.util.Scanner;
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
        //add 2 cards to each player's hand
        for(Player person : players) {
            for (int i = 0; i < handSize; i++) {
                person.addToHand(newTable.deal());
            }
        }
        //deal the first 3 face up cards (The Flop)
        faceUp = newTable.flop();
        //call playerDecision() method to get each person's bet for the round
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
        //reset()

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
        //different Players will start each round, so we shift all players one spot over
        Player lastPlayer = players.get(players.size() - 1);
        for (int i = players.size(); i > 0; i--) {
            players.set(i, players.get(i - 1));
        }
        //lastPlayer gets moved to the first position
        players.set(0, lastPlayer);

        //each player begins their own bets for the round
        for (Player person : players) {
            if(person.isComputer() == false){
                person.bet();
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
        Player temp = new Player("",0);
        for(Player person : players){
            //if (person.getHand() > winner.gethand()
        }
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
