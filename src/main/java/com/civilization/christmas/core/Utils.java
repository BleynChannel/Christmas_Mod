package com.civilization.christmas.core;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String DIRECTORY_PATH = "christmas";
    public static final Path PLAYERS_DATE_GIFTS_FILE = Paths.get(DIRECTORY_PATH, "players_date_gifts.json");
    public static final Path GIFTS_FILE = Paths.get(DIRECTORY_PATH, "gifts.json");

    private static boolean makeChristmasDirectory() {
        Path dir = Paths.get(Utils.DIRECTORY_PATH);

        if (!Files.isDirectory(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                LOGGER.error("Make dir error: " + e.getMessage());
                return false;
            }
        }

        return true;
    }

    public static boolean makeChristmasFile(Path path) {
        if (makeChristmasDirectory() && !Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                LOGGER.error("Error for file" + path.getFileName() + ": " + e.getMessage());
                return false;
            }
        }

        return true;
    }
}
