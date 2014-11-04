package tanks.server.game.tank.types;

import tanks.server.game.battlefield.BattleField;
import tanks.server.game.tank.AbstractTank;
import tanks.server.game.tank.Direction;
import tanks.server.game.tank.Response;
import tanks.server.game.utils.CoordManager;

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
