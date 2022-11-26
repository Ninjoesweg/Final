import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>();
    private int chips;
    private boolean computer;
    public Player(String name, int chips){
        this.chips = chips;
        this.hand = hand;
    }
    public void Hand(){

    }

    public int getChips() {
        return chips;
    }

    public boolean isComputer() {
        return computer;
    }
}

