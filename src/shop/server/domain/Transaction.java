package shop.server.domain;

import java.util.Date;

/**
 * Created by lexmint on 01.07.14.
 */
public class Transaction implements shop.server.Serializable {

    private Customer customer;
    private Item item;
    private int quantity;
    private double sum;
    private Date date;

    public Transaction(Customer customer, Item item, int quantity) {
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.sum = item.getPrice() * quantity;
        this.date = new Date(System.currentTimeMillis());
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

    @Override
    public String serialize() {
        StringBuilder transactionString = new StringBuilder();
        transactionString
                .append("Transaction")
                .append("_")
                .append(customer.getName())
                .append("_")
                .append(item.getId())
                .append("_")
                .append(quantity)
                .append("_")
                .append(sum)
                .append("_")
                .append(date.getTime());

        return transactionString.toString();
    }
}
