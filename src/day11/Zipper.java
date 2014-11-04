package day11;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by lexmint on 11.07.14.
 */
public class Zipper {
    public static void main(String[] args) throws IOException {
        // Read command
        StringBuilder cmd = new StringBuilder();
        int in;
        while ((in = System.in.read()) != '\n') {
            cmd.append((char) in);
        }
        String wholeCmd = cmd.toString();

        try {
            String command = wholeCmd.split(" ")[0];
            String path = wholeCmd.split(" ")[1];

            if (command.equals("zip")) {
                zip(path);
            } else if (command.equals("unzip")) {
                unzip(path);
            } else {
                System.err.println("Wrong command!");
            }

        } catch (ArrayIndexOutOfBoundsException exc) {
            System.err.println("Wrong number of arguments!");
        }

    }

    private static void zip(String path) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path + ".zip"));
        zipper(path, zos);
        zos.close();
    }


    private static void unzip(String path) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().equals(".DS_Store")) {
                continue;
            }
            File f = new File(entry.getName());

            if (!entry.isDirectory()) {
                FileWriter fileWriter = new FileWriter(entry.getName());
                int ch;
                while ((ch = zis.read()) != -1) {
                    fileWriter.write(ch);
                }
                fileWriter.close();
            } else {
                f.mkdir();
            }
            zis.closeEntry();
        }
        zis.getNextEntry();
    }


    private static void zipper(String path, ZipOutputStream zos) throws IOException {
        File target = new File(path);
        if (target.getName().equals(".DS_Store")) {
            return;
        }


        if (target.isDirectory()) {
            zos.putNextEntry(new ZipEntry(path + "/"));
            zos.closeEntry();
            File[] files = target.listFiles();
            for (File file : files) {
                zipper(file.getPath(), zos);
            }

        } else {
            zos.putNextEntry(new ZipEntry(path));
            FileInputStream fis = new FileInputStream(path);
            int ch;
            while ((ch = fis.read()) != -1) {
                zos.write(ch);
            }
            fis.close();
            zos.closeEntry();
        }

    }
}
