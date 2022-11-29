import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

public class Animation implements ActionListener{
    private static JFrame frame;
    private static JPanel cardPanel;
    private static JPanel buttonPanel;
    private String text;
    public Animation(String t) {
        text = t;
    }
    public Animation() {
        frame = new JFrame();
        cardPanel = new JPanel();
        buttonPanel =  new JPanel();
        text = "";
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "You pushed button " + text);
    }
    public static void main(String args[]) {
        // create and set up the window.
        frame = new JFrame("Poker Game");

        // make the program close when the window closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // display the window.
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 500);
        GameWorld g = new GameWorld();
        frame.add(g);
        frame.setVisible(true);

        cardPanel = new JPanel();
        buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton button = new JButton("Raise");
        button.addActionListener(new Raise());
        c.gridx = 0;
        c.gridy = 0;
        buttonPanel.add(button, c);

        button = new JButton("Call");
        button.addActionListener(new Call());
        c.gridx = 1;
        buttonPanel.add(button, c);

        button = new JButton("Check");
        button.addActionListener(new Check());
        c.gridx = 2;
        buttonPanel.add(button, c);

        button = new JButton("Fold");
        button.addActionListener(new Fold());
        c.gridx = 3;
        buttonPanel.add(button, c);

        button = new JButton("All-in");
        button.addActionListener(new AllIN());
        c.gridx = 4;
        buttonPanel.add(button, c);

        buttonPanel.setBounds(new Rectangle(500, 200));
        frame.add(buttonPanel);

        frame.repaint();
        frame.validate();
    }

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

    private static class AllIN implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}