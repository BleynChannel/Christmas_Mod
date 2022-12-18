package com.civilization.christmas;

import com.civilization.christmas.core.Utils;
import com.civilization.christmas.modules.christmas_mood.ChristmasMood;
import com.civilization.christmas.modules.daily_gifts.DailyGifts;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Christmas.MODID)
public class Christmas {
    public static final String MODID = "christmas";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ArrayList<Object> MODULES = new ArrayList<Object>();

    ///////////////////////////

    public Christmas() {
        LOGGER.info("Register modules");

        MODULES.add(new DailyGifts());
        MODULES.add(new ChristmasMood());
    }
}
