package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.SQLInterface;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PlayerJoinListener implements Listener {
    private final SQLInterface sql = new SQLInterface();
    private Core plugin = null;
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();

    public PlayerJoinListener(Core plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
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
        if (entryExists) {
            double seconds = 0;
            try {
                query.beforeFirst();
                while (query.next()) {
                    seconds = query.getDouble("Seconds")/60/60;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            int hours = (int) seconds;
            Objective hourObjective = board.registerNewObjective("test", "dummy");
            hourObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
            hourObjective.setDisplayName(ChatColor.GRAY + "Info for " + ChatColor.GREEN + player.getName());
            Score time = hourObjective.getScore(ChatColor.LIGHT_PURPLE + "Hours:");
            time.setScore(hours);
            player.setScoreboard(board);
            sql.cleanSql();
        } else {
            sql.updateDatabase(
                    "INSERT INTO `time_log`(`UUID`, `Seconds`) VALUES (\"" + uuid + "\", \"0\")"
            );
        }
        plugin.getConfig().set(uuid + ".timestamp", System.currentTimeMillis()/1000);
        plugin.saveConfig();
    }
}
