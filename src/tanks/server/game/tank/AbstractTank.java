package tanks.server.game.tank;

import tanks.server.game.battlefield.BattleField;
import tanks.server.game.interfaces.IDestroyable;
import tanks.server.game.interfaces.IDrawable;
import tanks.server.game.interfaces.IMovable;
import tanks.server.game.utils.CoordManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public abstract class AbstractTank implements IDestroyable, IDrawable, IMovable {
	protected int speed = 14;

    protected int fireCooldown = 4;

    // 1 - top, 2 - bottom, 3 - left, 4 - right
	private Direction direction = Direction.UP;

	protected int x = 128;
	protected int y = 512;


	protected BattleField bf;

	protected LinkedList<Response> actions;

	protected BufferedImage image_up;
	protected BufferedImage image_down;
	protected BufferedImage image_left;
	protected BufferedImage image_right;

	public AbstractTank(BattleField bf) {
		this(bf, 128, 512, Direction.UP);
	}

	public AbstractTank(BattleField bf, int x, int y, Direction direction) {
		this.bf = bf;
		this.x = x;
		this.y = y;
		this.direction = direction;
		actions = new LinkedList<>();
		try {
			image_up = ImageIO.read(new File("resources/Tank_Up.png"));
			image_down = ImageIO.read(new File("resources/Tank_Down.png"));
			image_left = ImageIO.read(new File("resources/Tank_Left.png"));
			image_right = ImageIO.read(new File("resources/Tank_Right.png"));
		} catch (IOException ex) {
			System.err.println("Image wasn't found!");
		}
	}

	public abstract Response response(int enemyX, int enemyY);

	public void moveToQuadrant(int destX, int destY) {
		int currX = CoordManager.getQuadrantX(this.getX());
		int currY = CoordManager.getQuadrantY(this.getY());

		if (currX < destX) {
			for (int i = 0; i < destX - currX; i++) {
				actions.add(Response.MOVE_RIGHT);
			}
		} else {
			for (int i = 0; i < currX - destX; i++) {
				actions.add(Response.MOVE_LEFT);
			}
		}

		if (currY < destY) {
			for (int i = 0; i < destY - currY; i++) {
				actions.add(Response.MOVE_DOWN);
			}
		} else {
			for (int i = 0; i < currY - destY; i++) {

				actions.add(Response.MOVE_UP);
			}
		}

	}

	public void setDirection(Direction direction)  {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
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

	public void destroy() {
		x = -100;
		y = -100;
	}

	/**
	 * Returns true if {@link tanks.server.game.tank.AbstractTank} destination quadrant is destroyable
	 * and available for movement
	 */
	public boolean checkRoad() {
		if (bf.scanQuadrant(CoordManager.getRoadY(this.getX(), this.getDirection()),
				CoordManager.getRoadX(this.getY(), this.getDirection())) instanceof IDestroyable) {
			return true;
		} else {
			return false;
		}
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public void draw(Graphics g) {
		if (this.getDirection() == Direction.UP) {
			g.drawImage(image_up, this.getX(), this.getY(), null);
		} else if (this.getDirection() == Direction.DOWN) {
			g.drawImage(image_down, this.getX(), this.getY(), null);
		} else if (this.getDirection() == Direction.LEFT) {
			g.drawImage(image_left, this.getX(), this.getY(), null);
		} else {
			g.drawImage(image_right, this.getX(), this.getY(), null);
		}
	}

    public int getFireCooldown() {
        return fireCooldown;
    }
}
