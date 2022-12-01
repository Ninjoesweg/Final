import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class Animation implements ActionListener {
    private static JFrame frame;
    private static JPanel buttonPanel;
    private String text;
    public Animation(String t) {
        text = t;
    }
    public Animation() {
        frame = new JFrame();
        buttonPanel =  new JPanel();
        text = "";
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "You pushed button " + text);
    }
    public void main() {
        // create and set up the window.
        frame = new JFrame("Poker Game");
        // make the program close when the window closes
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // display the window.
        frame.setLayout(new FlowLayout());
        frame.setSize(1000, 1000);


        buttonPanel = new JPanel();
        buttonPanel.setSize(10, 5);
        buttonPanel.setLocation(10,10);

        JButton raise = new JButton("Raise");
        raise.addActionListener(new Raise());
        buttonPanel.add(raise);

        JButton call = new JButton("Call");
        call.addActionListener(new Call());
        buttonPanel.add(call);

        JButton check = new JButton("Check");
        check.addActionListener(new Check());
        buttonPanel.add(check);

        JButton fold = new JButton("Fold");
        fold.addActionListener(new Fold());
        buttonPanel.add(fold);

        JButton allIn= new JButton("All-in");
        allIn.addActionListener(new AllIn());
        buttonPanel.add(allIn);

        JButton showHand= new JButton("Show Hand");
        allIn.addActionListener(new ShowHand(Game.getPlayers()));
        buttonPanel.add(allIn);

        JButton showFaceUp = new JButton("Show Face Up Cards");
        allIn.addActionListener(new ShowUpCards(Game.getFaceUp()));
        buttonPanel.add(allIn);

        frame.add(buttonPanel);
        JLabel IH1 = new JLabel();
        IH1.setText("Your Hand");
        frame.add(IH1);
        JLabel inHand1 = new JLabel();
        JLabel inHand2 = new JLabel();
        /**cardPanel.setBounds(new Rectangle(50, 50));
        cardPanel.setSize(50, 50);
        cardPanel.setLocation(50,50);*/

        for (Player p : Game.getPlayers()){
            if (p.isComputer() == false) {

                Image first = cardPicture(p.getCard1());
                ImageIcon firstCard = new ImageIcon(first);
                inHand1.setIcon(firstCard);


                Image second = cardPicture(p.getCard2());
                ImageIcon secondCard = new ImageIcon(second);
                inHand2.setIcon(secondCard);

                frame.add(inHand1);
                frame.add(inHand2);
            }
        }
        if(Game.getFaceUp().size() !=0){
            JLabel FU1 = new JLabel();
            JLabel FU2 = new JLabel();
            JLabel FU3 = new JLabel();
            JLabel FU4 = new JLabel();
            JLabel FU5 = new JLabel();


            JLabel FCI = new JLabel();
            FCI.setText("Face up cards");
            frame.add(FCI);
            JLabel faceCard = new JLabel();
            ArrayList<Card> faceUp = Game.getFaceUp();
            for(int i=0;i<faceUp.size();i++){
                if (i==0){
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU1.setIcon(cardImage);
                    frame.add(FU1);
                }else if(i==1){
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU2.setIcon(cardImage);
                    frame.add(FU2);
                }else if(i==2){
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU3.setIcon(cardImage);
                    frame.add(FU3);
                }else if(i==3){
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU4.setIcon(cardImage);
                    frame.add(FU4);
                }else if(i==4){
                    Image card = cardPicture(faceUp.get(i));
                    ImageIcon cardImage = new ImageIcon(card);
                    FU5.setIcon(cardImage);
                    frame.add(FU5);
                }
            }
        }
        frame.pack();
        frame.setVisible(true);
    }

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
    /**public static JFrame getFrame(){
        frame.setVisible(true);
        return frame;
    }*/

    private static class Raise implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private static class Call implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private static class Check implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private static class Fold implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private static class AllIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private static class ShowHand implements ActionListener {
        public ShowHand(ArrayList<Player> p) {

        }
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private static class ShowUpCards implements ActionListener {
        public ShowUpCards(ArrayList<Card> p) {


        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(null, "You pushed button " + text);
            //JOptionPane.showMessageDialog(null, "Your Hand", JOptionPane.PLAIN_MESSAGE, )
        }

    }
}