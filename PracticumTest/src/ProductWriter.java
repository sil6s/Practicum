import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {
        ArrayList<String> products = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean doneInput = false;
        String ID;
        String name;
        String description;
        double cost;

        do {
            ID = SafeInput.getRegExString(in, "Enter the ID [6 digits]", "\\d{6}");
            name = SafeInput.getNonZeroLenString(in, "Enter the product name");
            description = SafeInput.getNonZeroLenString(in, "Enter the product description");

            // Cast the result to double, assuming getRangedDouble is actually returning an int
            cost = (double) SafeInput.getRangedDouble(in, "Enter the product cost", 1, 9999);

            String productRec = String.format("%s, %s, %s, %.2f", ID, name, description, cost);
            products.add(productRec);

            doneInput = SafeInput.getYNConfirm(in, "Are you done entering products?");

        } while(!doneInput);

        // Prompt for file name
        String fileName = SafeInput.getNonZeroLenString(in, "Enter the name for the output text file (without extension)");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + File.separator + fileName + ".txt");

        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for (String rec : products) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Data file written to: " + file.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}