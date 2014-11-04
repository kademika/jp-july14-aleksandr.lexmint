package tanks.client;

import tanks.client.battlefield.BattleField;
import tanks.client.battlefield.objects.Dirt;
import tanks.client.battlefield.objects.Rock;
import tanks.client.battlefield.objects.Water;
import tanks.client.interfaces.IDestroyable;
import tanks.client.tank.AbstractTank;
import tanks.client.tank.Bullet;
import tanks.client.tank.Direction;
import tanks.client.tank.Response;
import tanks.client.tank.types.T34;
import tanks.client.tank.types.Tiger;
import tanks.client.utils.CoordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActionField extends JPanel {
    private BattleField battleField;

    private AbstractTank defender;
    private AbstractTank agressor;

    private volatile boolean isStopped = false;

    private Thread graphicsThread;
    private Thread agressorThread;

    private boolean MULTIPLAYER = false;

    private LinkedList<Bullet> bullets = new LinkedList<>();

    private Client client;

    private UI ui;

    public ActionField(UI ui, Client client) {
        battleField = new BattleField();
        String loc = battleField.getAgressorLocation();
        agressor = new Tiger(battleField, Integer.parseInt(loc.split("_")[0]),
                Integer.parseInt(loc.split("_")[1]), Direction.RIGHT);
        defender = new T34(battleField);

        this.ui = ui;
        if (client != null) {
            MULTIPLAYER = true;
        }
        this.client = client;


        ui.getFrame().getContentPane().add(this);
        ui.getFrame().pack();
    }

    void runTheGame() throws Exception {

        graphicsThread = graphicsThread();
        if (!MULTIPLAYER) {
            defendersSPThread();
            agressorThread = agressorsThread();
        } else {
            defendersMPThread();
        }
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

    public void action(Response r, int tankId) {
        //TODO
        if (tankId == 1) {
            action(r, agressor);
        } else if (tankId == 2) {
            action(r, defender);
        }
    }

    // FOR REPLAY
    public void action(String response, AbstractTank t) throws Exception {
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
        if (fireCooldowns.get(tank) != null && ((System.currentTimeMillis() - fireCooldowns.get(tank)) / 1000) < tank.getFireCooldown()) {
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

    private Thread graphicsThread() {
        Thread graphicsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStopped) {
                    repaint();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        graphicsThread.start();
        return graphicsThread;
    }

    private void defendersSPThread() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future[] isReady = {executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        })};
        ui.getFrame().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (isReady[0].isDone()) {
                    isReady[0] = executorService.submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                                action(Response.MOVE_DOWN, defender);
                            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                                action(Response.MOVE_UP, defender);
                            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                                action(Response.MOVE_LEFT, defender);
                            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                action(Response.MOVE_RIGHT, defender);
                            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                                action(Response.FIRE, defender);
                            }
                            return true;
                        }
                    });
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void defendersMPThread() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future[] isReady = {executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        })};
        ui.getFrame().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (isReady[0].isDone()) {
                    isReady[0] = executorService.submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                                client.sendAction(Response.MOVE_DOWN);
                            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                                client.sendAction(Response.MOVE_UP);
                            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                                client.sendAction(Response.MOVE_LEFT);
                            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                client.sendAction(Response.MOVE_RIGHT);
                            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                                client.sendAction(Response.FIRE);
                            }
                            return true;
                        }
                    });
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private Thread agressorsThread() {
        Thread agressorsThread =
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!isStopped) {
                            action(agressor.response(defender.getX(), defender.getY()), agressor);
                        }
                    }
                });
        agressorsThread.start();
        return agressorThread;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        battleField.draw(g);
        defender.draw(g);
        agressor.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        battleField.drawWater(g);
    }

    public void shutdown() {
        isStopped = true;
    }
}