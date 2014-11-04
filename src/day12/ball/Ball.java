package day12.ball;

import java.awt.*;

/**
 * Created by lexmint on 25.07.14.
 */
public class Ball {
    private Color color;
    private int height;
    private int width;
    private int x;
    private int y;
    int speed;

    public Ball(Color color, int startX, int startY, int width, int height, int speed, UI ui) {
        this.color = color;
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;
        ui.addBall(this);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public void move(int moveX, int moveY) {
        x += moveX;
        y += moveY;
    }




}
