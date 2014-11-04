package shop.server;

/**
 * Created by lexmint on 02.07.14.
 */
public class ItemOutOfStockException extends Exception {

    private int itemsAvailable;

    public ItemOutOfStockException(int itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }

    public int getItemsAvailable() {
        return itemsAvailable;
    }
}
