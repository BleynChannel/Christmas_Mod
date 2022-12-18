package com.civilization.christmas.modules.daily_gifts;

import com.civilization.christmas.modules.daily_gifts.command.GetGiftCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DailyGiftsSubscriber {
    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        GetGiftCommand.register(commandDispatcher);
    }

    @SubscribeEvent
    public static void onStartingServer(ServerStartingEvent event) {
        DailyGifts.INSTANCE.loadGifts();
        DailyGifts.INSTANCE.loadPlayersDate();
    }

    @SubscribeEvent
    public static void onStoppingServer(ServerStoppingEvent event) {
        DailyGifts.INSTANCE.savePlayersDate();
    }
}
