package tanks.server.game.battlefield.objects;

import tanks.server.game.interfaces.IDrawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dirt implements IDrawable {

    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int x;
    protected BufferedImage image;

    public Dirt(int y, int x) {
        this.y = y;
        this.x = x;
        try {
            image = ImageIO.read(new File("resources/Dirt.png"));
        } catch (IOException ex) {

        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x * 64, y * 64, null);
    }

}
