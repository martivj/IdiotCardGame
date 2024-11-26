package idiot.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHelperTest {

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("test_replays");
        FileHelper.setReplayFolderPath(tempDir);
        // Create a temporary file to ensure the directory is not empty
        Files.createFile(tempDir.resolve("test.txt"));
    }

    @Test
    void testReadLines() throws IOException {
        Path tempFile = tempDir.resolve("test.txt");
        Files.write(tempFile, Arrays.asList("foo", "bar"));

        List<String> lines = FileHelper.readLines(tempFile.getFileName().toString());

        assertEquals(Arrays.asList("foo", "bar"), lines);
    }

    @Test
    void testWriteLines() throws IOException {
        Path tempFile = tempDir.resolve("test.txt");

        FileHelper.writeLines(tempFile.getFileName().toString(), Arrays.asList("foo", "bar"));

        List<String> lines = Files.readAllLines(tempFile);

        assertEquals(Arrays.asList("foo", "bar"), lines);
    }

    @Test
    void testGetReplayNames() {
        List<String> replayNames = FileHelper.getReplayNames();

        assertFalse(replayNames.isEmpty());
        assertTrue(replayNames.stream().allMatch(name -> name.endsWith(".txt")));
    }
}