package dev.kybu.paydaytracker;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;

@Mod(modid = "paydaytracker", name = "Kybu's PayDay Tracker", version = "1")
public class PayDayTrackerMod {

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss 'Uhr'");
    private static File MOD_DIRECTORY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MOD_DIRECTORY = new File(Minecraft.getMinecraft().mcDataDir, "payday-tracker");
        MOD_DIRECTORY.mkdirs();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new TestCommand());
    }

    public static void addPayDay(final PayDay payDay) {
        final long timestamp = System.currentTimeMillis();
        final File file = new File(MOD_DIRECTORY, "paydays.txt");
        final StringBuilder stringBuilder = new StringBuilder("------------[ PayDay vom " + DATE_FORMAT.format(timestamp) + " ]------------\n");
        stringBuilder.append("Alter Kontostand: ").append(payDay.getNewMoney() - payDay.getAddedMoney()).append("$\n");
        stringBuilder.append("Geld auf der Hand: ").append(payDay.getMoneyOnHand()).append("$\n");
        stringBuilder.append("Wert vom PayDay: ").append(payDay.getAddedMoney()).append("$\n");
        stringBuilder.append("Neuer Kontostand: ").append(payDay.getNewMoney()).append("$\n");
        stringBuilder.append("Gesamt (Konto + Hand): ").append(payDay.getNewMoney() + payDay.getMoneyOnHand()).append("$\n\n");

        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            Files.write(Paths.get(file.toURI()), stringBuilder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
