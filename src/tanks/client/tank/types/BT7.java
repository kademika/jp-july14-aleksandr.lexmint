package tanks.client.tank.types;

import tanks.client.utils.CoordManager;
import tanks.client.battlefield.BattleField;
import tanks.client.tank.AbstractTank;
import tanks.client.tank.Direction;
import tanks.client.tank.Response;

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
