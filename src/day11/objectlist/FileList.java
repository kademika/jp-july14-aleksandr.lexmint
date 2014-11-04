package day11.objectlist;

import java.io.*;
import java.util.*;

/**
 * Created by lexmint on 22.07.14.
 */
public class FileList<T> {

    File file;
    int index;

    public FileList(String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner scanner = new Scanner(new FileInputStream(file));
        try {
        String line;
        while ((line = scanner.nextLine()) != null) {
            if (line.split("_")[0].equals("")) {
                return;
            }
            if (Integer.parseInt(line.split("_")[0]) >= index) {
                index = Integer.parseInt(line.split("_")[0]) + 1;
            }
        } } catch (NoSuchElementException exc) {

        }

    }

    public void add(TestObject obj) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.append(String.valueOf(index) + "_");
        fileWriter.append(obj.str + "_");
        fileWriter.append(String.valueOf(obj.id));
        fileWriter.append('\n');
        index++;
        fileWriter.flush();
        fileWriter.close();
    }

    public TestObject get(int index) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            String line;
            while ((line = scanner.nextLine()) != null) {
                if (line.startsWith(String.valueOf(index))) {
                    scanner.close();
                    return new TestObject(line.split("_")[1], Integer.parseInt(line.split("_")[2]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    public void remove(int index) throws IOException {
        File tempFile = new File("temp.tmp");
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while((line = br.readLine()) != null) {
            if (!line.startsWith(String.valueOf(index + "_"))) {
                bw.write(line + '\n');
            }
        }
        br.close();
        bw.flush();
        bw.close();
        file.delete();
        tempFile.renameTo(file);

    }


}
