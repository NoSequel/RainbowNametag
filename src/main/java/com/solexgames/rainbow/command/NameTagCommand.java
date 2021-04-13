package com.solexgames.rainbow.command;

import com.solexgames.rainbow.NameTagPlugin;
import com.solexgames.rainbow.rainbow.RainbowNameTag;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author GrowlyX
 * @since 4/12/2021
 */

public class NameTagCommand implements CommandExecutor {

    private final NameTagPlugin plugin;

    /**
     * Constructor to make a new nametag command
     *
     * @param plugin the plugin the command is registered to
     */
    public NameTagCommand(NameTagPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return false;
        }

        if (!sender.hasPermission("rainbow.command.nametag")) {
            sender.sendMessage("I'm sorry, but you do not have permission to perform this command.");
            return false;
        }

        final Player player = (Player) sender;
        final RainbowNameTag rainbowNameTag = plugin.getNametagManager().getPlayerRainbowMap().get(player);
        final String message = rainbowNameTag.isActive()
                ? ChatColor.translateAlternateColorCodes('&', plugin.getDisabledNameTags())
                : ChatColor.translateAlternateColorCodes('&', plugin.getEnabledNameTags());

        rainbowNameTag.toggle();
        player.sendMessage(message);


        return true;
    }
}