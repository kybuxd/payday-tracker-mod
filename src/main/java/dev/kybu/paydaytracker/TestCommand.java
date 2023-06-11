package dev.kybu.paydaytracker;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class TestCommand extends CommandBase implements ICommand {

    @Override
    public String getName() {
        return "testpayday";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/testpayday";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("Neuer Betrag: 106553$ (+1973$)"));
    }
}
