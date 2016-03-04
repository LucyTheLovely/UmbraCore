package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;

import com.umbrafactions.umbracore.shortcuts.SQLInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDeathListener implements Listener {
    private Core plugin;
    private final SQLInterface sql = new SQLInterface();

    public PlayerDeathListener(Core plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        ResultSet results = sql.queryDatabase(
                "SELECT * FROM player_deaths WHERE `victim`=\"" + victim.getUniqueId().toString() + "\""
        );
        boolean entryExists = false;
        try {
            if (results.next()) {
                entryExists = true;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        if (entryExists) {
            try {
                results.beforeFirst();
                while (results.next()) {
                    final int oldDeaths = results.getInt("deaths");
                    sql.updateDatabase(String.format(
                            "UPDATE `player_deaths` SET `deaths`=\"%d\" WHERE `victim`=\"%s\"",
                            oldDeaths + 1,
                            victim.getUniqueId().toString()));
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else {
            sql.updateDatabase(String.format(
                    "INSERT INTO `player_deaths`(`victim`,`deaths`) VALUES (\"%s\",\"1\")",
                    victim.getUniqueId().toString())
            );
        }
    }
}
