package tanks.server.game.tank;

import tanks.server.game.interfaces.IDestroyable;
import tanks.server.game.interfaces.IDrawable;
import tanks.server.game.interfaces.IMovable;

import java.awt.*;

public class Bullet implements IDestroyable, IDrawable, IMovable {
	private int speed = 7;

	private Direction direction;
	private int x;
	private int y;

	public Bullet(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 0));
		if (this.getX() >= 0 && this.getY() >= 0 && this.getX() <= 576 && this.getY() <= 576) {
			g.fillRect(this.getX(), this.getY(), 14, 14);
		}
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public void destroy() {
		x = -100;
		y = -100;
	}

	public int getSpeed() {
		return speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Direction getDirection() {
		return direction;
	}
}
