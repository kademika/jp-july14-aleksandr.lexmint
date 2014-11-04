package tanks.client;

import java.awt.*;

public class Launcher {

	public static void main(String[] args) throws Exception {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        Thread.sleep(5000);
        splashScreen.close();

        Client client = new Client();
        client.connectToHub();

        UI ui = new UI(client);

	}
}
