import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lexmint on 26.07.14.
 */
public class speedlist {
    public static void main(String[] args) {
        HashSet<String> list = new HashSet<String>();
        for (int i = 0; i < 10000; i++) {
            list.add("test" + i);
        }

        long start = System.currentTimeMillis();
        for (String str : list) {
            if (str.equals("lol")) {
                System.out.println("test");
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
