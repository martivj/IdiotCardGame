package idiot.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class FileHelperTest {

    @Test
    void testReadLines() throws IOException {
        // create a test file
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, Arrays.asList("foo", "bar"));
        
        // read lines from the test file
        List<String> lines = FileHelper.readLines(tempFile.getFileName().toString());
        
        // verify that the lines are read correctly
        assertEquals(Arrays.asList("foo", "bar"), lines);
        
        // delete the test file
        Files.delete(tempFile);
    }
    
    @Test
    void testWriteLines() throws IOException {
        // create a test file
        Path tempFile = Files.createTempFile("test", ".txt");
        
        // write lines to the test file
        FileHelper.writeLines(tempFile.getFileName().toString(), Arrays.asList("foo", "bar"));
        
        // read lines from the test file
        List<String> lines = Files.readAllLines(tempFile);
        
        // verify that the lines are written correctly
        assertEquals(Arrays.asList("foo", "bar"), lines);
        
        // delete the test file
        Files.delete(tempFile);
    }
    
    @Test
    void testGetReplayNames() {
        // get the replay names
        List<String> replayNames = FileHelper.getReplayNames();
        
        // verify that the list is not empty
        assertFalse(replayNames.isEmpty());
        
        // verify that all elements in the list end with ".txt"
        assertTrue(replayNames.stream().allMatch(name -> name.endsWith(".txt")));
    }
}
