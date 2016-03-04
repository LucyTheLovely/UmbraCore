package com.umbrafactions.umbracore.shortcuts;

import com.umbrafactions.umbracore.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerScoreboard {
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();
    private Objective playerInfoObjective = board.registerNewObjective("playerinfo", "dummy");

    private Score prefix = null;
    private Score money = null;

    public void setPlayerInfoScoreboard(Player p) {
        String prefixText = Core.colorize(Core.chat.getPlayerPrefix(p));
        prefixText = prefixText.replace("[", "").replace("]", "");
        playerInfoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        playerInfoObjective.setDisplayName(ChatColor.GRAY + "Info for " + ChatColor.YELLOW + p.getName());
        money = playerInfoObjective.getScore(
                ChatColor.LIGHT_PURPLE + "Balance: " + ChatColor.GREEN + Core.econ.format(Core.econ.getBalance(p))
        );
        prefix = playerInfoObjective.getScore(ChatColor.LIGHT_PURPLE + "Rank: " + prefixText);
        money.setScore(1);
        prefix.setScore(2);
        p.setScoreboard(board);
    }

    public void updatePlayerInfoScoreboard(Player p) {
        Scoreboard board = manager.getNewScoreboard();
        Objective playerInfoObjective = board.registerNewObjective("playerinfo", "dummy");
        String prefixText = Core.colorize(Core.chat.getPlayerPrefix(p));
        prefixText = prefixText.replace("[", "").replace("]", "");
        playerInfoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        playerInfoObjective.setDisplayName(ChatColor.GRAY + "Info for " + ChatColor.YELLOW + p.getName());
        money = playerInfoObjective.getScore(
                ChatColor.LIGHT_PURPLE + "Balance: " + ChatColor.GREEN + Core.econ.format(Core.econ.getBalance(p))
        );
        prefix = playerInfoObjective.getScore(ChatColor.LIGHT_PURPLE + "Rank: " + prefixText);
        money.setScore(1);
        prefix.setScore(2);
        p.setScoreboard(board);
    }
}
