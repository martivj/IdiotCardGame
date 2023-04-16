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


/* Credit: Eirik Lorgen Tanberg for the original file. */


public class FileHelper {

    /* path to replay folder */

    private static final String REPLAY_FOLDER_PATH = "idiotCardGame/src/main/resources/idiot/replays/";

    /* read from file within the resources directory */

    public static List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(REPLAY_FOLDER_PATH + fileName));
    }

    /* Write lines to file at the given path. Overwrites any existing file. */
     
    public static void writeLines(String fileName, List<String> lines) throws IOException {
        Files.write(Path.of(REPLAY_FOLDER_PATH + fileName + ".txt"), lines, Charset.defaultCharset(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* get names of all available replay files */

    public static List<String> getReplayNames() {
        File folder = new File(REPLAY_FOLDER_PATH);
        File[] files = folder.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            if (!file.isDirectory())
            fileNames.add(file.getName());
        }
        return fileNames;
    }
}
