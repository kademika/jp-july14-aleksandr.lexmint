package tanks.client.battlefield.objects;

import tanks.client.interfaces.IDrawable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rock extends Dirt implements IDrawable {

	public Rock(int v, int h) {
		super(v, h);
		try {
			this.image = ImageIO.read(new File("resources/Rock.png"));
		} catch (IOException ex) {
			
		}
	}
}
