import java.util.*;
import java.io.*;

public class FileWriter {

    public void writeToFile(String text) throws java.io.IOException
    {
        String userHomeFolder = System.getProperty("user.home");
        File textFile = new File(userHomeFolder + "/Desktop/", "Database.txt");
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
        (textFile), false), "utf-8"))) {
            writer.write(text);
        }
    }
        
}
