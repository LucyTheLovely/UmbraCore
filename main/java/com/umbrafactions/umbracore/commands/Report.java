package com.umbrafactions.umbracore.commands;

import com.umbrafactions.umbracore.Core;
import com.umbrafactions.umbracore.shortcuts.SQLInterface;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Report implements CommandExecutor {
    private Core plugin;
    private SQLInterface sql = new SQLInterface();

    public Report(Core plugin) { this.plugin = plugin; }

    private void displayHelp(CommandSender player) {
        player.sendMessage(ChatColor.GRAY + "// TODO: Help menu");
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player reported = plugin.getServer().getPlayer(args[0]);
                Player reporter = plugin.getServer().getPlayer(sender.getName());
                if (!reporter.getName().equalsIgnoreCase(reported.getName())) {
                    StringBuilder reason = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        reason.append(args[i] + " ");
                    }
                    String parsedReason = reason.toString();
                    parsedReason = parsedReason.substring(0, parsedReason.length()-1);
                    sql.updateDatabase(
                            "INSERT INTO `player_reports`(`reporter`, `reported`, `reason`) VALUES (" +
                                    "\"" + reporter.getUniqueId().toString() + "\", " +
                                    "\"" + reported.getUniqueId().toString() + "\", " +
                                    "\"" + parsedReason + "\"" +
                                    ")"
                    );
                    sender.sendMessage(String.format("%sSuccessfully sent report for %s%s with reason \"%s\"!",
                            ChatColor.GREEN,
                            reported.getDisplayName(),
                            ChatColor.GREEN,
                            parsedReason));
                } else {
                    sender.sendMessage(
                            String.format("%sYou%s are %s%s%s",
                                    ChatColor.RED,
                                    ChatColor.ITALIC,
                                    ChatColor.RESET,
                                    ChatColor.RED,
                                    args[0])
                    );
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.RED + args[0] + " is not online.");
                return false;
            }
        } else {
            displayHelp(sender);
            return false;
        }
        displayHelp(sender);
        return false;
    }
}
