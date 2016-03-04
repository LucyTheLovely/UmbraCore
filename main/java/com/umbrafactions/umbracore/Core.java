package com.umbrafactions.umbracore;

import com.umbrafactions.umbracore.commands.Report;
import com.umbrafactions.umbracore.listeners.LaunchPadListener;
import com.umbrafactions.umbracore.listeners.PlayerDeathListener;
import com.umbrafactions.umbracore.listeners.PlayerJoinListener;
import com.umbrafactions.umbracore.listeners.PlayerQuitListener;
import com.umbrafactions.umbracore.runnables.UpdatePlayerScoreboard;
import com.umbrafactions.umbracore.shortcuts.SQLInterface;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Core extends JavaPlugin {
    private final SQLInterface sql = new SQLInterface();
    private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new LaunchPadListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);

        this.getCommand("report").setExecutor(new Report(this));

        scheduler.scheduleSyncRepeatingTask(this, new UpdatePlayerScoreboard(this), 0L, 20*5L);
    }

    @Override
    public void onDisable() {
        sql.cleanSql();
    }

    // Thanks bukkit forums! https://bukkit.org/threads/solved-parse-for-color-codes.25965/#post-467467
    public static String colorize(String s){
        if(s == null) return null;
        return s.replaceAll("&([0-9a-f])", "\u00A7$1");
    }

    // This code isn't mine, copy-pasted from the example @ https://github.com/MilkBowl/VaultAPI
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    // End of copy-paste
}
