import java.util.*;
import java.io.*;

public class FileWriter {

    public void writeToFile(String text) throws java.io.IOException
    {
        System.out.println("Saving to file 3");
        
        String userHomeFolder = System.getProperty("user.home");
        File textFile = new File(userHomeFolder + "/Desktop/", "Database.txt");
        System.out.println("Writing " + text + " to " + userHomeFolder + "/Desktop/Database.txt");
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
        (textFile), true), "utf-8"))) {
            System.out.println("Saving to file 4");
            writer.write(text);
        }
    }
        
}
