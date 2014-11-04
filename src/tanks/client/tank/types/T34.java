package tanks.client.tank.types;

import tanks.client.utils.CoordManager;
import tanks.client.battlefield.BattleField;
import tanks.client.tank.AbstractTank;
import tanks.client.tank.Direction;
import tanks.client.tank.Response;

public class T34 extends AbstractTank {

	public T34(BattleField bf) {
		super(bf);
	}

	public T34(BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
	}

	@Override
	public Response response(int enemyX, int enemyY) {
		return actions.getFirst();
	}

	@Override
	public String toString() {
		return "T34_" + CoordManager.getQuadrantX(this.getX()) + "_"
				+ CoordManager.getQuadrantY(this.getY());
	}
}
