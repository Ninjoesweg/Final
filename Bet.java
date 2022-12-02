import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class has various methods for each betting options raise, call, check, fold, and go all-in
 * Chips are moved as needed.
 */
public class Bet {
    private static int pot = 0;
    private static int betPerPerson = 0;

    public static void decisionMenu(Player player){
        System.out.println("Would you like to: \n1: Call \n2:Raise \n3: Check \n 4: Fold \n5: All in");
        Scanner in = new Scanner(System.in);
        int answer = in.nextInt();
        //try catch block, in case user input does not match numbers 1-5
        try{
            if(answer == 1) {
                call(player);
            } else if (answer == 2) {
                raise(player);
            } else if (answer == 3) {
                check(player);
            }else if(answer == 4){
                fold(player);
            }else if(answer == 5) {
                allin(player);
            }else {
                System.out.println("That is not a valid number.");
                decisionMenu(player);
            }
        } catch(InputMismatchException e){
            System.out.println("That is not a valid response. Please enter the number corresponding to your choice");
            decisionMenu(player);
        }
    }
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
                }
            }catch(InputMismatchException e){
                System.out.println("ERROR input not recognized");
                scan.next();
            }
        }
        for(Player p : Game.getPlayers()){
            if(p.isComputer()){
                call(p);
            }
        }
    }
    public static void check(Player player){

    }
    public static void fold(Player player){
        player.setFold(true);
    }
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

    public static int getBetPerPerson() {
        return betPerPerson;
    }

    public static int getPot() {
        return pot;
    }

    public static void setPot(int pot) {
        Bet.pot = pot;
    }

    public static void setBetPerPerson(int betPerPerson) {
        Bet.betPerPerson = betPerPerson;
    }
}
