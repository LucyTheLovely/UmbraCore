package com.umbrafactions.umbracore.runnables;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.shortcuts.PlayerScoreboard;

import org.bukkit.entity.Player;

public class UpdatePlayerScoreboard implements Runnable {
    private Core plugin;
    public UpdatePlayerScoreboard(Core plugin) { this.plugin = plugin; }

    private PlayerScoreboard playerScoreboard = new PlayerScoreboard();

    @Override
    public void run() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            playerScoreboard.updatePlayerInfoScoreboard(p);
        }
    }
}
