package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class LaunchPadListener implements Listener {
    private Core plugin;
    public LaunchPadListener(Core plugin) { this.plugin = plugin; }

    @EventHandler
    public void Launchpad (PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.GOLD_PLATE) {
                if (e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
                    final Player player = e.getPlayer();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            player.setVelocity(new Vector(player.getLocation().getDirection().getX(), 0.3, player.getLocation().getDirection().getZ()).multiply(3D));
                        }
                    }, 1L);
                }
            }
        }
    }
}
