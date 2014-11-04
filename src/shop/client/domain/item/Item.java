package shop.client.domain.item;

import shop.client.ItemOutOfStockException;

/**
 * Created by lexmint on 01.07.14.
 */
public class Item {

    private String producer;
    private String name;
    private double price;
    private int quantity;

    public Item(String producer, String name, double price, int quantity) {
        this.producer = producer;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getProducer() {
        return this.producer;
    }

    public String getName() {
        return this.name;
    }

    public void updateQuantity(int quantity) throws ItemOutOfStockException {
        if (this.quantity - quantity < 0) {
            throw new ItemOutOfStockException(this.quantity);
        }
        this.quantity -= quantity;

    }

    public int getQuantity() {
        return this.quantity;
    }

}
