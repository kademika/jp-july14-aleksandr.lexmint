package day11;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Files;

/**
 * Created by lexmint on 05.07.14.
 */
public class ByteInt {
    public static void main(String[] args) throws IOException {


        ByteArrayInputStream input = new ByteArrayInputStream(new byte[] {56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57,56, 36, 57});
        printStreamData(input);

    }

    public static void printStreamData(InputStream inputStream) throws IOException {
        File file = new File("printStreamData.txt");
        System.out.println(file.canWrite());
        System.out.println(file.canRead());
        FileWriter fileWriter = new FileWriter(file, true);


        int i = inputStream.read();
        do {
            System.out.println(i);
            fileWriter.append((char) i);
            i = inputStream.read();
        } while (i != -1);
//fileWriter.flush();
//        fileWriter.close();
        inputStream.close();
        fileWriter.flush();



    }

}
