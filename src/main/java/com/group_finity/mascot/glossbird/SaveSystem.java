package com.group_finity.mascot.glossbird;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveSystem {
    public static final Path DATA_DIRECTORY = Paths.get("data");
    public static final Path SAVE_FILE = DATA_DIRECTORY.resolve("save.properties");

    public SaveSystem() {
        if (Files.isRegularFile(SAVE_FILE)) {
            try (InputStream input = Files.newInputStream(SAVE_FILE)) {
                //properties.load(input);
            } catch (IOException e) {
                //  log.log(Level.SEVERE, "Failed to load settings", e);
            }

        }
    }

}
