package tanks.client.battlefield.objects;

import tanks.client.interfaces.IDrawable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Water extends Dirt implements IDrawable {

	public Water(int v, int h) {
		super(v, h);
		try {
			this.image = ImageIO.read(new File("resources/Water.png"));
		} catch (IOException ex) {
			
		}
	}
}
