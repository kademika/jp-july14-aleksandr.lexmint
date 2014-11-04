package shop.client.domain;

import shop.client.domain.item.Item;

import java.util.Date;

/**
 * Created by lexmint on 01.07.14.
 */
public class Transaction {

    private Customer customer;
    private Item item;
    private int quantity;
    private double sum;
    private Date date;

    public Transaction(Customer customer, Item item, int quantity, double sum, long time) {
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.sum = sum;
        this.date = new Date(time);
    }

    public double getSum() {
        return sum;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
