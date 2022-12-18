package com.civilization.christmas.modules.daily_gifts.command;

import com.civilization.christmas.modules.daily_gifts.DailyGifts;
import com.civilization.christmas.modules.daily_gifts.core.*;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Map;

public class GetGiftCommand {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command =
                Commands.literal("nygift")
                        .executes(GetGiftCommand::getGift);

        dispatcher.register(command);
    }

    private static int getGift(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
        Player player = commandContext.getSource().getPlayerOrException();

        //Ищем игрока в списке игроков
        PlayerGiftModel playerGift = null;
        for (var plGift : DailyGifts.INSTANCE.players)
            if (plGift.uuid.getLeastSignificantBits() == player.getUUID().getLeastSignificantBits() &&
                    plGift.uuid.getMostSignificantBits() == player.getUUID().getMostSignificantBits())
                playerGift = plGift;

        boolean isCanGive = true;
        long timeLeft = 0L;

        if (playerGift == null) {
            playerGift = new PlayerGiftModel(player.getUUID(), new Date(), 0, 1.f);
            DailyGifts.INSTANCE.players.add(playerGift);
        } else {
            long range = new Date().getTime() - playerGift.lastPick.getTime();
            if (range < 86400000L) {
//                isCanGive = false;
                timeLeft = 86400000L - range;
            }
        }

        MinecraftServer server = player.getServer();
        String playerName = player.getName().getContents();

        if (isCanGive) {
            //Получаем подарок с определенным шансом игрока
            Map.Entry<Bundle, Gift> bundleAndGift = GiveGift.getGift(playerGift.chance);
            Bundle bundle = bundleAndGift.getKey();
            Gift gift = bundleAndGift.getValue();

            playerGift.lastPick = new Date();
            playerGift.countPick++;

            String give = "give " + playerName + " " + gift.id + gift.nbt + " " + gift.amount;
            server.getCommands().performCommand(server.createCommandSourceStack(), give);

            String giftName = gift.name;
            if (gift.amount > 1) giftName += " x" + gift.amount;

            String message = "tellraw " + playerName + " [\"\",{\"text\":\"Вы получили подарок из набора \\\"\"},{\"text\":\"" + bundle.name + "\",\"bold\":true,\"italic\":true,\"color\":\"" + bundle.color + "\"},{\"text\":\"\\\": \"},{\"text\":\"" + gift.name + "\",\"bold\":true,\"color\":\"" + (gift.type == ItemChance.ItemType.COMMON ? "dark_aqua" : "gold") + "\"},{\"text\":\" \"}]";
            server.getCommands().performCommand(server.createCommandSourceStack(), message);

            LOGGER.info("Player " + playerName + " received a gift:" + giftName);

            DailyGifts.INSTANCE.savePlayersDate();
        } else {
            //Если он не может получить подарок
            String time = getTimeString(timeLeft);

            String message = "tellraw " + playerName + " [\"\",{\"text\":\"Вы сможете получить подарок через \"},{\"text\":\"" + time + "\",\"color\":\"#FFB92C\"}]";

            server.getCommands().performCommand(server.createCommandSourceStack(), message);
            LOGGER.info("Player " + playerName + " tried to get a gift");
        }

        return Command.SINGLE_SUCCESS;
    }

    private static String addNull(int time) {
        return time < 10 ? ("0" + time) : String.valueOf(time);
    }

    private static String getTimeString(long time) {
        int hour = (int)(time / (60L * 60L * 1000L));
        time -= hour * 60L * 60L * 1000L;
        int minute = (int)(time / (60L * 1000L));
        time -= minute * 60L * 1000L;
        int second = (int)(time / 1000L);

        return addNull(hour) + ":" + addNull(minute) + ":" + addNull(second);
    }
}
