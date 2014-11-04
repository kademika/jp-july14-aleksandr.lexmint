package shop.client;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.SocketChannel;

/**
 * Created by lexmint on 14.09.14.
 */
public class Client {
    private final String host = "localhost";
    private final int port = 8075;

    private Socket socket;

    private Shop shop;

    public Client(Shop shop) {
        this.shop = shop;
    }

    public void init() {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(1000);
            readData();
        } catch (IOException exc) {
            System.out.println("Shop needs online server!");
        }
    }

    private void readData() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("GET_DATA");
        bufferedWriter.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "Unicode"));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                deserialize(line);
            }
        } catch (SocketTimeoutException exc) {

        }
    }

    private void deserialize(String str) {
        String[] request = str.split("_");
        if (request[0].equals("Item")) {
            shop.addItem(request[1], request[2], Double.parseDouble(request[3]), Integer.parseInt(request[4]));
        } else if (request[0].equals("Transaction")) {
            try {
                shop.processTransaction(request[1], Integer.parseInt(request[2]), Integer.parseInt(request[3]), Double.parseDouble(request[4]), Long.parseLong(request[5]));
            } catch (ItemOutOfStockException e) {
                e.printStackTrace();
            }
        }
    }
}
