package de.ender.meins_proxy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinLeaveListener implements Listener {
    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxyServer.getInstance().broadcast(
                new TextComponent("["+ChatColor.GREEN+"+"+ChatColor.WHITE+ "] "+event.getPlayer().getName())
        );
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        ProxyServer.getInstance().broadcast(
                new TextComponent("["+ChatColor.RED+"-"+ChatColor.WHITE+ "] "+event.getPlayer().getName())
        );
    }
}
