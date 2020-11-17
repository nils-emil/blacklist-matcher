package utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileUtils {

    private FileUtils() {
    }

    public static Set<String> getCsvLinesAsUniqueNames(String filename) throws FileNotFoundException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = classloader.getResourceAsStream(filename);
        if (resourceAsStream == null) {
            throw new FileNotFoundException("File not found");
        }
        Set<String> names = new HashSet<>();
        try (Scanner scanner = new Scanner(resourceAsStream)) {
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        }
        return names;
    }

}
