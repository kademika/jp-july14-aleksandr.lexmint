package day11.objectlist;

import java.io.*;

/**
 * Created by lexmint on 22.07.14.
 */
public class Launcher {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        TestObject test1 = new TestObject("One", 35);
//        TestObject test2 = new TestObject("Two", 72);
//        TestObject test3 = new TestObject("Three", 96);
        FileList fileList = new FileList("simpleser.txt");
//        fileList.add(test1);
//        fileList.add(test2);
//        fileList.add(test3);
//        fileList.remove(1);
        fileList.remove(3); fileList.remove(4);


        System.out.println("[OBJ ONE] : " + " Str: " + fileList.get(0).str + " Id : " + fileList.get(0).id);
        System.out.println("[OBJ FIVE] : " + " Str: " + fileList.get(5).str + " Id : " + fileList.get(5).id);
    }
}
