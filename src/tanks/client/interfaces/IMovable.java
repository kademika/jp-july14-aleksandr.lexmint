package tanks.client.interfaces;

import tanks.client.tank.Direction;

public interface IMovable {
	public int getX();
	public int getY();
	public void updateX(int x);
	public void updateY(int y);
	public Direction getDirection();
}
