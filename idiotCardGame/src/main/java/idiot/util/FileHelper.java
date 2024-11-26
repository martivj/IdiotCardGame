package idiot.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    private static Path replayFolderPath = Paths.get("src/main/resources/idiot/replays/");

    public static void setReplayFolderPath(Path path) {
        replayFolderPath = path;
    }

    public static List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(replayFolderPath.resolve(fileName));
    }

    public static void writeLines(String fileName, List<String> lines) throws IOException {
        Files.write(replayFolderPath.resolve(fileName), lines, Charset.defaultCharset(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static List<String> getReplayNames() {
        File folder = replayFolderPath.toFile();
        File[] files = folder.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            if (!file.isDirectory())
                fileNames.add(file.getName());
        }
        return fileNames;
    }
}