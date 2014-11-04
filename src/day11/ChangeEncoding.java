package day11;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by lexmint on 11.07.14.
 */
public class ChangeEncoding {
    public static void main(String[] args) {
        File file = new File("testdir/test.txt");
        file.delete();
       changeEncoding(new File("testdir/test.txt"), StandardCharsets.UTF_8, StandardCharsets.US_ASCII);
    }

    public static void changeEncoding(File file, Charset currEncoding, Charset neededEncoding) {
        try (
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(fis, currEncoding), 256);
                BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(fos, neededEncoding), 256);
        ) {
            char ch = (char) buffReader.read();
            while (ch != -1) {
                buffWriter.write(ch);
                ch = (char) buffReader.read();
            }
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
