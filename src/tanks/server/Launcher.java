package tanks.server;

import tanks.server.network.HubServer;

import java.io.IOException;

/**
 * Created by lexmint on 28.09.14.
 */
public class Launcher {
    public static void main(String[] args) {
        HubServer hubServer = new HubServer(20560);
        hubServer.registerAvailablePort(20561);
        try {
            hubServer.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
