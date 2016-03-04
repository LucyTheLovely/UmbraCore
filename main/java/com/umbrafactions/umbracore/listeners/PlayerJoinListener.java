package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.shortcuts.PlayerScoreboard;
import com.umbrafactions.umbracore.SQLInterface;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

//import java.sql.ResultSet;
//import java.sql.SQLException;

public final class PlayerJoinListener implements Listener {
    private Core plugin;
    public PlayerJoinListener(Core plugin) { this.plugin = plugin; }

//    private final SQLInterface sql = new SQLInterface();
    private PlayerScoreboard playerScoreboard = new PlayerScoreboard();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerScoreboard.setPlayerInfoScoreboard(player);
        plugin.getLogger().info("Player " + player.getName() + " has prefix " + Core.chat.getPlayerPrefix(player));
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
//        if (entryExists) {
//            double seconds = 0;
//            try {
//                query.beforeFirst();
//                while (query.next()) {
//                    seconds = query.getDouble("Seconds")/60/60;
//                }
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//            int hours = (int) seconds;
//            sql.cleanSql();
//        } else {
//            sql.updateDatabase(
//                    "INSERT INTO `time_log`(`UUID`, `Seconds`) VALUES (\"" + uuid + "\", \"0\")"
//            );
//        }
    }
}
