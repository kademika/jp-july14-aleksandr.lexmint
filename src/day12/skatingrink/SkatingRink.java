package day12.skatingrink;

/**
 * Created by lexmint on 06.08.14.
 */
public interface SkatingRink {

    public Skates getSkates(Skater skater) throws InterruptedException;

    public void returnSkates(Skates skates, Skater skater) throws InterruptedException;
}
