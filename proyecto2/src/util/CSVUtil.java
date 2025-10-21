package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVUtil {
    public static String[][] leerCSV(String fileName) {
        String[][] lines = new String[100][];
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines[count++] = line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] result = new String[count][];
        System.arraycopy(lines, 0, result, 0, count);
        return result;
    }

    public static void escribirCSV(String fileName, String[][] lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] line : lines) {
                writer.write(String.join(",", line));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}