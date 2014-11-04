package tanks.server.game.battlefield.objects;

import tanks.server.game.interfaces.IDrawable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Rock extends Dirt implements IDrawable {

	public Rock(int v, int h) {
		super(v, h);
		try {
			this.image = ImageIO.read(new File("resources/Rock.png"));
		} catch (IOException ex) {
			
		}
	}
}
