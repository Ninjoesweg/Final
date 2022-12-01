import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Handler;

public class Game {
    //private static Card
    private static boolean gameOver = false;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Card> faceUp = new ArrayList<>();
    private static int handSize = 2;
    private static Player winner = new Player("winner", 0, true);
    private static boolean hasMoved = true;
    private static int rounds;
    private static Table newTable = new Table();

    /**
     * This method begins the game. Game continues until somebody wins.
     * @param players ArrayList containing all Player objects
     */
    public static synchronized void startGame(ArrayList<Player> players)throws IllegalMonitorStateException{
        //start new rounds until game is over
        //while(!gameOver){
            setRound(1);
            startRound(rounds);
            //reset all cards in hand and on table between rounds
            //reset();
        //}
        //if game is over
        endGame();
    }

    /**
     * This method is called in the startGame() method to begin each round of the game.
     * Cards will be shuffled. Each player is dealt 2 cards and the first 3 face up cards are dealt.
     */
    public static synchronized void startRound(int round) throws IllegalMonitorStateException{
        if(round == 1) {
            //create new deck of cards and shuffle
            newTable = new Table();
            newTable.add52();
            newTable.shuffle();
           /* //different Players will start each round, so we shift all players one spot over
            Player lastPlayer = players.get(players.size() - 1);
            for (int i = players.size() - 1; i > 0; i--) {
                players.set(i, players.get(i - 1));
            }
            //lastPlayer gets moved to the first position
            players.set(0, lastPlayer);
        */
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
            for(Player p : getPlayers()) {
                if (!p.isComputer() && !p.isFold()){
                    Animation animation = new Animation();
                animation.main();
            }
            }
        }
        if(round == 2 || round == 3){
            faceUp.add(newTable.deal());
            //create a new Animation to use GUI and bet!
            Animation animation = new Animation();
            animation.main();

        }
        else{
            roundWinner();
        }

        //playerDecision();
        int x=0;
        /**while (x<5 && hasMoved == true){
            x++;
            if(x==1) {
                //add 2 cards to each player's hand
                for (Player person : players) {
                    for (int i = 0; i < handSize; i++) {
                        person.addToHand(newTable.deal());
                    }

                    x++;
                }
            }
                //create a new Animation to use GUI and bet!
                Animation animation = new Animation();
                animation.main();
            }
        }*/

        //bet
        /*animation.main();
        playerDecision();
        //hasMoved = false;
        //add another card to faceUp Arraylist
        faceUp.add(newTable.deal());
        //bet
        animation.main();
        playerDecision();
        //add last card to face UpArraylist
        faceUp.add(newTable.deal());
        //last bets for this round
        animation.main();
        playerDecision();
        */
        //winner gets all chips in pot
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
        Bet.setBetPerPerson(0);
        Bet.setPot(0);
        for (Player p: getPlayers()) {
            p.setHand(new ArrayList<Card>());
            p.setChipsInPot(0);
            p.setFold(false);
            if(p.getChips() <= 0){
                players.remove(p);
            }
            if(players.size() == 1){
                gameOver = true;
            }

        }
        if(gameOver){
            System.out.println("Winner is " + getPlayers().get(0).getName());
            System.exit(0);
        }
            setRound(1);
            startRound(rounds);
    }
    public static boolean move(Player p){
        hasMoved = true;
        return hasMoved;
    }
    public static void roundWinner(){
        boolean tie = false;
        Player temp = null;
        Player temp2 = null;
        for(Player person : players){
            //if (person.getHand() > winner.gethand()
            if(person.isFold()){

            }
            else if(temp == null || compareHands(temp, person) == -1 ){
                temp = person;
                tie = false;
                temp2 = null;
            }
            else if(compareHands(temp, person) == 0){
                tie = true;
                temp2 = person;
            }
        }
        int winnings;
        if(tie){
            winnings = Bet.getPot() / 2;
            temp.setChips(temp.getChips() + winnings);
            temp2.setChips(temp2.getChips() + winnings);
            System.out.println("Tie");
        }
        else {
            winnings = Bet.getPot();
            temp.setChips(temp.getChips() + winnings);
            System.out.println("winner is "+ temp.getName());
        }
        reset();
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
        boolean possibleFlush1 = false;
        boolean possibleFlush2 = false;
        if(spade1 > 4 || club1 > 4 || heart1 > 4 || diamond1 > 4){
            possibleFlush1 = true;
        }
        if(spade2 > 4 || club2 > 4 || heart2 > 4 || diamond2 > 4){
            possibleFlush2 = true;
        }
        boolean royal1 = false;
        boolean royal2 = false;
        //royal flush check
        if(possibleFlush1){
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
        if(possibleFlush2) {
            if (cards2.contains(new Card(Suit.Spades, 14)) &&
                    cards2.contains(new Card(Suit.Spades, 13)) &&
                    cards2.contains(new Card(Suit.Spades, 12)) &&
                    cards2.contains(new Card(Suit.Spades, 11)) &&
                    cards2.contains(new Card(Suit.Spades, 10))) {
                royal2 = true;
            }
            if (cards2.contains(new Card(Suit.Clubs, 14)) &&
                    cards2.contains(new Card(Suit.Clubs, 13)) &&
                    cards2.contains(new Card(Suit.Clubs, 12)) &&
                    cards2.contains(new Card(Suit.Clubs, 11)) &&
                    cards2.contains(new Card(Suit.Clubs, 10))) {
                royal2 = true;
            }
            if (cards2.contains(new Card(Suit.Hearts, 14)) &&
                    cards2.contains(new Card(Suit.Hearts, 13)) &&
                    cards2.contains(new Card(Suit.Hearts, 12)) &&
                    cards2.contains(new Card(Suit.Hearts, 11)) &&
                    cards2.contains(new Card(Suit.Hearts, 10))) {
                royal2 = true;
            }
            if (cards2.contains(new Card(Suit.Diamonds, 14)) &&
                    cards2.contains(new Card(Suit.Diamonds, 13)) &&
                    cards2.contains(new Card(Suit.Diamonds, 12)) &&
                    cards2.contains(new Card(Suit.Diamonds, 11)) &&
                    cards2.contains(new Card(Suit.Diamonds, 10))) {
                royal2 = true;
            }
        }
        if(royal1 &! royal2){
            System.out.println("Royal flush - " + player1.getName());
            return 1;
        }
        if(royal2 &! royal1){
            System.out.println("Royal flush - " + player2.getName());
            return -1;
        }
        if(royal1 && royal2){
            System.out.println("Royal flush tie");
            return 0;
        }
        boolean straightFlush1 = false;
        boolean straightFlush2 = false;
        if(possibleFlush1){
            for (Card card: cards1) {
                boolean stop = false;
                int count = 1;
                int rank = card.getRank() + 1;
                while(rank < 15 || !stop){
                    if(cards1.contains(new Card(card.getSuit(), rank))){
                        count++;
                        rank++;
                    }
                    else{
                        stop = true;
                    }
                }
                stop = false;
                while(rank > 2 || !stop){
                    if(cards1.contains(new Card(card.getSuit(), rank))){
                        count++;
                        rank--;
                    }
                    else{
                        stop = true;
                    }
                }
                if(count >= 5){
                    straightFlush1 = true;
                }
            }
        }
        if(possibleFlush2){
            for (Card card: cards2) {
                boolean stop = false;
                int count = 1;
                int rank = card.getRank() + 1;
                while(rank < 15 || !stop){
                    if(cards2.contains(new Card(card.getSuit(), rank))){
                        count++;
                        rank++;
                    }
                    else{
                        stop = true;
                    }
                }
                stop = false;
                rank = card.getRank() - 1;
                while(rank > 1 || !stop){
                    if(cards2.contains(new Card(card.getSuit(), rank))){
                        count++;
                        rank--;
                    }
                    else{
                        stop = true;
                    }
                }
                if(count >= 5){
                    straightFlush2 = true;
                }
            }
        }
        if(straightFlush1 &! straightFlush2){
            System.out.println("Straight flush - " + player1.getName());
            return 1;
        }
        if(straightFlush2 &! straightFlush1){
            System.out.println("Straight flush - " + player2.getName());
            return -1;
        }
        if(straightFlush1 && straightFlush2){
            System.out.println("Straight flushes");
            int high1 = 0;
            int high2 = 0;
            for (Card card:cards1) {
                if(card.getRank() > high1){
                    high1 = card.getRank();
                }
            }
            for (Card card:cards2) {
                if(card.getRank() > high2){
                    high2 = card.getRank();
                }
            }
            if(high1 > high2){
                return 1;
            }
            if(high2 > high1){
                return -1;
            }
            if(high1 == high2){
                return 0;
            }
        }
        boolean fourKind1 = false;
        boolean fourKind2 = false;
        for (Card card:cards1) {
            int count = 0;
            for (Card c:cards1) {
                if(c.getRank() == card.getRank()){
                    count++;
                }
            }
            if(count >= 4){
                fourKind1 = true;
            }
        }
        for (Card card:cards2) {
            int count = 0;
            for (Card c:cards2) {
                if(c.getRank() == card.getRank()){
                    count++;
                }
            }
            if(count >= 4){
                fourKind2 = true;
            }
        }
        if (fourKind1 &! fourKind2){
            System.out.println("Four of a kind - " + player1.getName());
            return 1;
        }
        if(fourKind2 &! fourKind1){
            System.out.println("Four of a kind - " + player2.getName());
            return -1;
        }
        if(fourKind1 && fourKind2){
            System.out.println("Four of a kind");
            int high1 = 0;
            int high2 = 0;
            for (Card card:cards1) {
                if(card.getRank() > high1){
                    high1 = card.getRank();
                }
            }
            for (Card card:cards2) {
                if(card.getRank() > high2){
                    high2 = card.getRank();
                }
            }
            if(high1 > high2){
                return 1;
            }
            if(high2 > high1){
                return -1;
            }
            if(high1 == high2){
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
        for (Card card:cards1) {
            int count = 0;
            for (Card c:cards1) {
                if(c.getRank() == card.getRank()){
                    count++;
                }
            }
            if(count >= 3){
                three1 = true;
            }
            if(count == 2){
                pair1 = true;
            }
        }
        for (Card card:cards2) {
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
            if(three1 && pair1){
                full1 = true;
            }
            if(three2 && pair2){
                full2 = true;
            }
            if(full1 && !full2){
                System.out.println("Full house " + player1.getName());
                return 1;
            }
            if(full2 && !full1){
                System.out.println("Full house " + player2.getName());
                return -1;
            }
            if(full1 && full2){
                System.out.println("Full house ");
                int high1 = 0;
                int high2 = 0;
                for (Card card:cards1) {
                    if(card.getRank() > high1){
                        high1 = card.getRank();
                    }
                }
                for (Card card:cards2) {
                    if(card.getRank() > high2){
                        high2 = card.getRank();
                    }
                }
                if(high1 > high2){
                    return 1;
                }
                if(high2 > high1){
                    return -1;
                }
                if(high1 == high2){
                    return 0;
                }

            }
        //flush check
        boolean flush1 = false;
        boolean flush2 = false;
        if(possibleFlush1){
            for (Card card: cards1) {
                int count = 0;
                for(Card c: cards1){
                    if(c.getSuit() == card.getSuit()){
                        count++;
                    }
                }
                if(count >= 5){
                    flush1 = true;
                }
            }
        }
        if(possibleFlush2){
            for (Card card: cards2) {
                int count = 0;
                for(Card c: cards2){
                    if(c.getSuit() == card.getSuit()){
                        count++;
                    }
                }
                if(count >= 5){
                    flush2 = true;
                }
            }
        }
        if(flush1 && !flush2){
            System.out.println("Flush - " + player1.getName());
            return 1;
        }
        else if(flush2 && !flush1){
            System.out.println("Flush - " + player2.getName());
            return -1;
        }
        else if(flush1 && flush2){
            System.out.println("Flushes");
            return 0;
        }
        //straight check
        boolean straight1 = false;
        boolean straight2 = false;
        for (Card card : cards1){
            int count = 1;
            int rank = card.getRank() + 1;
            boolean stop = false;
            while(rank > 15 &! stop){
                if(cards1.contains(new Card(Suit.Spades, rank)) ||
                cards1.contains(new Card(Suit.Clubs, rank)) ||
                cards1.contains(new Card(Suit.Hearts, rank)) ||
                cards1.contains(new Card(Suit.Diamonds, rank))){
                    count++;
                    rank++;
                }
                else{
                    stop = true;
                }
            }
            stop = false;
            while(rank < 1 &! stop){
                if(cards1.contains(new Card(Suit.Spades, rank)) ||
                        cards1.contains(new Card(Suit.Clubs, rank)) ||
                        cards1.contains(new Card(Suit.Hearts, rank)) ||
                        cards1.contains(new Card(Suit.Diamonds, rank))){
                    count++;
                    rank++;
                }
                else{
                    stop = true;
                }
            }
            if(count >= 5){
                straight1 = true;
            }
        }
        for (Card card : cards2){
            int count = 1;
            int rank = card.getRank() + 1;
            boolean stop = false;
            while(rank > 15 &! stop){
                if(cards2.contains(new Card(Suit.Spades, rank)) ||
                        cards2.contains(new Card(Suit.Clubs, rank)) ||
                        cards2.contains(new Card(Suit.Hearts, rank)) ||
                        cards2.contains(new Card(Suit.Diamonds, rank))){
                    count++;
                    rank++;
                }
                else{
                    stop = true;
                }
            }
            stop = false;
            while(rank < 1 &! stop){
                if(cards2.contains(new Card(Suit.Spades, rank)) ||
                        cards2.contains(new Card(Suit.Clubs, rank)) ||
                        cards2.contains(new Card(Suit.Hearts, rank)) ||
                        cards2.contains(new Card(Suit.Diamonds, rank))){
                    count++;
                    rank++;
                }
                else{
                    stop = true;
                }
            }
            if(count >= 5){
                straight2 = true;
            }
        }
        if(straight1 &! straight2){
            System.out.println("Straight - " + player1.getName());
            return 1;
        }
        if(straight2 &! straight1){
            System.out.println("Straight - " + player2.getName());
            return  -1;
        }
        if(straight1 && straight2){
            System.out.println("Straights");
            return 0;
        }
        //three
        if (three1 &! three2){
            System.out.println("Three of a kind - " + player1.getName());
            return 1;
        }
        if(three2 &! three1){
            System.out.println("Three of a kind - " + player2.getName());
            return -1;
        }
        if(three1 && three2){
            System.out.println("Three of a kind");
            int high1 = 0;
            int high2 = 0;
            for (Card card:cards1) {
                if(card.getRank() > high1){
                    high1 = card.getRank();
                }
            }
            for (Card card:cards2) {
                if(card.getRank() > high2){
                    high2 = card.getRank();
                }
            }
            if(high1 > high2){
                return 1;
            }
            if(high2 > high1){
                return -1;
            }
            if(high1 == high2){
                return 0;
            }
        }
        //pair, two pair, and high card check
        int numPairs1 = 0;
        int numPairs2 = 0;
        for (Card card:cards1) {
            int count = 0;
            for (Card c:cards1) {
                if(c.getRank() == card.getRank()){
                    count++;
                }
            }
            if(count >= 2){
                numPairs1++;
            }
        }
        numPairs1 = numPairs1 / 2;
        for (Card card:cards2) {
            int count = 0;
            for (Card c:cards2) {
                if(c.getRank() == card.getRank()){
                    count++;
                }
            }
            if(count >= 2){
                numPairs2++;
            }
        }
        numPairs2 = numPairs2 / 2;
        if (numPairs1 > numPairs2){
            System.out.println("More pairs " + player1.getName());
            return 1;
        }
        if(numPairs2 > numPairs1){
            System.out.println("More pairs " + player2.getName());
            return -1;
        }
        if(numPairs1 == numPairs2){
            int high1 = 0;
            int high2 = 0;
            for (Card card:cards1) {
                if(card.getRank() > high1){
                    high1 = card.getRank();
                }
            }
            for (Card card:cards2) {
                if(card.getRank() > high2){
                    high2 = card.getRank();
                }
            }
            if(high1 > high2){
                System.out.println("High card "  + player1.getName());
                return 1;
            }
            if(high2 > high1){
                System.out.println("High card "  + player2.getName());

                return -1;
            }
            if(high1 == high2){
                System.out.println("High card tie");

                return 0;
            }
        }
        int high1 = 0;
        int high2 = 0;
        for (Card card:cards1) {
            if(card.getRank() > high1){
                high1 = card.getRank();
            }
        }
        for (Card card:cards2) {
            if(card.getRank() > high2){
                high2 = card.getRank();
            }
        }
        if(high1 > high2){
            return 1;
        }
        if(high2 > high1){
            return -1;
        }
        if(high1 == high2){
            return 0;
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
    public static ArrayList<Player> getPlayers(){
        return players;
    }
    public static ArrayList<Card> getFaceUp(){
        return faceUp;
    }

    public static void setHasMoved(boolean hasMoved) {
        Game.hasMoved = hasMoved;
    }

    public static int getRound() {
        return rounds;
    }

    public static void setRound(int round) {
        Game.rounds = round;
    }

    /**
     * This method runs the entire program.
     * creates Player's and then calls to start the game.
     * @param args
     */
    public static synchronized void main(String[] args)throws IllegalMonitorStateException{
        System.out.print("Welcome! Lets start the game!");
        System.out.println("Please enter your name.");
        //ask for the user's name
        Scanner in = new Scanner(System.in);
        String user = in.next();
        //add user and computer to players. Start out with 50 chips.
        players.add(new Player(user, 50, false));
        players.add(new Player("Computer",50, true));
        //start the game
        Game.startGame(players);
    }
}
