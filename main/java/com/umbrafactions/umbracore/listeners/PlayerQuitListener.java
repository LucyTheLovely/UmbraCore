package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.SQLInterface;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PlayerQuitListener implements Listener {
    private final SQLInterface sql = new SQLInterface();
    private Core plugin = null;

    public PlayerQuitListener(Core plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        boolean entryExists = false;
        ResultSet query = sql.queryDatabase("SELECT * FROM `time_log` WHERE `UUID` = \"" + uuid + "\"");
        try {
            if (query.next()) {
                entryExists = true;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        sql.cleanSql();
        if (entryExists) {
            ResultSet newQuery = sql.queryDatabase("SELECT * FROM `time_log` WHERE `UUID` = \"" + uuid + "\"");
            try {
                while (newQuery.next()) {
                    sql.softUpdateDatabase(
                            "UPDATE `time_log` SET `Seconds`=\"" + (((System.currentTimeMillis()/1000) - plugin.getConfig().getLong(uuid + ".timestamp")) + newQuery.getLong("Seconds")) + "\" WHERE `UUID`=\"" + uuid + "\""
                    );
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                sql.cleanSql();
                plugin.getConfig().set(uuid + ".timestamp", null);
                plugin.getConfig().set(uuid, null);
                plugin.saveConfig();
            }
        } else {
            sql.cleanSql();
        }
    }
}
