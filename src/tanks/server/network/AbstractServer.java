package tanks.server.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lexmint on 30.09.14.
 */
public abstract class AbstractServer {

    private int port;

    protected final HashMap<SocketChannel, Queue<ByteBuffer>> requestsMap = new HashMap<SocketChannel, Queue<ByteBuffer>>();

    public AbstractServer(int port) {
        this.port = port;
    }

    public final void init() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", port));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            for (Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator(); keyIterator.hasNext(); ) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
            }
        }
    }

    private final void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);

        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private final void read(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();

        Queue<ByteBuffer> byteBuffers = requestsMap.get(sc);
        if (byteBuffers == null) {
            byteBuffers = new LinkedBlockingDeque<>();
            requestsMap.put(sc, byteBuffers);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32);
        while (sc.read(byteBuffer) > 0) {
            byteBuffers.add(byteBuffer);
            byteBuffer.flip();
        }

        sc.register(key.selector(), SelectionKey.OP_WRITE);
    }

    private final void write(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        Queue<ByteBuffer> requests = requestsMap.get(sc);

        StringBuilder requestsLine = new StringBuilder();
        for (ByteBuffer buffer : requests) {
            while (buffer.remaining() >= 1) {
                char ch = (char) buffer.get();
                if (ch != '\r' && ch != '\n') {
                    requestsLine.append(ch);
                }
            }
        }

        String[] requestsArray = requestsLine.toString().split(";");

        for (String request : requestsArray) {
            executeRequest(request, sc);
        }

        if (sc.isOpen()) {
            sc.register(key.selector(), SelectionKey.OP_READ);
        }
    }

    protected abstract void executeRequest(String request, SocketChannel sc);

    protected void sendResponse(String response, SocketChannel sc) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(response.length() * 2 + 2);
            byteBuffer.asCharBuffer().put(response);
            sc.write(byteBuffer);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

}
