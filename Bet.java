import java.util.InputMismatchException;
import java.util.Scanner;
public class Bet {
    private static int pot = 0;
    private static int betPerPerson = 0;

    public static void decisionMenu(Player player){
        boolean call = false;
        boolean raise = false;
        boolean check = false;
        if(betPerPerson > player.getChipsInPot() && player.getChips() > (betPerPerson - player.getChipsInPot())){
            call = true;
        }
        if(player.getChips() > (betPerPerson - player.getChipsInPot())){
            raise = true;
        }
        if(betPerPerson == player.getChipsInPot()){
            check = true;
        }
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
                check();
            }else if(answer == 4){
                fold();
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
        int bet = betPerPerson - player.getChipsInPot();
        betPerPerson += bet;
        pot += bet;
        player.setChips(player.getChips()-bet);
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
                    done = true;
                }
                else{
                    System.out.println("Not enough chips to bet that high!");
                }
            }catch(InputMismatchException e){
                System.out.println("ERROR input not recognized");
            }
        }
    }
    public static void check(){

    }
    public static void fold(){

    }
    public static void allin(Player player){
        pot += player.getChips();
        betPerPerson += player.getChips();
        player.setChips(0);
    }
}
