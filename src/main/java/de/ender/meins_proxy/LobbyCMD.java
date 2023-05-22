package de.ender.meins_proxy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;
import java.util.stream.Collectors;

public class LobbyCMD extends Command implements TabExecutor {
    public LobbyCMD() {
        super("lobby");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.RED).create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServer().getInfo().getName().equalsIgnoreCase("lobby")) {
            player.sendMessage(new ComponentBuilder("You are already connected to the Lobby!").color(ChatColor.RED).create());
            return;
        }
        ServerInfo target = ProxyServer.getInstance().getServerInfo("lobby");
        player.connect(target);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length != 1) {
            return Collections.emptyList();
        }

        return ProxyServer.getInstance().getServers().values().stream()
                .map(ServerInfo::getName)
                .filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}
