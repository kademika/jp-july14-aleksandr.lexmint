package tanks.server.game;

import tanks.server.game.battlefield.BattleField;
import tanks.server.game.battlefield.objects.Dirt;
import tanks.server.game.battlefield.objects.Rock;
import tanks.server.game.battlefield.objects.Water;
import tanks.server.game.interfaces.IDestroyable;
import tanks.server.game.tank.AbstractTank;
import tanks.server.game.tank.Bullet;
import tanks.server.game.tank.Direction;
import tanks.server.game.tank.Response;
import tanks.server.game.tank.types.T34;
import tanks.server.game.tank.types.Tiger;
import tanks.server.game.utils.CoordManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActionField {
    private BattleField battleField;

    private AbstractTank defender;
    private AbstractTank agressor;

    private LinkedList<Bullet> bullets = new LinkedList<>();

    public ActionField() {
        battleField = new BattleField();
        String loc = battleField.getAgressorLocation();
        agressor = new Tiger(battleField, Integer.parseInt(loc.split("_")[0]),
                Integer.parseInt(loc.split("_")[1]), Direction.RIGHT);
        defender = new T34(battleField);
    }


    public AbstractTank getDefender() {
        return defender;
    }

    public AbstractTank getAgressor() {
        return agressor;
    }

    public void action(Response r, AbstractTank t) {
        if (r == Response.FIRE) {
            processFire(t);
            return;
        } else if (r == Response.MOVE_DOWN) {
            processTurn(t, Direction.DOWN);
        } else if (r == Response.MOVE_UP) {
            processTurn(t, Direction.UP);
        } else if (r == Response.MOVE_LEFT) {
            processTurn(t, Direction.LEFT);
        } else if (r == Response.MOVE_RIGHT) {
            processTurn(t, Direction.RIGHT);
        } else if (r == Response.TURN_DOWN) {
            processTurn(t, Direction.DOWN);
            return;
        } else if (r == Response.TURN_UP) {
            processTurn(t, Direction.UP);
            return;
        } else if (r == Response.TURN_LEFT) {
            processTurn(t, Direction.LEFT);
            return;
        } else if (r == Response.TURN_RIGHT) {
            processTurn(t, Direction.RIGHT);
            return;
        } else {
            return;
        }
        processMove(t);

    }

    // FOR REPLAY
    public void action(String response, AbstractTank t) {
        if (response.equals("FIRE")) {
            processFire(t);
            return;
        } else if (response.equals("MOVE_RIGHT")) {
            processTurn(t, Direction.RIGHT);
        } else if (response.equals("MOVE_DOWN")) {
            processTurn(t, Direction.DOWN);
        } else if (response.equals("MOVE_UP")) {
            processTurn(t, Direction.UP);
        } else if (response.equals(("MOVE_LEFT"))) {
            processTurn(t, Direction.LEFT);
        } else if (response.equals("TURN_RIGHT")) {
            processTurn(t, Direction.RIGHT);
            return;
        } else if (response.equals("TURN_LEFT")) {
            processTurn(t, Direction.LEFT);
            return;
        } else if (response.equals("TURN_DOWN")) {
            processTurn(t, Direction.DOWN);
            return;
        } else if (response.equals("TURN_UP")) {
            processTurn(t, Direction.UP);
            return;
        } else {
            return;
        }
        processMove(t);
    }

    private boolean processCheckMove(AbstractTank tank) {
        int destX = tank.getX();
        int destY = tank.getY();
        if (tank.getDirection() == Direction.UP) {
            destY -= 64;
        } else if (tank.getDirection() == Direction.DOWN) {
            destY += 64;
        } else if (tank.getDirection() == Direction.LEFT) {
            destX -= 64;
        } else if (tank.getDirection() == Direction.RIGHT) {
            destX += 64;
        }
        if ((tank.getDirection() == Direction.UP && destY < 0)
                || (tank.getDirection() == Direction.DOWN && destY > 512)
                || (tank.getDirection() == Direction.LEFT && destX < 0)
                || (tank.getDirection() == Direction.RIGHT && destX > 512)) {
            return false;
        }
        int destQX = CoordManager.getQuadrantX(destX);
        int destQY = CoordManager.getQuadrantY(destY);


        if (battleField.scanQuadrant(destQX, destQY) instanceof IDestroyable) {
            // DESTROYING DESTROYABLE OBJECTS BY TANK
//			battleField.updateQuadrant(destQX, destQY);
//            return true;
            return false;

        } else if ((destQX == CoordManager.getQuadrantX(agressor.getX()) && destQY == CoordManager
                .getQuadrantY(agressor.getY()))
                || (destQX == CoordManager.getQuadrantX(defender.getX()) && destQY == CoordManager
                .getQuadrantY(defender.getY()))) {
            return false;
        } else if (battleField.scanQuadrant(destQX, destQY) instanceof Water) {
            return true;
        } else if (battleField.scanQuadrant(destQX, destQY) instanceof Rock) {
            return false;
        } else {
            return true;
        }
    }

    private boolean processInterception(Bullet bullet) throws Exception {
        String coord = CoordManager.getQuadrant(bullet.getX(), bullet.getY());
        int x = CoordManager.getQuadrantX(bullet.getX());
        int y = CoordManager.getQuadrantY(bullet.getY());

        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            if (CoordManager.getQuadrant(defender.getX(), defender.getY()).equals(
                    coord)) {
                defender.destroy();
                return true;

            } else if (CoordManager.getQuadrant(agressor.getX(), agressor.getY())
                    .equals(coord)) {
                agressor.destroy();
                Thread.sleep(300);
                String loc = battleField.getAgressorLocation();
                agressor = new Tiger(battleField,
                        Integer.parseInt(loc.split("_")[0]),
                        Integer.parseInt(loc.split("_")[1]), Direction.UP);
                return true;
            } else if (!(battleField.scanQuadrant(x, y) instanceof IDestroyable)
                    && !(battleField.scanQuadrant(x, y) instanceof Dirt)) {
                return true;
            } else if (battleField.scanQuadrant(x, y) instanceof IDestroyable) {
                battleField.updateQuadrant(x, y);
                return true;
            }
        }
        return false;
    }

    public void processTurn(AbstractTank tank, Direction dir) {
        if (dir == Direction.DOWN) {
            tank.setDirection(Direction.DOWN);
        } else if (dir == Direction.UP) {
            tank.setDirection(Direction.UP);
        } else if (dir == Direction.LEFT) {
            tank.setDirection(Direction.LEFT);
        } else if (dir == Direction.RIGHT) {
            tank.setDirection(Direction.RIGHT);
        }
    }

    public void processMove(AbstractTank tank) {
        Direction direction = tank.getDirection();
        int step = 1;
        int covered = 0;

        if (processCheckMove(tank) == false) {
            return;
        }

        while (covered < 64) {
            if (direction == Direction.UP) {
                tank.updateY(-step);
            } else if (direction == Direction.DOWN) {
                tank.updateY(step);
            } else if (direction == Direction.LEFT) {
                tank.updateX(-step);
            } else {
                tank.updateX(step);
            }
            covered += step;
            try {
                Thread.sleep(tank.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private HashMap<AbstractTank, Long> fireCooldowns = new HashMap<>();

    public void processFire(final AbstractTank tank) {
        if (fireCooldowns.get(tank) != null && ((System.currentTimeMillis() - fireCooldowns.get(tank))/1000) < tank.getFireCooldown()) {
            return;
        }
        fireCooldowns.put(tank, System.currentTimeMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bullet bullet;
                if (tank.getDirection() == Direction.DOWN) {
                    bullet = new Bullet(CoordManager.getRoadCoordX(tank.getX(),
                            tank.getDirection()) + 25, CoordManager.getRoadCoordY(tank.getY(),
                            tank.getDirection()) + 15, tank.getDirection());
                } else if (tank.getDirection() == Direction.UP) {
                    bullet = new Bullet(CoordManager.getRoadCoordX(tank.getX(),
                            tank.getDirection()) + 25, CoordManager.getRoadCoordY(tank.getY(),
                            tank.getDirection()) + 49, tank.getDirection());
                } else if (tank.getDirection() == Direction.LEFT) {
                    bullet = new Bullet(CoordManager.getRoadCoordX(tank.getX(),
                            tank.getDirection()) + 49, CoordManager.getRoadCoordY(tank.getY(),
                            tank.getDirection()) + 25, tank.getDirection());
                } else {
                    bullet = new Bullet(CoordManager.getRoadCoordX(tank.getX(),
                            tank.getDirection()) + 15, CoordManager.getRoadCoordY(tank.getY(),
                            tank.getDirection()) + 25, tank.getDirection());
                }
                bullets.add(bullet);
                int step = 1;
                while ((bullet.getX() > -14 && bullet.getX() < 590)
                        && (bullet.getY() > -14 && bullet.getY() < 590)) {

                    if (bullet.getDirection() == Direction.UP) {
                        bullet.updateY(-step);
                    } else if (bullet.getDirection() == Direction.DOWN) {
                        bullet.updateY(step);
                    } else if (bullet.getDirection() == Direction.LEFT) {
                        bullet.updateX(-step);
                    } else {
                        bullet.updateX(step);
                    }

                    try {
                        if (processInterception(bullet)) {
                            bullets.remove(bullet);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(bullet.getSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                bullets.remove(bullet);
            }
        }).start();

    }
}