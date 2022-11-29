import java.util.ArrayList;
public class Player {
    private ArrayList<Card> hand;
    private int chips;
    private String name;
    private boolean computer;
    private int chipsInPot;
    private boolean fold;
    private Card card1;
    private Card card2;
    public Player(String name, int chips){
        this.name = name;
        this.chips = chips;
        this.hand = new ArrayList<>();
        this.chipsInPot = 0;
        this.fold = false;
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
    public int getChipsInPot(){return this.chipsInPot;}

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public boolean isFold() {return fold;}

    public void setChips(int chips) {this.chips = chips;}

    public void setFold(boolean fold) {this.fold = fold;}

    public boolean isComputer() {
        if(getName().equalsIgnoreCase("Computer")){
            computer = true;
        }
        return computer;
    }
}

