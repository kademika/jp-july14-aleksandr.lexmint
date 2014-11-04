package day12.bank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lexmint on 26.07.14.
 */
public class LegacyAtm {


    int balance;


    public LegacyAtm() {
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int balance() {
        return balance;
    }

    public boolean withdraw(int sum) {

    try {
        if (balance >= sum) {

            System.out.println(Thread.currentThread().getName() + " Withdraw SUCCESS! Withdrawal: " + sum + " Balance: " + balance);
            balance -= sum;
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " Withdraw FAIL, not enough money! Withdrawal: " + sum + " Balance: " + balance);
        }
    } finally {



}
        return false;

    }
}
