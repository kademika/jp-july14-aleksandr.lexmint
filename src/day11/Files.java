package day11;

import java.io.File;
import java.io.IOException;

/**
 * Created by lexmint on 10.07.14.
 */
public class Files {
    public static void main(String[] args) throws IOException {
        File dir = new File("/Users/lexmint/Dropbox/jb-midgardabc/jc-jun-aleksandr.lexmint/testdir");
        System.out.println(dir.isDirectory());
        if (dir.mkdir()) {
            System.out.println("Directory testdir was created!");
        } else {
            System.out.println("Directory testdir was not created!");
        }
        System.out.println(dir.isDirectory());


        File file = new File("testdir/test.txt");
        if (file.createNewFile()) {
            System.out.println("File test.txt was successfully created!");
        } else {
            System.out.println("File test.txt already exists!");
        }

        System.out.println("Absolute path: " + file.getAbsolutePath());
        System.out.println("Canonical path: " + file.getCanonicalPath());
        System.out.println("Free space on disk: " + file.getFreeSpace() / 1024 / 1024);
//        System.getProperty(File.pathSeparator);
//        System.getProperty(File.separator);
//        System.getProperty(File.listRoots())
        File userDir = new File("/Users/lexmint");

       for ( File root : userDir.listFiles()) {
           if (root.isDirectory()) {
               System.out.println(root.getAbsolutePath());
           }

        }



    }
}
