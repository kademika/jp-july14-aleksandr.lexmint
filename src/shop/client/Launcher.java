package shop.client;

import java.io.IOException;

/**
 * Created by lexmint on 01.07.14.
 */
public class Launcher {


    public static void main(String[] args) throws IOException {


        Shop shop = new Shop();

        Client client = new Client(shop);
        client.init();

        ShopUI ui = new ShopUI(shop);
    }

}
