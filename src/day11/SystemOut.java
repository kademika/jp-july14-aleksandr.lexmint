package day11;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by lexmint on 11.07.14.
 */
public class SystemOut {
    public static void main(String[] args) {

        try {
            System.setOut(new PrintStream("testdir/systemout.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("stestsfsdfsdf");
        System.out.println("new");
        System.out.println("again new");
    }


}
