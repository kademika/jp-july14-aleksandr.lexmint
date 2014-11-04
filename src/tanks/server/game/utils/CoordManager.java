package tanks.server.game.utils;

import tanks.server.game.tank.Direction;

public final class CoordManager {
	public static String getQuadrant(int x, int y) {
		return x / 64 + "_" + y / 64;
	}

	public static int getQuadrantX(int x) {
		return x / 64;
	}

	public static int getQuadrantY(int y) {
		return y / 64;
	}

	public static int getQuadrantCoordX(int h) {
		return h * 64;
	}

	public static int getQuadrantCoordY(int v) {
		return v * 64;
	}

	public static int getRoadY(int y, Direction direction) {
		int vert = getQuadrantY(y);

		if (direction == Direction.DOWN) {
			vert++;
		} else if (direction == Direction.UP) {
			vert--;
		}
		return vert;
	}
	
	public static int getRoadX(int x, Direction direction) {
		int hor = getQuadrantX(x);
		
		if (direction == Direction.LEFT){
			hor--;
		} else if (direction == Direction.RIGHT){
			hor++;
		}
		return hor;
	}
	
	public static int getRoadCoordX(int x, Direction direction) {
		return getQuadrantCoordX(getRoadX(x, direction));
	}
	
	public static int getRoadCoordY(int y, Direction direction) {
		return getQuadrantCoordY(getRoadY(y, direction));
	}

    public static int getCoordX(String coord) {
        return Integer.valueOf(coord.split("_")[0]);
    }

    public static int getCoordY(String coord) {
        return Integer.valueOf(coord.split("_")[1]);
    }
	
}
