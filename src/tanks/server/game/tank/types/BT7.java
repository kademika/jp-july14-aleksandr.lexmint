package tanks.server.game.tank.types;

import tanks.server.game.battlefield.BattleField;
import tanks.server.game.tank.AbstractTank;
import tanks.server.game.tank.Direction;
import tanks.server.game.tank.Response;
import tanks.server.game.utils.CoordManager;

public class BT7 extends AbstractTank {

	private int speed = super.speed * 2;
	int i = 0;

	public BT7(BattleField bf) {
		super(bf);
	}

	public BT7(BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
	}

	@Override
	public Response response(int enemyX, int enemyY) {
        return actions.getFirst();
    }
	
	@Override
	public String toString() {
		return "BT7_" + CoordManager.getQuadrantX(this.getX()) + "_" + CoordManager.getQuadrantY(this.getY());
	}
}
