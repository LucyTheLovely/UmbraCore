package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.shortcuts.PlayerScoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {
    private Core plugin;
    public PlayerJoinListener(Core plugin) { this.plugin = plugin; }
    private PlayerScoreboard playerScoreboard = new PlayerScoreboard();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerScoreboard.setPlayerInfoScoreboard(player);
    }
}
