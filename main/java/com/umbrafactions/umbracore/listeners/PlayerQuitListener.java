package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.shortcuts.SQLInterface;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {
    private Core plugin;
    public PlayerQuitListener(Core plugin) { this.plugin = plugin; }

    private final SQLInterface sql = new SQLInterface();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // TODO
    }
}
