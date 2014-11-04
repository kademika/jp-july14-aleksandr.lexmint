package tanks.server.game.interfaces;

import tanks.server.game.tank.Direction;

public interface IMovable {
	public int getX();
	public int getY();
	public void updateX(int x);
	public void updateY(int y);
	public Direction getDirection();
}
