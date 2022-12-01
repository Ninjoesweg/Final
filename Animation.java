import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * In this class, GUI is implemented to interact with the other classes.
 */
public class Animation implements ActionListener {
    //instance variables
    private static JFrame frame;
    private static JPanel buttonPanel;
    private String text;

    public Animation(String t) {
        text = t;
    }

    /**
     * Animation constructor.
     */
    public Animation() {
        frame = new JFrame();
        buttonPanel = new JPanel();
        text = "";
    }

    /**
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "You pushed button " + text);
    }

    /**
     * This method creates the JFrame, multiple JButtons, and multiple JPanels for the GUI.
     * Buttons allow you to raise, call, check, fold, or go all-in.
     * Your hand and any face up cards will be displayed as images.
     */
    public void main() throws IllegalMonitorStateException {
        boolean end = false;
        // create and set up the window.
        frame = new JFrame("Poker Game");
        // make the program close when the window closes
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // display the window.
        frame.setLayout(new FlowLayout());
        frame.setSize(1000, 1000);



        frame.add(buttonPanel);

        //create a JLabel to show card Images in your hand
        JLabel IH1 = new JLabel();
        IH1.setText("Your Hand");
        frame.add(IH1);
        JLabel inHand1 = new JLabel();
        JLabel inHand2 = new JLabel();
        for (Player p : Game.getPlayers()) {
            if(p.isFold()){
                end = true;
            }
            if (!p.isComputer()) {
                JLabel cash = new JLabel();
                cash.setText("Name: " + p.getName() + " Cash: " + p.getChips() + " Pot " + Bet.getPot());
                frame.add(cash, 0);
                //show users first card
                Image first = cardPicture(p.getCard1());
                ImageIcon firstCard = new ImageIcon(first);
                inHand1.setIcon(firstCard);
                //show user's 2nd card
                Image second = cardPicture(p.getCard2());
                ImageIcon secondCard = new ImageIcon(second);
                inHand2.setIcon(secondCard);

                frame.add(inHand1);
                frame.add(inHand2);

                //Create all buttons
                boolean call = false;
                boolean raise = false;
                boolean check = false;
                if(Bet.getBetPerPerson() > p.getChipsInPot() && p.getChips() > (Bet.getBetPerPerson() - p.getChipsInPot())){
                    call = true;
                }
                if(p.getChips() > (Bet.getBetPerPerson() - p.getChipsInPot())){
                    raise = true;
                }
                if(Bet.getBetPerPerson() == p.getChipsInPot()){
                    check = true;
                }
                buttonPanel = new JPanel();
                buttonPanel.setSize(10, 5);
                buttonPanel.setLocation(10, 10);
                if(raise) {
                    JButton raiseBut = new JButton("Raise");
                    raiseBut.addActionListener(new Raise());
                    buttonPanel.add(raiseBut);
                }
                if(call) {
                    JButton callBut = new JButton("Call");
                    callBut.addActionListener(new Call());
                    buttonPanel.add(callBut);
                }
                if(check) {
                    JButton checkBut = new JButton("Check");
                    checkBut.addActionListener(new Check());
                    buttonPanel.add(checkBut);
                }
                JButton fold = new JButton("Fold");
                fold.addActionListener(new Fold());
                buttonPanel.add(fold);

                JButton allIn = new JButton("All-in");
                allIn.addActionListener(new AllIn());
                buttonPanel.add(allIn);
            }
        }
        //If there are any face up cards display them similar to how we did for cards in your hand
        if (Game.getFaceUp().size() != 0) {
            JLabel FU1 = new JLabel();
            JLabel FU2 = new JLabel();
            JLabel FU3 = new JLabel();
            JLabel FU4 = new JLabel();
            JLabel FU5 = new JLabel();
            JLabel FCI = new JLabel();
            FCI.setText("Face up cards");
            frame.add(FCI);
            //JLabel faceCard = new JLabel();
            ArrayList<Card> faceUp = Game.getFaceUp();
            for (int i = 0; i < faceUp.size(); i++) {
                if (i == 0) {
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU1.setIcon(cardImage);
                    frame.add(FU1);
                } else if (i == 1) {
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU2.setIcon(cardImage);
                    frame.add(FU2);
                } else if (i == 2) {
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU3.setIcon(cardImage);
                    frame.add(FU3);
                } else if (i == 3) {
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU4.setIcon(cardImage);
                    frame.add(FU4);
                } else if (i == 4) {
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU5.setIcon(cardImage);
                    frame.add(FU5);
                }
            }
            frame.add(buttonPanel);
        }
        //fit the buttons and images to frame size
        frame.pack();
        //set to visible
        if(end){
            while(Game.getRound() < 4){
                Game.setRound(Game.getRound() + 1);
                Game.startRound(Game.getRound());
            }
            Game.reset();
        }
        else {
            frame.setVisible(true);
        }
        try {
            Thread.currentThread().wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method reads in Images from the Images directory and resizes them.
     * This is called in the Animation main() method, and uses toImageFileName() method from the Card Class to get the pathnames
     *
     * @param c The Card object that we want the image of.
     * @return a corrected Image
     */
    private Image cardPicture(Card c) {
        try {
            Image cardImage = ImageIO.read(new File(c.toImageFileName()));
            Image corrected = cardImage.getScaledInstance(70, 100, Image.SCALE_DEFAULT);
            return corrected;
        } catch (Exception e) {
            System.out.println("unable to get Image.");
            return null;
        }
    }

    /**
     * This class is called if user pressed the raise button
     * performs the raise() method from Bet class. Then closes the GUI.
     */
    private static class Raise implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Player p:Game.getPlayers()) {
                if(!p.isComputer()){
                    Bet.raise(p);
                }
            }
            frame.setVisible(false);
            Game.setRound(Game.getRound() + 1);
            Game.startRound(Game.getRound());
        }
    }

    /**
     * This class is called if user pressed the call button
     * performs the call() method from Bet class. Then closes the GUI.
     */
    private static class Call implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Player p:Game.getPlayers()) {
                if(!p.isComputer()){
                    Bet.call(p);
                }
            }
            frame.setVisible(false);
            Game.setRound(Game.getRound() + 1);
            Game.startRound(Game.getRound());
        }
    }

    /**
     * This class is called if user pressed the check button
     * performs the check() method from Bet class. Then closes the GUI.
     */
    private static class Check implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Player p:Game.getPlayers()) {
                if(!p.isComputer()){
                    Bet.check(p);
                }
            }
            frame.setVisible(false);
            Game.setRound(Game.getRound() + 1);
            Game.startRound(Game.getRound());
        }
    }


    /**
     * This class is called if user pressed the fold button
     * performs the fold() method from Bet class. Then closes the GUI.
     */
    private static class Fold implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Player p:Game.getPlayers()) {
                if(!p.isComputer()){
                    Bet.fold(p);
                }
            }
            frame.setVisible(false);
            Game.setRound(Game.getRound() + 1);
            Game.startRound(Game.getRound());
        }
    }

    /**
     * This class is called if user pressed the all-in button
     * performs the allin() method from Bet class. Then closes the GUI.
     */
    private static class AllIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Player p:Game.getPlayers()) {
                if(!p.isComputer()){
                    Bet.allin(p);
                }
            }
            frame.setVisible(false);
            Game.setRound(Game.getRound() + 1);
            Game.startRound(Game.getRound());
        }
    }
}
