package shop.server.domain;

import shop.client.ItemOutOfStockException;


/**
 * Created by lexmint on 01.07.14.
 */
public class Item implements shop.server.Serializable {

    private String producer;
    private String name;
    private double price;
    private int quantity;
    private int id;

    public Item(String producer, String name, double price, int quantity, int id) {
        this.producer = producer;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
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
        return quantity;
    }

    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Item")
                .append("_")
                .append(producer)
                .append("_")
                .append(name)
                .append("_")
                .append(price)
                .append("_")
                .append(quantity);

        return stringBuilder.toString();
    }

    public int getId() { return id; }


}
