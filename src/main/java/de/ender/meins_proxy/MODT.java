package de.ender.meins_proxy;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MODT implements Listener {

    HashMap<String,String> ipInfos = new HashMap<>();
    long ipTime = System.currentTimeMillis();


    @EventHandler

    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        String name = e.getConnection().getName();

        List<String> d = Arrays.asList(
                "§3E§2A §fNet",
                "§a§k------§3§k------",
                "§2§lYOUR AD HERE"
        );

        Date currentDate = new Date(System.currentTimeMillis());
        int date = currentDate.getDate();
        int month = currentDate.getMonth();

        if(date == 24 && month == Calendar.DECEMBER) d.add(multicolorString("Merry Christmas", "§4§f§c§f", ""));
        if((date == 31 && month == Calendar.DECEMBER) || (date == 1 && month == Calendar.JANUARY)) d.add(multicolorString("Happy new year", "§2", " "));
        if(name != null) d.add("§4Hello "+name);

        ping.setDescription(d.get(new Random().nextInt(d.size()))+"§r");

        List<ServerPing.PlayerInfo> playerInfos = new ArrayList<>();
        for (ProxiedPlayer player:
             Main.proxy().getPlayers()) {
            String cPlayerName = player.getName();
            UUID cPlayerUUID = player.getUniqueId();

            playerInfos.add(new ServerPing.PlayerInfo(cPlayerName,cPlayerUUID));
        }
        ServerPing.PlayerInfo[] playerInfosArray = playerInfos.toArray(new ServerPing.PlayerInfo[0]);
        ping.setPlayers(new ServerPing.Players(ping.getPlayers().getMax(),ping.getPlayers().getOnline(),playerInfosArray));

        if(!e.getConnection().isOnlineMode()){
            ping.setDescription(multicolorString("You shall not pass!","§4§c§6§e§2§a§b§3§1§9§d§5§f§7§8§0",""));
        }

        e.setResponse(ping);

        String address = e.getConnection().getSocketAddress().toString();
        String ip = address.substring(1, e.getConnection().getSocketAddress().toString().indexOf(":"));
        Main.proxy().getScheduler().schedule(Main.getInstance(), () -> {
            if(System.currentTimeMillis()-ipTime<=3600000 && ipInfos.containsKey(ip)) {
                Main.proxy().getLogger().log(Level.INFO,"old: "+ipInfos.get(ip));
            } else if(System.currentTimeMillis()-ipTime>=3600000) {
                ipTime = System.currentTimeMillis();
                ipInfos.clear();
                ipInfo(ip);
            } else {
                //Main.proxy().getLogger().log(Level.INFO, ip);
                ipInfo(ip);
            }
        }, 1, TimeUnit.MILLISECONDS);
    }

    private static String multicolorString(String string, String colorSequence,String regex) {
        String[] colors = colorSequence.split("§");
        String[] letters = string.split(regex);
        ArrayList<String> coloredString = new ArrayList<>();
        for (int i = 0; i < letters.length; i++) {
            String color = colors[i % colors.length];
            String letter = letters[i];
            coloredString.add("§"+color+letter);
        }
        return String.join("", coloredString);
    }
    private void ipInfo(String address) {
        Logger logger = Main.proxy().getLogger();
        try {
            String country = null;
            String city = null;

            InputStream stream = new URL("https://ipinfo.io/"+address+"?token=00a34cb8e96173").openStream();

            Scanner scanner = new Scanner(stream);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("\"country\": \"")) {
                    country = line.substring(line.indexOf("\"country\": \"")+"\"country\": \"".split("").length, line.indexOf("\","));
                    break;
                } else if(line.contains("\"city\": \"")) {
                    city = line.substring(line.indexOf("\"city\": \"")+"\"city\": \"".split("").length, line.indexOf("\","));
                }
            }
            scanner.close();

            this.ipInfos.put(address,"address: "+address+", country: "+country+", city: "+city);

            logger.log(Level.INFO,"new: address: "+address+", country: "+country+", city: "+city);
        } catch (IOException e) {
            logger.log(Level.WARNING,"Something went wrong tyring to get geolocation!");
        }
    }
}
