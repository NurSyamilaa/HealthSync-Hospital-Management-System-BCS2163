package module4;

import java.io.FileWriter;

public class BPReceiptFile {

    public static void saveReceipt(
            String content) {

        try {

            FileWriter writer =
                    new FileWriter(
                            "receipt.txt");

            writer.write(content);

            writer.close();

        } catch(Exception e) {

            e.printStackTrace();

        }
    }
}