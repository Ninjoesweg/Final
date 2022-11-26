import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>();
    private int chips;
    private String name;
    private boolean computer;
    public Player(String name, int chips){
        this.chips = chips;
        this.hand = hand;
    }
    public void Hand(){

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

}

