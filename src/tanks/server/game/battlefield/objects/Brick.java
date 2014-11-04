package tanks.server.game.battlefield.objects;

import tanks.server.game.battlefield.BattleField;
import tanks.server.game.interfaces.IDestroyable;
import tanks.server.game.interfaces.IDrawable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Brick extends Dirt implements IDestroyable, IDrawable {
	
	private BattleField bf;
	
	public Brick(int v, int h, BattleField bf) {
		super(v, h);
		try {
			this.image = ImageIO.read(new File("resources/Brick.png"));
		} catch (IOException ex) {
			
		}
		this.bf = bf;
	}

	public void destroy() {
		bf.updateQuadrant(x, y);
	}
}
