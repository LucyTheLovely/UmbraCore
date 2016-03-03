package com.umbrafactions.umbracore.runnables;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.SQLInterface;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckPlayerHours implements Runnable {
    private final Core plugin;
    private final SQLInterface sql = new SQLInterface();
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();

    public CheckPlayerHours(Core plugin) { this.plugin = plugin; }

    @Override
    public void run() {
        // Below is basically a copy-paste of PlayerJoinListener and PlayerQuitListener. I'm so bad.
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            String uuid = p.getUniqueId().toString();
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
                                "UPDATE `time_log` SET `Seconds`=\"" + (((System.currentTimeMillis() / 1000) - plugin.getConfig().getLong(uuid + ".timestamp")) + newQuery.getLong("Seconds")) + "\" WHERE `UUID`=\"" + uuid + "\""
                        );
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                } finally {
                    sql.cleanSql();
                }
                ResultSet newNewQuery = sql.queryDatabase("SELECT * FROM `time_log` WHERE `UUID` = \"" + uuid + "\"");
                double seconds = 0;
                try {
                    newNewQuery.beforeFirst();
                    while (newNewQuery.next()) {
                        seconds = newNewQuery.getDouble("Seconds")/60/60;
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
                int hours = (int) seconds;
                Objective hourObjective = board.registerNewObjective("test", "dummy");
                hourObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
                hourObjective.setDisplayName(ChatColor.GRAY + "Info for " + ChatColor.GREEN + p.getName());
                Score time = hourObjective.getScore(ChatColor.LIGHT_PURPLE + "Hours:");
                time.setScore(hours);
                p.setScoreboard(board);
                sql.cleanSql();
            }
        }
    }
}
