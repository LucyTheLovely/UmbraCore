package com.umbrafactions.umbracore;

import com.umbrafactions.umbracore.listeners.PlayerJoinListener;

import com.umbrafactions.umbracore.listeners.PlayerQuitListener;
import com.umbrafactions.umbracore.runnables.CheckPlayerHours;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Core extends JavaPlugin {
    private final SQLInterface sql = new SQLInterface();
    private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        scheduler.scheduleSyncRepeatingTask(this, new CheckPlayerHours(this), 20*60*60L, 20*60*60L);
    }

    @Override
    public void onDisable() {
        sql.cleanSql();
    }
}
