package dev.kybu.paydaytracker;

import akka.japi.pf.Match;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ChatListener {

    private static final Pattern PATTERN = Pattern.compile("Neuer Betrag: (?<money>.*?)\\$ \\(\\+(?<add>.*?)\\$\\)");
    private static final Pattern STATS_PATTERN = Pattern.compile(" - Geld: (?<money>.*?)\\$");
    private static PayDay ACTIVE_PAYDAY = null;

    @SubscribeEvent
    public static void onChatMessageReceive(final ClientChatReceivedEvent event) throws IOException {
        final String message = StringUtils.stripControlCodes(event.getMessage().getFormattedText());

        final Matcher statsMatcher = STATS_PATTERN.matcher(message);
        if(statsMatcher.find() && ACTIVE_PAYDAY != null) {
            final long moneyOnHand = Long.parseLong(statsMatcher.group("money"));
            ACTIVE_PAYDAY.setMoneyOnHand(moneyOnHand);
            PayDayTrackerMod.addPayDay(ACTIVE_PAYDAY);
            ACTIVE_PAYDAY = null;
        }

        final Matcher matcher = PATTERN.matcher(message);
        if(matcher.find()) {
            final String newMoney = matcher.group("money");
            final String add = matcher.group("add");

            ACTIVE_PAYDAY = new PayDay(Long.parseLong(newMoney), Long.parseLong(add));
            Minecraft.getMinecraft().player.sendChatMessage("/stats");
        }
    }

}
