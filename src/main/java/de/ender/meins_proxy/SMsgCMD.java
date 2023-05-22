package de.ender.meins_proxy;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class SMsgCMD extends Command {
    public SMsgCMD() {
        super("smsg");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = Main.proxy().getPlayer(args[0]);
        player.sendMessage("sMSG: §b[§r"+sender.getName()+"§b]§r: " + String.join(" ",Arrays.copyOfRange(args, 1, args.length)));
    }
}
