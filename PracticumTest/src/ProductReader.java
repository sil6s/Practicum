import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // Display header
                System.out.println(String.format("%-8s %-20s %-30s %s", "ID", "Name", "Description", "Cost"));
                System.out.println("=================================================================================");

                // Read and display each line
                while (reader.ready()) {
                    rec = reader.readLine();
                    String[] fields = rec.split(", ");
                    if (fields.length == 4) {
                        System.out.println(String.format("%-8s %-20s %-30s $%.2f",
                                fields[0], fields[1], fields[2], Double.parseDouble(fields[3])));
                    }
                }
                reader.close();
                System.out.println("\nData file read!");
            } else {
                System.out.println("No file selected. Exiting program.");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}