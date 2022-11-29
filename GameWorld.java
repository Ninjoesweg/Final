import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

class GameWorld extends JComponent {
    // store the game object(s)
    private ArrayList<Card> card;
    private GUI gui;

    public void paintComponent(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(0, 0, 500, 500);
    }

}

