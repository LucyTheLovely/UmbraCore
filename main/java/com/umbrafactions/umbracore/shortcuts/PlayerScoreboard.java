package com.umbrafactions.umbracore.shortcuts;

import com.umbrafactions.umbracore.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerScoreboard {
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();
    private Objective playerInfoObjective = board.registerNewObjective("playerinfo", "dummy");
    private Objective playerPrefix = board.registerNewObjective("tablist", "dummy");

    private final SQLInterface sql = new SQLInterface();

    private Score localPrefix = null;
    private Score money = null;
    private Score kills = null;
    private Score deaths = null;

    private int deathCount = 0;
    private int killCount = 0;

    @SuppressWarnings("deprecation")
    public void setPlayerInfoScoreboard(Player p) {
        ResultSet results = sql.queryDatabase(
                "SELECT * FROM player_deaths WHERE `victim` = \"" + p.getUniqueId().toString() + "\""
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
                    deathCount = results.getInt("deaths");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else {
            sql.updateDatabase(String.format(
                    "INSERT INTO `player_deaths`(`victim`,`deaths`) VALUES (\"%s\",\"0\")",
                    p.getUniqueId().toString())
            );
        }

        results = sql.queryDatabase(
                "SELECT * FROM player_kills WHERE `killer` = \"" + p.getUniqueId().toString() + "\""
        );
        entryExists = false;
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
                    killCount = results.getInt("kills");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else {
            sql.updateDatabase(String.format(
                    "INSERT INTO `player_kills`(`killer`,`kills`) VALUES (\"%s\",\"0\")",
                    p.getUniqueId().toString())
            );
        }

        String prefixText = Core.colorize(Core.chat.getPlayerPrefix(p)).replace("[", "").replace("]", "");

        playerPrefix.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        playerInfoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        playerInfoObjective.setDisplayName(ChatColor.LIGHT_PURPLE + "Umbra" + ChatColor.GRAY + ChatColor.BOLD + "Console");
        money = playerInfoObjective.getScore(
                ChatColor.GRAY + "Balance: " + ChatColor.LIGHT_PURPLE + Core.econ.format(Core.econ.getBalance(p))
        );
        localPrefix = playerInfoObjective.getScore(ChatColor.GRAY + "Rank: " + prefixText);
        deaths = playerInfoObjective.getScore(ChatColor.GRAY + "Deaths: " + ChatColor.LIGHT_PURPLE + deathCount);
        kills = playerInfoObjective.getScore(ChatColor.GRAY + "Kills: " + ChatColor.LIGHT_PURPLE + killCount);

        kills.setScore(5);
        deaths.setScore(4);
        money.setScore(3);
        // 2: Killstreak
        localPrefix.setScore(1);

        p.setScoreboard(board);
    }

    public void updatePlayerInfoScoreboard(Player p) {
        ResultSet results = sql.queryDatabase(
                "SELECT * FROM player_deaths WHERE `victim` = \"" + p.getUniqueId().toString() + "\""
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
                    deathCount = results.getInt("deaths");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else {
            sql.updateDatabase(String.format(
                    "INSERT INTO `player_deaths`(`victim`,`deaths`) VALUES (\"%s\",\"0\")",
                    p.getUniqueId().toString())
            );
        }

        results = sql.queryDatabase(
                "SELECT * FROM player_kills WHERE `killer` = \"" + p.getUniqueId().toString() + "\""
        );
        entryExists = false;
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
                    killCount = results.getInt("kills");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else {
            sql.updateDatabase(String.format(
                    "INSERT INTO `player_kills`(`killer`,`kills`) VALUES (\"%s\",\"0\")",
                    p.getUniqueId().toString())
            );
        }

        Scoreboard board = manager.getNewScoreboard();

        Objective playerInfoObjective = board.registerNewObjective("playerinfo", "dummy");
        playerInfoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String prefixText = Core.colorize(Core.chat.getPlayerPrefix(p)).replace("[", "").replace("]", "");
        playerInfoObjective.setDisplayName(ChatColor.LIGHT_PURPLE + "Umbra" + ChatColor.GRAY + ChatColor.BOLD + "Console");

        money = playerInfoObjective.getScore(
                ChatColor.GRAY + "Balance: " + ChatColor.LIGHT_PURPLE + Core.econ.format(Core.econ.getBalance(p))
        );
        localPrefix = playerInfoObjective.getScore(ChatColor.GRAY + "Rank: " + prefixText);
        deaths = playerInfoObjective.getScore(ChatColor.GRAY + "Deaths: " + ChatColor.LIGHT_PURPLE + deathCount);
        kills = playerInfoObjective.getScore(ChatColor.GRAY + "Kills: " + ChatColor.LIGHT_PURPLE + killCount);

        kills.setScore(5);
        deaths.setScore(4);
        money.setScore(3);
        // 2: Killstreak
        localPrefix.setScore(1);

        p.setScoreboard(board);
    }
}
