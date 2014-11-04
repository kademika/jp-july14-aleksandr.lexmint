package day12.stargate;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lexmint on 26.07.14.
 */
public class UI extends JPanel {


    public UI(Ship ship, Gate gate) {

        JFrame frame = new JFrame("Stargate");
        frame.getContentPane().add(this);
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Launcher.ship.draw(g);
        Launcher.gate.draw(g);

    }
}
