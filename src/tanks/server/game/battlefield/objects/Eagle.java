package tanks.server.game.battlefield.objects;

import tanks.server.game.battlefield.BattleField;
import tanks.server.game.interfaces.IDestroyable;
import tanks.server.game.interfaces.IDrawable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Eagle extends Dirt implements IDrawable, IDestroyable {

	private BattleField bf;
	
	public Eagle(int v, int h, BattleField bf) {
		super(v, h);
		try {
			this.image = ImageIO.read(new File("resources/Eagle.png"));
		} catch (IOException ex) {
			
		}
		this.bf = bf;
	}
	
	public void destroy() {
		bf.updateQuadrant(x, y);
	}
}
