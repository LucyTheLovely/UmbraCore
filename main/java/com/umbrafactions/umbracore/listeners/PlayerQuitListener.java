package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.SQLInterface;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.Instant;

public final class PlayerQuitListener implements Listener {
    private Core plugin;
    public PlayerQuitListener(Core plugin) { this.plugin = plugin; }

    private final SQLInterface sql = new SQLInterface();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
//        Player player = event.getPlayer();
//        String uuid = player.getUniqueId().toString();
//        boolean entryExists = false;
//        ResultSet query = sql.queryDatabase("SELECT * FROM `time_log` WHERE `UUID` = \"" + uuid + "\"");
//        try {
//            if (query.next()) {
//                entryExists = true;
//            }
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        sql.cleanSql();
//        if (entryExists) {
//            ResultSet newQuery = sql.queryDatabase("SELECT * FROM `time_log` WHERE `UUID` = \"" + uuid + "\"");
//            try {
//                while (newQuery.next()) {
//                    sql.softUpdateDatabase(
//                            "UPDATE `time_log` SET `Seconds`=\"" + (Instant.now().getEpochSecond()) + "\" WHERE `UUID`=\"" + uuid + "\""
//                    );
//                }
//            } catch (SQLException se) {
//                se.printStackTrace();
//            } finally {
//                sql.cleanSql();
//                plugin.getConfig().set(uuid + ".timestamp", null);
//                plugin.getConfig().set(uuid, null);
//                plugin.saveConfig();
//            }
//        } else {
//            sql.cleanSql();
//        }
    }
}
