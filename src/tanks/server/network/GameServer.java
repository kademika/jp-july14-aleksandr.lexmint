package tanks.server.network;

import tanks.server.game.ActionField;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by lexmint on 28.09.14.
 */
public class GameServer extends AbstractServer {

    private final static int PLAYER_LIMIT = 2;
    private int playersOnline = 0;
    private GameState gameState;
    private HashMap<SocketChannel, Player> playersBySocketChannel = new HashMap<>();

    private ActionField game;

    public GameServer(int port) {
        super(port);
        gameState = GameState.WAITING_FOR_PLAYERS;
    }

    @Override
    protected void executeRequest(String request, SocketChannel sc) {
        if (request.startsWith("JOIN")) {
            join(request, sc);
        } else if (request.startsWith("ACTION") && gameState == GameState.GAME) {
            processAction(request, sc);
        }
    }

    public void setPlayersOnline(int playersOnline) {
        this.playersOnline = playersOnline;
    }

    public int getPlayersOnline() {
        return playersOnline;
    }

    public GameState getGameState() {
        return gameState;
    }

    private void join(String request, SocketChannel sc) {
        if (playersOnline < PLAYER_LIMIT) {
            if (request.split("_").length >= 2 && playersBySocketChannel.get(sc) == null) {
                playersOnline++;
                playersBySocketChannel.put(sc, new Player(request.split("_")[1], sc, playersOnline));

                if (playersOnline == PLAYER_LIMIT) {
                    startGame();
                }
            } else {
                sendResponse("CAN'T JOIN", sc);
            }
        } else {
            sendResponse("PLAYERS_LIMIT_REACHED", sc);
            try {
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startGame() {
        gameState = GameState.STARTING;

        game = new ActionField();

        gameState = GameState.GAME;
    }

    Future task1;
    Future task2;

    private void processAction(String request, SocketChannel sc) {
        final String action = request.split("_", 2)[1];

        ExecutorService executor = playersBySocketChannel.get(sc).getExecutorService();
        if (playersBySocketChannel.get(sc).getTankId() == 1) {
            if (task1 == null || task1.isDone()) {
                task1 = executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        game.action(action, game.getAgressor());

                    }
                });
                notifyPlayers(action, playersBySocketChannel.get(sc).getTankId());
            }
        } else if (playersBySocketChannel.get(sc).getTankId() == 2) {
            if (task2 == null || task2.isDone()) {
                task2 = executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        game.action(action, game.getDefender());
                    }
                });
                notifyPlayers(action, playersBySocketChannel.get(sc).getTankId());

            }
        }
        System.out.println(action);
    }

    private void notifyPlayers(String action, int tankId) {
        for (SocketChannel sc : playersBySocketChannel.keySet()) {
            StringBuilder notification = new StringBuilder();
            notification.append(tankId).append('_').append(action).append('\n');
            sendResponse(notification.toString(), sc);
        }
    }


}
