package shop.server;


import shop.server.domain.Customer;
import shop.server.domain.Item;
import shop.server.domain.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by lexmint on 01.07.14.
 */
public class Storage<T extends Item> {
    private ArrayList<T> items = new ArrayList<T>();



    private HashMap<String, Customer> customers = new HashMap<String, Customer>();
    private LinkedList<Transaction> transactions = new LinkedList<Transaction>();

    public Storage() {

    }


    public void addItem(T item) {
        items.add(item);;
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public void addCustomer(String name) {
        customers.put(name, new Customer(name));
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public Customer getCustomer(String name) {
        return customers.get(name);
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public T getItem(int index) {
        return items.get(index);
    }

    public HashMap<String, Customer> getCustomers() {
        return customers;
    }


    public int getItemsSize() {
        return items.size();
    }

}
