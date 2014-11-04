package day11.objectlist;

import java.io.Serializable;

/**
 * Created by lexmint on 22.07.14.
 */
public class TestObject implements Serializable {
    public String str;
    public int id;

    public TestObject(String str, int id) {
        this.str = str;
        this.id = id;
    }
}
