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

    protected final String ONLY_PLAYERS = ChatColor.RED + "Only players can execute this command.";
    protected final String NO_PERMISSION = ChatColor.RED + "I'm sorry, but you do not have permission to perform this command.";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ONLY_PLAYERS);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rainbow.command.nametag")) {
            player.sendMessage(NO_PERMISSION);
            return false;
        }

        if (args.length == 0) {
            final RainbowNameTag rainbowNameTag = NameTagPlugin.getInstance().getNametagManager().getPlayerRainbowMap().get(player);

            if (rainbowNameTag.isActive()) {
                rainbowNameTag.stopProcess();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NameTagPlugin.getInstance().getDisabledNameTags()));
            } else {
                rainbowNameTag.startProcess();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NameTagPlugin.getInstance().getEnabledNameTags()));
            }
        }

        return false;
    }
}
