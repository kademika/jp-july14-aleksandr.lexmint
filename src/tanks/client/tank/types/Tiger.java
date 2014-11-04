package tanks.client.tank.types;

import tanks.client.battlefield.BattleField;
import tanks.client.battlefield.objects.Dirt;
import tanks.client.battlefield.objects.Water;
import tanks.client.interfaces.IDestroyable;
import tanks.client.tank.AbstractTank;
import tanks.client.tank.Direction;
import tanks.client.tank.Response;
import tanks.client.utils.CoordManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Tiger extends AbstractTank {

    private int armor = 1;

    public Tiger(BattleField bf) {
        super(bf);
    }

    public Tiger(BattleField bf, int x, int y,
                 Direction direction) {
        super(bf, x, y, direction);
    }

    @Override
    public void destroy() {
        if (armor == 0) {
            super.destroy();
        } else {
            armor--;
            return;
        }
    }

    private LinkedList<Response> path;

    @Override
    public Response response(int enemyX, int enemyY) {
        if (path == null || path.size() < 1) {
            path = getPathToEagle();
        }

        Response turnToEnemy = scanTarget(enemyX, enemyY);
        if (turnToEnemy != null) {
            if (getDirection() == Direction.DOWN) {
                if (turnToEnemy == Response.TURN_DOWN) {
                    return Response.FIRE;
                }
            } else if (getDirection() == Direction.RIGHT) {
                if (turnToEnemy == Response.TURN_RIGHT) {
                    return Response.FIRE;
                }
                return turnToEnemy;
            } else if (getDirection() == Direction.LEFT) {
                if (turnToEnemy == Response.TURN_LEFT) {
                    return Response.FIRE;
                }
            } else if (getDirection() == Direction.UP) {
                if (turnToEnemy == Response.TURN_UP) {
                    return Response.FIRE;
                }
            }
        }

        Response response = path.getFirst();
        path.removeFirst();


        return response;
    }

    private LinkedList<Response> getPathToEagle() {
        LinkedList<Dirt> startPath = new LinkedList<>();
        startPath.add(bf.scanQuadrant(CoordManager.getQuadrantX(this.x), CoordManager.getQuadrantY(this.y)));

        String eagleLocation = bf.getEagleLocation();
        if (eagleLocation == null) {
            System.out.println("Игра выиграна!");
            return null;
        }

        int destX = Integer.valueOf(eagleLocation.split("_")[0]);
        int destY = Integer.valueOf(eagleLocation.split("_")[1]);

        paths.clear();
        scanPath(startPath, destX, destY);
        pathsCount = 0;

        LinkedList<Dirt> selection = null;
        for (LinkedList<Dirt> path : paths) {
            if (selection == null || selection.size() > path.size()) {
                selection = path;
            }
        }
        return translateToActions(selection);
    }

    LinkedList<LinkedList<Dirt>> paths = new LinkedList<>();

    private int pathsCount = 0;

    private void scanPath(LinkedList<Dirt> path, int destX, int destY) {
        if (pathsCount > 1000) {
            return;
        }

        Dirt[] options = {
                bf.scanQuadrant(path.getLast().getX(), path.getLast().getY() - 1),
                bf.scanQuadrant(path.getLast().getX(), path.getLast().getY() + 1),
                bf.scanQuadrant(path.getLast().getX() - 1, path.getLast().getY()),
                bf.scanQuadrant(path.getLast().getX() + 1, path.getLast().getY())
        };

        for (Dirt option : options) {
            if (option != null && (option instanceof IDestroyable || option instanceof Water || option.getClass() == Dirt.class) &&
                    !path.contains(option)) {
                LinkedList<Dirt> newPath = new LinkedList<>(path);
                newPath.addLast(option);

                if (option.getY() == destY && option.getX() == destX) {
                    paths.add(newPath);
                    pathsCount++;
                    continue;
                }
                scanPath(newPath, destX, destY);
            }
        }
    }

    private LinkedList<Response> translateToActions(List<Dirt> path) {
        LinkedList<Response> actionList = new LinkedList<>();
        Iterator<Dirt> it = path.iterator();
        while (it.hasNext()) {
            Dirt nextBlock = it.next();
            if (nextBlock.getX() > CoordManager.getQuadrantX(x)) {

                actionList.add(Response.MOVE_RIGHT);
//                if (nextBlock instanceof IDestroyable) {
//                    actionList.add(Response.TURN_RIGHT);
//                    actionList.add(Response.FIRE);
//                }
            } else if (nextBlock.getX() < CoordManager.getQuadrantX(x)) {

                actionList.add(Response.MOVE_LEFT);
//                if (nextBlock instanceof IDestroyable) {
//                    actionList.add(Response.TURN_LEFT);
//                    actionList.add(Response.FIRE);
//                }
            } else if (nextBlock.getY() < CoordManager.getQuadrantY(y)) {

                actionList.add(Response.MOVE_UP);
//                if (nextBlock instanceof IDestroyable) {
//                    actionList.add(Response.TURN_UP);
//                    actionList.add(Response.FIRE);
//                }
            } else if (nextBlock.getY() > CoordManager.getQuadrantY(y)) {

                actionList.add(Response.MOVE_DOWN);
//                if (nextBlock instanceof IDestroyable) {
//                    actionList.add(Response.TURN_DOWN);
//                    actionList.add(Response.FIRE);
//                }
            }
            it.remove();
        }
        return actionList;
    }

    private Response scanTarget(int enemyX, int enemyY) {
        int currX = CoordManager.getQuadrantX(x);
        int currY = CoordManager.getQuadrantY(y);

        int eagleX = Integer.parseInt(bf.getEagleLocation().split("_")[0]);
        int eagleY = Integer.parseInt(bf.getEagleLocation().split("_")[1]);

        enemyX = CoordManager.getQuadrantX(enemyX);
        enemyY = CoordManager.getQuadrantY(enemyY);

        // enemyX >= 0 - check if enemy wasn't destroyed
        if (enemyX == currX || currX == eagleX) {
            if ((enemyY >= 0 && currY > enemyY) || currY > eagleY) {
                return Response.TURN_UP;
            } else if ((enemyY >= 0 && currY < enemyY) || currY < eagleY) {
                return Response.TURN_DOWN;
            }

            // enemyX >= 0 - check if enemy wasn't destroyed
        } else if (enemyY == currY || currY == eagleY) {
            if ((enemyX >= 0 && currX > enemyX) || currX > eagleX) {
                return Response.TURN_LEFT;
            } else if ((enemyX >= 0 && currX < enemyX) || currX < eagleX) {
                return Response.TURN_RIGHT;
            }
        }

        return null;
    }
}
