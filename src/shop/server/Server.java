package shop.server;

import shop.server.domain.Customer;
import shop.server.domain.Item;
import shop.server.domain.Transaction;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lexmint on 14.09.14.
 * <p/>
 * Пытался использовать ServerSocketChannel и NIO с Selector'ами, но тогда ObjectOutputStream было невозможно
 * использовать (из-за blocking mode), а читать и отдавать все буферами накладно.
 */
public class Server {

    private HashMap<SocketChannel, Queue<ByteBuffer>> requestsMap = new HashMap<SocketChannel, Queue<ByteBuffer>>();
    private Storage<Item> storage;

    public Server(Storage<Item> storage) {
        this.storage = storage;
    }

    public void init() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 8075));
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

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);

        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();

        Queue<ByteBuffer> byteBuffers = requestsMap.get(sc);
        if (byteBuffers == null) {
            byteBuffers = new LinkedBlockingDeque<>();
            requestsMap.put(sc, byteBuffers);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        while (sc.read(byteBuffer) > 0) {
            byteBuffers.add(byteBuffer);
            byteBuffer.flip();
        }

        sc.register(key.selector(), SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
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
            if (request.equals("GET_DATA")) {
                transferItems(sc);
                transferCustomers(sc);
                transferPurchases(sc);
            }
        }


        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private void transferItems(SocketChannel sc) throws IOException {
        for (Item item : storage.getItems()) {
            String itemString = item.serialize();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(itemString.length() * 2  + 2);
            CharBuffer charBuffer = byteBuffer.asCharBuffer();
            charBuffer.put(itemString);
            charBuffer.append('\n');
            sc.write(byteBuffer);
        }
    }

    private void transferCustomers(SocketChannel sc) throws IOException {
        for (String customerName : storage.getCustomers().keySet()) {
            Customer customer = storage.getCustomers().get(customerName);
            String customerString = customer.serialize();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(customerString.length() * 2  + 2);
            CharBuffer charBuffer = byteBuffer.asCharBuffer();
            charBuffer.put(customerString);
            charBuffer.append('\n');
            sc.write(byteBuffer);
        }
    }

    private void transferPurchases(SocketChannel sc) throws IOException {
        for (Transaction transaction : storage.getTransactions()) {
            String transactionString = transaction.serialize();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(transactionString.length() * 2  + 2);
            CharBuffer charBuffer = byteBuffer.asCharBuffer();
            charBuffer.put(transactionString);
            charBuffer.append('\n');
            sc.write(byteBuffer);
        }
    }

}
