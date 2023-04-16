package idiot.util;

import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import idiot.util.FileHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelperTest {

    // @BeforeEach
	// public void setUp() throws Exception {
	// 	System.setProperty("user.dir", "idiotCardGame/src/main/java/idiot/game/");
    //     System.out.println(Files.readAllLines(Paths.get("testing.txt")));
	// }

    @Test
    void testReadLines() throws IOException {
        //System.out.println(Files.readAllLines(Paths.get("idiotCardGame/src/test/java/idiot/util/testing.txt")));
        List<String> expectedLines = Arrays.asList("Hello", "World!");
        List<String> actualLines = FileHelper.readLines("test_read_lines.txt");
        Assertions.assertEquals(expectedLines, actualLines);
    }

    @Test
    void testWriteLines() throws IOException {
        List<String> linesToWrite = Arrays.asList("Hello", "JUnit!");
        String fileName = "/test/testWriteLines";
        FileHelper.writeLines(fileName, linesToWrite);
        List<String> actualLines = FileHelper.readLines(fileName + ".txt");
        Assertions.assertEquals(linesToWrite, actualLines);
    }

    @Test
    void testGetReplayNames() {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("replay1.txt");
        expectedNames.add("replay2.txt");
        expectedNames.add("replay3.txt");
        List<String> actualNames = FileHelper.getReplayNames();
        Assertions.assertEquals(expectedNames, actualNames);
    }
}
