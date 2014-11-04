package day10.generics;

/**
 * Created by lexmint on 24.06.14.
 */
public class Launcher {
    public static void main(String[] args) {
        Box<Integer> b = new Box<Integer>();
        Integer num = b.getObj();
        System.out.println(b.toString());
    }
}
