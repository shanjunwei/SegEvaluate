package file;

import java.io.*;

public class FileTest {

    public static void main(String[] args) {
        testAddLineNumToFile("data/test-text.txt","data/test-text02.txt");
    }


    public static void testAddLineNumToFile(String inputPath, String outputPath) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "utf-8"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8"))
        ) {
            String result;
            int lineCount = 1;
            while ((result = reader.readLine()) != null) {
                result = result.trim();
                writer.write(lineCount + " " + result+"\n");
                lineCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
