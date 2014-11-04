package tanks.client;

import tanks.client.tank.Response;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lexmint on 05.10.14.
 */
public class Client {

    private ActionField actionField;

    private Socket hubSocket;
    private Socket gameSocket;

    public void connectToHub() {
        try {
            hubSocket = new Socket("localhost", 20560);
            hubSocket.setSoTimeout(1000);
        } catch (IOException e) {
            System.out.println("Главный сервер недоступен! Невозможно подключиться!");
        }

    }

    public boolean createGame(final String playerName) {
        connectToHub();

        String answer = sendRequest("CREATE_GAME", hubSocket, true);
        if (answer == null || answer.equals("HOSTING_IS_NOT_AVAILABLE")) {
            System.out.println("HOSTING IS NOT AVAILABLE");
        } else {
            final int gamePort = Integer.valueOf(answer);

            try {
                joinGame(playerName, gamePort);
            } catch (IOException e) {
                e.printStackTrace();
                //TODO
            }

            return true;
        }

        return false;
    }

    public boolean joinGame(final String playerName, final int gamePort) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gameSocket = new Socket("localhost", gamePort);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String answer = sendRequest("JOIN_" + playerName, gameSocket, true);
                if (answer == null || answer.equals("PLAYERS_LIMIT_REACHED")) {
                    System.out.println("PLAYERS LIMIT REACHED");

                } else {
                    game();
                }
            }
        }).start();

        return true;
    }

    public boolean searchGame(String playerName) {
        try {
            connectToHub();
            String answer = sendRequest("SEARCH_GAME", hubSocket, true);
            if (answer == null || answer.equals("NO_SERVERS_AVAILABLE")) {
                System.out.println("NO SERVERS AVAILABLE");
            } else {
                return joinGame(playerName, Integer.valueOf(answer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String sendRequest(String request, Socket socket, boolean needsAnswer) {
        try {
            StringBuilder answer = new StringBuilder();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(request);
            bufferedWriter.flush();

            if (needsAnswer) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "Unicode"));
                char ch;
                while ((ch = (char) bufferedReader.read()) != -1 && ch != 0) {
                    answer.append(ch);
                }
                return answer.toString();
            }

        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public void sendAction(Response action) {
        sendRequest(new StringBuilder("ACTION_").append(action.name()).toString(), gameSocket, false);
    }

    private void game() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gameSocket.getInputStream(), "Unicode"));

            ExecutorService executorService = Executors.newFixedThreadPool(10);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String lineFinal = line;
                System.out.println(lineFinal.split("_", 2)[1]);
                // TODO \u0000
                System.out.println(Integer.valueOf(lineFinal.split("_", 2)[0].replaceAll("\u0000", "")));
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        actionField.action(Response.valueOf(lineFinal.split("_", 2)[1]), Integer.valueOf(lineFinal.split("_", 2)[0].replaceAll("\u0000", "")));
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActionField(ActionField af) {
        actionField = af;
    }
}
