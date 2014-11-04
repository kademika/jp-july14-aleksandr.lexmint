package shop.client;


import shop.client.domain.Customer;
import shop.client.domain.Transaction;

import shop.client.domain.item.Item;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by lexmint on 01.07.14.
 */
class Shop {
    private Storage storage;

    private final boolean DEBUG_MODE = true;

    public Shop() {
        storage = new Storage();
    }

    void buy(String buyerName, Item item, int quantity, double sum, long time, boolean ignoreStock) throws ItemOutOfStockException {
        if (!ignoreStock) {
        item.updateQuantity(quantity);}
        Customer customer = storage.getCustomer(buyerName);
        if (customer == null) {
            storage.addCustomer(buyerName);
            customer = storage.getCustomer(buyerName);
        }

        Transaction transaction;
        if (sum != -1) {
            transaction = new Transaction(customer, item, quantity, sum, time);
        } else {
            transaction = new Transaction(customer, item, quantity, item.getPrice() * quantity, time);
        }
        storage.addTransaction(transaction);
        // DEBUG
        if (!ignoreStock) {
            System.out.println("[TRANSACTION] " + buyerName + " has bought " + quantity + " of " + item.getName() + " for " + transaction.getSum() + " His total sum of purchases: " + customer.getSumOfPurchases());
        }
        // DEBUG
        customer.addPurchaseToSum(transaction);
    }

    public void buy(String buyerName, int itemId, int quantity) throws ItemOutOfStockException {
        buy(buyerName, storage.getItem(itemId), quantity, -1, System.currentTimeMillis(), false);
    }

    /**
     * Equals to method buy()
     * Used for deserializing items as usual
     */
    public void processTransaction(String buyerName, int itemId, int quantity, double sum, long time) throws ItemOutOfStockException {
        buy(buyerName, storage.getItem(itemId), quantity, sum, time, true);
    }


    public ArrayList<? extends Item> getItems() {
        return storage.getItems();
    }

    public LinkedList<Transaction> getTransactions() {
        return storage.getTransactions();
    }

    public void addItem(String producer, String name, double price, int quantity) {
        Item item = new Item(producer, name, price, quantity);
        storage.addItem(item);
    }

    public Storage getStorage() {
        return storage;
    }

}
