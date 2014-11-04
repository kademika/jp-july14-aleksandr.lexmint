package tanks.client.battlefield.objects;

import tanks.client.interfaces.IDrawable;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
