import java.util.ArrayList;
public class Player {
    private ArrayList<Card> hand;
    private int chips;
    private String name;
    private boolean computer;
    public Player(String name, int chips){
        this.name = name;
        this.chips = chips;
        this.hand = new ArrayList<>();
    }
    public void Hand(){

    }
    public void addToHand(Card x){
        this.hand.add(x);
    }
    public ArrayList<Card> getHand(){
        return this.hand;
    }

    public int getChips() {
        return this.chips;
    }
    public String getName(){
        return this.name;
    }

    public boolean isComputer() {
        if(getName().equalsIgnoreCase("Computer")){
            computer = true;
        }
        return computer;
    }
    public void bet(){
        Bet bet = new Bet();
        bet.decisionMenu();
    }
}

