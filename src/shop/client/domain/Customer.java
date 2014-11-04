package shop.client.domain;

import java.util.ArrayList;

/**
 * Created by lexmint on 01.07.14.
 */
public class Customer {

    private String name;

    private double sumOfPurchases;

    private ArrayList<Transaction> purchases;

    public Customer(String name) {
        this.name = name;
        this.purchases = new ArrayList<Transaction>();
    }

    public double getSumOfPurchases() {
        return sumOfPurchases;
    }

    public void addPurchaseToSum(Transaction transaction) {
        this.sumOfPurchases += transaction.getSum();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Transaction> getPurchases() {
        return purchases;
    }
}
