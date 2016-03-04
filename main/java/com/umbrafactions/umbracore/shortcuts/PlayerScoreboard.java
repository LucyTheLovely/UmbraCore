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
    private Objective prefixObjective = board.registerNewObjective("playerprefix", "dummy");

    private Score prefix = null;
    private Score nameTagPrefix = null;
    private Score money = null;
    private Score dividerOne = null;

    public void setPlayerInfoScoreboard(Player p) {
        String prefixText = Core.colorize(Core.chat.getPlayerPrefix(p));
        prefixText = prefixText.replace("[", "").replace("]", "");
        prefixObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        playerInfoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        playerInfoObjective.setDisplayName(ChatColor.GRAY + "Info for " + ChatColor.YELLOW + p.getName());
        money = playerInfoObjective.getScore(
                ChatColor.LIGHT_PURPLE + "Balance: " + ChatColor.GREEN + Core.econ.format(Core.econ.getBalance(p))
        );
        prefix = playerInfoObjective.getScore(ChatColor.LIGHT_PURPLE + "Rank: " + prefixText);
        nameTagPrefix = prefixObjective.getScore(prefixText);
        dividerOne = playerInfoObjective.getScore("");
        money.setScore(1);
        prefix.setScore(2);
        dividerOne.setScore(3);
        p.setScoreboard(board);
    }

    public void updatePlayerInfoScoreboard(Player p) {
        Scoreboard board = manager.getNewScoreboard();
        Objective playerInfoObjective = board.registerNewObjective("playerinfo", "dummy");
        Objective prefixObjective = board.registerNewObjective("playerprefix", "dummy");
        String prefixText = Core.colorize(Core.chat.getPlayerPrefix(p));
        prefixText = prefixText.replace("[", "").replace("]", "");
        prefixObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        playerInfoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        playerInfoObjective.setDisplayName(ChatColor.GRAY + "Info for " + ChatColor.YELLOW + p.getName());
        money = playerInfoObjective.getScore(
                ChatColor.LIGHT_PURPLE + "Balance: " + ChatColor.GREEN + Core.econ.format(Core.econ.getBalance(p))
        );
        prefix = playerInfoObjective.getScore(ChatColor.LIGHT_PURPLE + "Rank: " + prefixText);
        nameTagPrefix = prefixObjective.getScore(prefixText);
        dividerOne = playerInfoObjective.getScore("");
        money.setScore(1);
        prefix.setScore(2);
        dividerOne.setScore(3);
        p.setScoreboard(board);
    }
}
