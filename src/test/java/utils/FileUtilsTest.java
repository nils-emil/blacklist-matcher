package utils;


import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilsTest {

    @Test
    public void getCsvLinesAsUniqueNames_ReturnsSet_IfCorrectFileNameIsGiven() throws FileNotFoundException {
        // Given
        String blacklistFileName = "names.csv";
        Set<String> names = FileUtils.getCsvLinesAsUniqueNames(blacklistFileName);

        // Then
        assertTrue(names.contains("Franklin The Delano Roosevelt"));
        assertTrue(names.contains("William Henry Harrison"));
        assertTrue(names.contains("James Madison"));
        assertTrue(names.contains("Thomas Jefferson"));
        assertEquals(4, names.size());
    }

    @Test(expected = FileNotFoundException.class)
    public void getCsvLinesAsUniqueNames_ReturnsEmptySet_IfCorrectFileNameIsGiven() throws FileNotFoundException {
        // Given
        String blacklistFileName = "very_strange_name_that_some_blob_mistyped.csv";

        // Then
        FileUtils.getCsvLinesAsUniqueNames(blacklistFileName);
    }

}