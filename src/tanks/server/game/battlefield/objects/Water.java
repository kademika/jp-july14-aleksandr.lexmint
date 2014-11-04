package tanks.server.game.battlefield.objects;

import tanks.server.game.interfaces.IDrawable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Water extends Dirt implements IDrawable {

	public Water(int v, int h) {
		super(v, h);
		try {
			this.image = ImageIO.read(new File("resources/Water.png"));
		} catch (IOException ex) {
			
		}
	}
}
