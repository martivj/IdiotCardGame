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

    private static Path replayFolderPath = Paths.get("idiotCardGame/replays/");

    public static void setReplayFolderPath(Path path) {
        replayFolderPath = path;
    }

    public static List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(replayFolderPath.resolve(fileName));
    }

    public static void writeLines(String fileName, List<String> lines) throws IOException {
        Path filePath = replayFolderPath.resolve(fileName);
        Files.createDirectories(filePath.getParent()); // Ensure the directory exists
        Files.write(filePath, lines, Charset.defaultCharset(), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static List<String> getReplayNames() {
        File folder = replayFolderPath.toFile();
        List<String> fileNames = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }
}