package tanks.client;

import tanks.client.battlefield.BattleField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 * Created by lexmint on 05.10.14.
 */
public class UI {
    private JFrame frame;

    private ActionField currentActionField;
    private Client client;

    private final static String PLAYER_NAME = "TEST";

    public UI(final Client client) {
        this.client = client;

        frame = new JFrame("Tanks V1.0");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(BattleField.getBfWidth(),
                BattleField.getBfHeight() + 22 + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);


        JMenu singleMenu = new JMenu("Одиночная игра");

        JMenuItem newGameItem = new JMenuItem("Новая игра");
        singleMenu.add(newGameItem);

        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(null);
            }
        });

        menuBar.add(singleMenu);

        JMenu multiMenu = new JMenu("Мультиплеер");


        JMenuItem createGameItem = new JMenuItem("Создать игру");
        multiMenu.add(createGameItem);
        createGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client.createGame(PLAYER_NAME)) {
                    newGame(client);
                }
            }
        });

        JMenuItem joinGameItem = new JMenuItem("Присоединиться к игре");
        multiMenu.add(joinGameItem);
        joinGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client.searchGame(PLAYER_NAME)) {
                    newGame(client);
                }
            }
        });

        menuBar.add(multiMenu);

        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void newGame(Client client) {
        try {
            if (currentActionField != null) {
                for (KeyListener keyListener : frame.getKeyListeners()) {
                    frame.removeKeyListener(keyListener);
                }
                frame.remove(currentActionField);
                currentActionField.shutdown();
            }

            currentActionField = new ActionField(this, client);
            if (client != null) {
                client.setActionField(currentActionField);
            }
            currentActionField.runTheGame();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
