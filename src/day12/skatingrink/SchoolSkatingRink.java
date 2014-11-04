package day12.skatingrink;

import java.util.LinkedList;

/**
 * Created by lexmint on 06.08.14.
 */
public class SchoolSkatingRink implements SkatingRink {

    LinkedList<Skates> skatesList = new LinkedList<>();

    public SchoolSkatingRink() {
        skatesList.add(new Skates(1));
        skatesList.add(new Skates(2));
        skatesList.add(new Skates(3));
    }

    @Override
    public Skates getSkates(Skater skater) throws InterruptedException {
        if (skatesList.isEmpty()) {
            synchronized (skatesList) {
                System.out.println("New skater " + skater.name + " is waiting for skates");
                skatesList.wait();
            }
        }
        synchronized (skatesList) {
            System.out.println("Skater " + skater.name + " got his skates and now skating!");
            Skates skates = skatesList.getFirst();
            skatesList.removeFirst();
            return skates;
        }
    }

    @Override
    public void returnSkates(Skates skates, Skater skater) throws InterruptedException {
        System.out.println("Skater " + skater.name + " has returned skates №" + skates.number);
        synchronized (skatesList) {
            skatesList.add(skates);
            skatesList.notify();
        }
    }
}
