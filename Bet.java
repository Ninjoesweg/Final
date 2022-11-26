import java.util.InputMismatchException;
import java.util.Scanner;
public class Bet {
    private static int pot = 0;
    public void decisionMenu(){
        System.out.println("Would you like to: \n1: Call \n2:Raise \n3: Check \n 4: Fold \n5: All in");
        Scanner in = new Scanner(System.in);
        int answer = in.nextInt();
        //try catch block, in case user input does not match numbers 1-5
        try{
            if(answer == 1) {
                call();
            } else if (answer == 2) {
                raise();
            } else if (answer == 3) {
                check();
            }else if(answer == 4){
                fold();
            }else if(answer == 5) {
                allin();
            }else if(answer<=0 | answer>5){
                System.out.println("That is not a valid number.");
                decisionMenu();
            }
        } catch(InputMismatchException e){
            System.out.println("That is not a valid response. Please enter the number corresponding to your choice");
            decisionMenu();
        }
    }
    public void call(){

    }
    public void raise(){

    }
    public void check(){

    }
    public void fold(){

    }
    public void allin(){

    }
}
