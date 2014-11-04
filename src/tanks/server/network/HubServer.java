package tanks.server.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * Created by lexmint on 28.09.14.
 */
public class HubServer extends AbstractServer {

    private LinkedList<Integer> availablePorts = new LinkedList<>();
    private LinkedList<GameServer> gameServers = new LinkedList<>();

    public HubServer(int port) {
        super(port);
    }

    @Override
    protected void executeRequest(String request, SocketChannel sc) {
        if (request.equals("CREATE_GAME")) {
            createGame(sc);
        } else if (request.equals("SEARCH_GAME")) {
            searchGame(sc);
        }
    }

    protected void createGame(SocketChannel sc) {
        try {
            if (availablePorts.size() >= 1) {
                int port = availablePorts.removeFirst();

                final GameServer gameServer = new GameServer(port);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            gameServer.init();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                gameServers.add(gameServer);

                sendResponse(String.valueOf(port), sc);
            } else {
                sendResponse("HOSTING_IS_NOT_AVAILABLE", sc);
            }
            sendResponse(new String(new char[]{(char) -1}), sc);
            sc.close();
        } catch (IOException exc) {
            // TODO: Exception
            exc.printStackTrace();
            System.exit(666);
        }
    }

    public void registerAvailablePort(int port) {
        availablePorts.add(port);
    }

    protected void searchGame(SocketChannel sc) {
        for (GameServer gameServer : gameServers) {
            if (gameServer.getGameState() == GameState.WAITING_FOR_PLAYERS) {

                sendResponse(String.valueOf(gameServer.getPort()), sc);
                try {
                    sendResponse(new String(new char[]{(char) -1}), sc);
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        sendResponse("NO_SERVERS_AVAILABLE", sc);
    }
}
