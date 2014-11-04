package day11;

import java.io.*;
import java.nio.file.*;

/**
 * Created by lexmint on 10.07.14.
 */
public class CopyFile {
    public static void main(String[] args) {

        File copyFile = new File("testdir/test.txt");
        File destFile = new File("testdir/testCopy.txt");
        Long start = System.currentTimeMillis();
        copyFile(copyFile, destFile);
        System.out.println(System.currentTimeMillis() - start);


    }

    public static void copyFile(File copyFile, File destFile) {
        StringBuilder sb = new StringBuilder();
        try (
                BufferedReader buffReader = new BufferedReader(new FileReader(copyFile), 256);
                BufferedWriter buffWriter = new BufferedWriter(new FileWriter(destFile), 256);
        ) {

            int ch = buffReader.read();
            do {
                sb.append((char) ch);
                ch = buffReader.read();
            } while (ch != -1);
            buffReader.close();

            for (int i = 0; i < sb.length(); i++) {
                buffWriter.write(sb.charAt(i));
            }

        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        } catch (IOException exc) {
            exc.printStackTrace();
        }


    }

}
