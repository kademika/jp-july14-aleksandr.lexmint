package tanks.server.network;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lexmint on 05.10.14.
 */
public class Player {
    private SocketChannel socketChannel;
    private String name;
    private int tankId;
    private ExecutorService executorService;

    public Player (String name, SocketChannel socketChannel, int tankId) {
        this.socketChannel = socketChannel;
        this.name = name;
        this.tankId = tankId;
        executorService = Executors.newSingleThreadExecutor();
    }

    public String getName() {
        return name;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public int getTankId() {
        return tankId;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
