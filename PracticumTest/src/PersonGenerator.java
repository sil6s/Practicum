import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args) {
        ArrayList<String> folks = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean doneInput = false;
        String ID;
        String firstName;
        String lastName;
        String title;
        int YOB;

        do {
            ID = SafeInput.getRegExString(in, "Enter the ID [6 digits]", "\\d{6}");
            firstName = SafeInput.getNonZeroLenString(in, "Enter the first name");
            lastName = SafeInput.getNonZeroLenString(in, "Enter the last name");
            title = SafeInput.getNonZeroLenString(in, "Enter the title");
            YOB = SafeInput.getRangedInt(in, "Enter the year of birth", 1000, 9999);

            String personRec = String.format("%s, %s, %s, %s, %d", ID, firstName, lastName, title, YOB);
            folks.add(personRec);

            doneInput = SafeInput.getYNConfirm(in, "Are you done entering people?");

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

            for (String rec : folks) {
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