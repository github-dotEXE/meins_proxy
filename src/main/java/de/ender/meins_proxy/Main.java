package de.ender.meins_proxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class Main extends Plugin {

    private static ProxyServer proxy;
    private static Main instance;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerCommand(this,new LobbyCMD());
        pluginManager.registerCommand(this,new SMsgCMD());
        pluginManager.registerListener(this,new MODT());
        pluginManager.registerListener(this,new JoinLeaveListener());

        proxy = this.getProxy();
        instance =  this;
    }

    @Override
    public void onDisable() {
    }
    public static ProxyServer proxy(){
        return proxy;
    }
    public static Main getInstance(){return instance;}
}
