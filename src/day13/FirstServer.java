package day13;

import com.javafx.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by lexmint on 21.08.14.
 */
public class FirstServer {

    static Set<SocketChannel> sockets = new HashSet<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8075));
        serverSocketChannel.configureBlocking(false);

//        ServerSocket serverSocket = new ServerSocket(8075);
        while (true) {
            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            SocketChannel socket = serverSocketChannel.accept();
            if (socket != null) {
                socket.configureBlocking(false);
                sockets.add(socket);
            }


            Iterator<SocketChannel> it = sockets.iterator();
            while (it.hasNext()) {
                socket = it.next();

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int bytesRead = socket.read(buffer);
                if (bytesRead == -1) {
                    it.remove();
                } else if (bytesRead != 0) {
                    buffer.flip();
                    socket.write(buffer);
                    buffer.clear();
                }
            }
        }


    }

    private static void read(SelectionKey key) throws IOException {

    }

    private static void accept(SelectionKey key) throws Exception {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socket = ssc.accept();
        socket.configureBlocking(false);

        socket.register(key.selector(), SelectionKey.OP_READ);

    }
}
