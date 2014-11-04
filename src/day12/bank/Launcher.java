package day12.bank;

/**
 * Created by lexmint on 25.07.14.
 */
public class Launcher {
    public static void main(String[] args) throws InterruptedException {
       final LegacyAtm atm = new LegacyAtm();
        atm.setBalance(1);
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                 atm.withdraw(1);
                }
            }).start();
        }
        Thread.sleep(100);
        System.out.println("BALANCE: " + atm.balance());
    }
}
