import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class has various methods for each betting options raise, call, check, fold, and go all-in
 * Chips are moved as needed.
 */
public class Bet {
    private static int pot = 0;
    private static int betPerPerson = 0;

    /**
     * This method is used if a Player wishes to call. They want to match the bet of the other player.
     * @param player Obeject.
     */
    public static void call(Player player){
        int bet;
        if(betPerPerson - player.getChipsInPot() <= player.getChips()) {
            bet = betPerPerson - player.getChipsInPot();
        }
        else{
            bet = player.getChips();
        }
        pot += bet;
        player.setChips(player.getChips()-bet);
        player.setChipsInPot(player.getChipsInPot() + bet);
    }

    /**
     * This method is called when a player wishes to raise. They want to bet more money to put in the pot.
     * @param player Player who wants to raise
     */
    public static void raise(Player player){
        Scanner scan = new Scanner(System.in);
        Boolean done = false;
        while(!done){
            try {
                System.out.println("Choose bet");
                int bet = scan.nextInt();
                if (bet < 0){
                    System.out.println("Cannot use negative values!");
                }
                else if(bet < player.getChipsInPot() - betPerPerson){
                    System.out.println("Cannot raise to a value lower than the current bet");
                }
                else if(player.getChips() >= bet){
                    player.setChips(player.getChips()-bet);
                    player.setChipsInPot(player.getChipsInPot() + bet);
                    pot += bet;
                    betPerPerson += bet;
                    done = true;
                }
                else{
                    System.out.println("Not enough chips to bet that high!");
                    done= true;
                    raise(player);
                }
            }catch(InputMismatchException e){
                System.out.println("ERROR input not recognized");
                done = true;
                raise(player);
            }
        }
        for(Player p : Game.getPlayers()){
            if(p.isComputer()){
                call(p);
            }
        }
    }

    /**
     * This method is called when a player checks.
     * They do not wish to bet anymore chips, but still want to move on to next round.
     * @param player Player who wishes to check
     */
    public static void check(Player player){

    }

    /**
     * This method is called when the user presses the fold button.
     * Player chooses to give up on this round other Player wins round.
     * Sets setFold to true for the player method is called on.
     * @param player Player who folded
     */
    public static void fold(Player player){
        player.setFold(true);
    }

    /**
     * This method is called when the user presses the all-in button in the GUI.
     * puts all of the Player's chips in the pot.
     * @param player
     */
    public static void allin(Player player){
        pot += player.getChips();
        betPerPerson += player.getChips();
        player.setChips(0);
        for(Player p : Game.getPlayers()){
            if(p.isComputer()){
                call(p);
            }
        }
    }

    /**
     * This method is used to get the bets per person.
     * @return int the bet a player has made.
     */
    public static int getBetPerPerson() {
        return betPerPerson;
    }

    /**
     * This method is used to get the amount of chips in the pot
     * @return int, the number of chips in the pot
     */
    public static int getPot() {
        return pot;
    }

    /**
     * This method updates the amount in the pot.
     * @param pot int, the number of chips that the pot will be set to
     */
    public static void setPot(int pot) {
        Bet.pot = pot;
    }
    /**
     * This method sets the total amount a person has bet.
     * @param betPerPerson int
     */
    public static void setBetPerPerson(int betPerPerson) {
        Bet.betPerPerson = betPerPerson;
    }
}
