package day12.ball;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lexmint on 25.07.14.
 */
public class UI extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();

    public UI() {
        JFrame frame = new JFrame("Balls");
        frame.getContentPane().add(this);
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void addBall(Ball ball) {
        balls.add(ball);
        repaint();
    }

    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < balls.size(); i++) {
            g.setColor(balls.get(i).getColor());
            g.drawOval(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getWidth(), balls.get(i).getHeight());
            g.fillOval(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getWidth(), balls.get(i).getHeight());
        }
    }
}
