package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.shortcuts.SQLInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerKillListener implements Listener {
    private Core plugin;
    private final SQLInterface sql = new SQLInterface();

    public PlayerKillListener(Core plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKill(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            ResultSet results = sql.queryDatabase(
                    "SELECT * FROM player_kills WHERE `killer`=\"" + damager.getUniqueId().toString() + "\""
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
                        final int oldKills = results.getInt("kills");
                        sql.updateDatabase(String.format(
                                "UPDATE `player_kills` SET `kills`=\"%d\" WHERE `killer`=\"%s\"",
                                oldKills + 1,
                                damager.getUniqueId().toString()));
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            } else {
                sql.updateDatabase(String.format(
                        "INSERT INTO `player_kills`(`killer`,`kills`) VALUES (\"%s\",\"1\")",
                        damager.getUniqueId().toString())
                );
            }
        }
    }
}
