package shop.server;

import shop.server.domain.Customer;
import shop.server.domain.Transaction;
import shop.server.domain.Item;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by lexmint on 13.09.14.
 */
public class Shop {
    private Storage storage;

    private final boolean DEBUG_MODE = true;

    public Shop(Storage<Item> storage) {
        this.storage = storage;
    }

    void buy(String buyerName, Item item, int quantity) throws shop.client.ItemOutOfStockException {
        item.updateQuantity(quantity);
        Customer customer = storage.getCustomer(buyerName);
        if (customer == null) {
            storage.addCustomer(buyerName);
            customer = storage.getCustomer(buyerName);
        }
        Transaction transaction = new Transaction(customer, item, quantity);
        storage.addTransaction(transaction);
        customer.addPurchaseToSum(transaction);
        if (DEBUG_MODE) {
            System.out.println("[NEW TRANSACTION] Customer: " + buyerName + " Sum of purchases: " + customer.getSumOfPurchases() + " Item: " + item.getName() + " Quantity: " + quantity + " Item available: " + item.getQuantity());
        }
    }

    public void buy(String buyerName, int itemId, int quantity) throws ItemOutOfStockException, shop.client.ItemOutOfStockException {
        buy(buyerName, storage.getItem(itemId), quantity);
    }

    public void addItem(String producer, String name, double price, int quantity) {
       Item item =  new Item(producer, name, price, quantity, storage.getItemsSize());
        storage.addItem(item);
    }

    public ArrayList<? extends Item> getItems() {
        return storage.getItems();
    }

    public LinkedList<Transaction> getTransactions() { return storage.getTransactions(); }
}
