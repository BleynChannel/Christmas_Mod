package com.civilization.christmas;

import com.civilization.christmas.core.Module;
import com.civilization.christmas.modules.christmas_event.ChristmasEvent;
import com.civilization.christmas.modules.christmas_mood.ChristmasMood;
import com.civilization.christmas.modules.daily_gifts.DailyGifts;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Christmas.MODID)
public class Christmas {
    public static final String MODID = "christmas";
    private static final String SERVERIP = "usualworld.ru/136.243.113.69:25565"; //civilization.20tps.ru
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ArrayList<Module> MODULES = new ArrayList<Module>();

    ///////////////////////////

    private void register() {
        LOGGER.info("Register modules");

        MODULES.add(new ChristmasEvent());
        MODULES.add(new DailyGifts());
        MODULES.add(new ChristmasMood());

        MODULES.forEach(module -> module.register());
    }

    ///////////////////////////

    public Christmas() {
        register();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(
            modid = Christmas.MODID,
            bus = Mod.EventBusSubscriber.Bus.FORGE,
            value = Dist.CLIENT)
    private static class ClientEventSubscriber {
        @SubscribeEvent
        public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
            MODULES.forEach(module -> module.loadCommon());

            if (!event.getConnection().isMemoryConnection() &&
                    event.getConnection().getRemoteAddress().toString().equals(SERVERIP)) {
                MODULES.forEach(module -> module.loadClient());
            }
        }

        @SubscribeEvent
        public static void onPlayerLoggedOut(ClientPlayerNetworkEvent.LoggedOutEvent event) {
            MODULES.forEach(module -> module.stopCommon());
            MODULES.forEach(module -> module.stopClient());
        }
    }

    @Mod.EventBusSubscriber(
            modid = Christmas.MODID,
            bus = Mod.EventBusSubscriber.Bus.FORGE,
            value = Dist.DEDICATED_SERVER)
    private class ServerEventSubscriber {
        @SubscribeEvent
        public static void onServerStarting(ServerStartingEvent event) {
            MODULES.forEach(module -> module.loadCommon());
            MODULES.forEach(module -> module.loadServer());
        }

        @SubscribeEvent
        public static void onServerStopping(ServerStoppingEvent event) {
            MODULES.forEach(module -> module.stopCommon());
            MODULES.forEach(module -> module.stopServer());
        }
    }
}
