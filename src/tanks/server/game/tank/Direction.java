package tanks.server.game.tank;

public enum Direction {

	UP(1), DOWN(2), LEFT(3), RIGHT(4), NONE(-1);

	private int id;

	Direction(int id) {
		this.id = id;
	}
}
