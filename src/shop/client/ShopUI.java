package shop.client;



import shop.client.domain.Transaction;
import shop.client.domain.item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
* Created by lexmint on 02.07.14.
*/
class ShopUI extends JPanel {

    private final Shop shop;
    private final JFrame frame;
    private final JPanel panel;

    private JScrollPane tablePanel;

    public ShopUI(final Shop shop) {
        this.shop = shop;
        this.panel = this;

        frame = new JFrame("Shop - Client");
        frame.setLocation(0, 0);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);


        MenuBar menuBar = new MenuBar();
        frame.setMenuBar(menuBar);
        Menu viewMenu = new Menu("View");
        MenuItem showTransItem = new MenuItem("Show transactions");
        viewMenu.add(showTransItem);
        menuBar.add(viewMenu);
        showTransItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().remove(panel);
            }
        });

        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();


        JLabel label = new JLabel("Enter your name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(label, c);

        final JTextField textField = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        this.add(textField, c);

        ArrayList<String> goods = new ArrayList<String>();
        for (Item item : shop.getItems()) {
            goods.add(item.getName());

        }

        final JComboBox comboBox = new JComboBox<>(goods.toArray());
        c.gridx = 0;
        c.gridy = 1;
        this.add(comboBox, c);

        JLabel label_2 = new JLabel("Count: ");
        c.gridx = 0;
        c.gridy = 2;
        this.add(label_2, c);

        final JTextField textfield_2 = new JTextField(2);
        c.gridx = 1;
        c.gridy = 2;
        this.add(textfield_2, c);

        JButton button = new JButton("Buy");
        c.gridx = 0;
        c.gridy = 3;
        this.add(button, c);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane();
                panel.add(optionPane);
                try {
                    if (!textField.getText().matches("[A-z]*") || textField.getText().trim().isEmpty()) {
                        optionPane.showMessageDialog(frame, "Enter correct name!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int itemId = comboBox.getSelectedIndex();
                        shop.buy(textField.getText(), itemId, Integer.parseInt(textfield_2.getText()));
                    }
                } catch (NumberFormatException exc) {
                    optionPane.showMessageDialog(frame, "Enter correct number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ItemOutOfStockException exc) {
                    optionPane.showMessageDialog(frame, "There is not enough items in the shop. Only " + exc.getItemsAvailable() + " available.", "Item of stock", JOptionPane.PLAIN_MESSAGE);
                }
            }

        });




        frame.pack();
        frame.setVisible(true);
    }

    public void createTable(GridBagConstraints c) {

        String[] columnNames = {
                "Item", "Customer", "Quantity", "Summary"
        };

        LinkedList<Transaction> transactions = shop.getTransactions();
        Object[][] data = new Object[transactions.size()][4];
        int i = 0;
        for (Transaction transaction : transactions) {
            data[i][0] = transaction.getItem().getName();
            data[i][1] = transaction.getCustomer().getName();
            data[i][2] = transaction.getQuantity();
            data[i][3] = transaction.getSum();
            i++;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        c.gridx = 0;
        c.gridy = 5;
        this.add(scrollPane, c);

        this.tablePanel = scrollPane;
    }

    public void updateTable(GridBagConstraints c) {
        this.frame.remove(tablePanel);
        this.add(this.tablePanel, c);
        frame.pack();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
