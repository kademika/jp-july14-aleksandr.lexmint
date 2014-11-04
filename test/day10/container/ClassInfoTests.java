package day10.container;

//import junit.framework.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
import day10.reflection.ClassInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lexmint on 01.07.14.
 */
//@RunWith(JUnit4.class)
public class ClassInfoTests {

//    @Test
    public void initClassListTest() {
        List<Object> list = new ArrayList<Object>();
        list.add(5); list.add(5); list.add(5);

//        Assert.assertNotNull(ClassInfo.initClass(Toyota.class, list));

    }

//    @Test
    public void initClassHashMapTest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("price", 1);
        map.put("passengers", 5);
        map.put("speed", 10);
        Toyota car = ClassInfo.initClass(Toyota.class, map);
//        Assert.assertEquals(1, car.getPrice());
//        Assert.assertEquals(5, car.getPassengers());
//        Assert.assertEquals(10, car.getSpeed());
    }

//    @Test
    public void setPrivatesTest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("guarantee", 7);
        Toyota car = new Toyota();
        ClassInfo.setPrivates(car, map);
//        Assert.assertEquals(7, car.getGuarantee());
    }



}
