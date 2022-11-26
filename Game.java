import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    //private static Card
    private static boolean gameOver = false;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Card> faceUp = new ArrayList<>();
    private static int handSize = 2;
    private static Player winner = new Player("winner", 0);
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
        //Show your hand
        for(Player person : players){
            if(!person.isComputer()){
                System.out.println(person.getHand());
            }
        }
        //show the face up cards (flop)
        System.out.println(faceUp);

    }
    public static void reset(){

    }
    public static void move(boolean b){

    }
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
