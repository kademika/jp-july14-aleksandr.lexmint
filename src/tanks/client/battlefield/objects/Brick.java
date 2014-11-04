package tanks.client.battlefield.objects;

import tanks.client.battlefield.BattleField;
import tanks.client.interfaces.IDestroyable;
import tanks.client.interfaces.IDrawable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
