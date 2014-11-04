package shop.server;

import shop.client.*;
import shop.server.domain.Item;

import java.io.*;
import java.util.Random;

/**
 * Created by lexmint on 13.09.14.
 */
public class Launcher {
    public static void main(String[] args) throws IOException, ItemOutOfStockException, shop.client.ItemOutOfStockException {
        final Storage<Item> storage = new Storage<>();
        Shop shop = new Shop(storage);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server server = new Server(storage);
                    server.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "SocketServerThread").start();


        for(int i = 0; i < 2000; i++) {
            shop.addItem("Apple", new StringBuilder().append("iPhone ").append(i).toString(), 31990, i);
        }
        for (int i = 0; i < 500; i++) {
            shop.buy("Aleksandr", i, i);
        }

        Random r = new Random();
    }
}
