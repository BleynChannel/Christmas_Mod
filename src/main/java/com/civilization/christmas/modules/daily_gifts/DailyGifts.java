package com.civilization.christmas.modules.daily_gifts;

import com.civilization.christmas.core.Utils;
import com.civilization.christmas.modules.daily_gifts.core.Bundle;
import com.civilization.christmas.modules.daily_gifts.core.PlayerGiftModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.civilization.christmas.core.Utils.makeChristmasFile;

// Ежедневные подарки за вход в игру
public class DailyGifts {
    public static DailyGifts INSTANCE;

    private static final Logger LOGGER = LogUtils.getLogger();

    ///////////////////////////

    @SerializedName("players")
    public ArrayList<PlayerGiftModel> players;

    @SerializedName("bundle")
    public ArrayList<Bundle> bundles;

    ///////////////////////////

    private String fileToString(Path path) {
        try {
            InputStream file = Files.newInputStream(path, StandardOpenOption.READ);
            InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            reader.close();
            file.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            LOGGER.error("Error for file" + path.getFileName() + ": " + e.getMessage());
        }

        return "";
    }

    private <T> void classToFile(Path path, T t) {
        try {
            OutputStream file = Files.newOutputStream(path, StandardOpenOption.WRITE);
            OutputStreamWriter writer = new OutputStreamWriter(file, StandardCharsets.UTF_8);
            writer.write(new Gson().toJson(t));
            writer.flush();
            file.flush();
        } catch (IOException e) {
            LOGGER.error("Error for file" + path.getFileName() + ": " + e.getMessage());
        }
    }

    private <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        if (s != null && !s.isEmpty()) {
            return Arrays.asList(new Gson().fromJson(s, clazz));
        }

        return new ArrayList<T>();
    }

    public void loadGifts() {
        //Достаем из файла список наборов и подарков
        bundles = new ArrayList<Bundle>();

        if (makeChristmasFile(Utils.GIFTS_FILE)) {
            bundles = new ArrayList<Bundle>(stringToArray(fileToString(Utils.GIFTS_FILE), Bundle[].class));
        }
    }

    public void loadPlayersDate() {
        //Достаем из файла данные о всех игроках
        players = new ArrayList<PlayerGiftModel>();

        if (makeChristmasFile(Utils.PLAYERS_DATE_GIFTS_FILE)) {
            players = new ArrayList<PlayerGiftModel>(stringToArray(fileToString(Utils.PLAYERS_DATE_GIFTS_FILE), PlayerGiftModel[].class));
        }
    }

    public void savePlayersDate() {
        //Сохраняем данные по всем игрокам
        makeChristmasFile(Utils.PLAYERS_DATE_GIFTS_FILE);
        classToFile(Utils.PLAYERS_DATE_GIFTS_FILE, players);
    }

    ///////////////////////////

    public DailyGifts() {
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(DailyGiftsSubscriber.class);
    }
}
