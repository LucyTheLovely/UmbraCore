package com.umbrafactions.umbracore.listeners;

import com.umbrafactions.umbracore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class LaunchPadListener implements Listener {
    private Core plugin;
    public LaunchPadListener(Core plugin) { this.plugin = plugin; }
    private ArrayList<Player> justLaunched = new ArrayList<Player>();

    @EventHandler
    public void Launchpad (PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.GOLD_PLATE) {
                if (e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
                    final Player player = e.getPlayer();
                    final Block block = e.getClickedBlock();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            player.setVelocity(new Vector(player.getLocation().getDirection().getX(), 0.4, player.getLocation().getDirection().getZ()).multiply(3D));
                            block.getWorld().createExplosion(block.getX(), block.getY()+1, block.getZ(), 0F);
                            justLaunched.add(player);
                        }
                    }, 1L);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            justLaunched.remove(player);
                        }
                    }, 20*3L);
                }
            }
        }
    }

    @EventHandler
    public void LaunchpadFallDamage (EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (justLaunched.contains(e.getEntity())) {
                e.setCancelled(true);
                justLaunched.remove(e.getEntity());
            }
        }
    }

    @EventHandler
    public void LaunchpadBuild (BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.GOLD_PLATE) {
            if (e.getBlock().getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
                e.getBlock().getWorld().createExplosion(e.getBlock().getX(), e.getBlock().getY()+1, e.getBlock().getZ(), 0F);
            }
        }
    }
}
