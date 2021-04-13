package com.solexgames.rainbow.rainbow;

import com.solexgames.rainbow.NameTagPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class RainbowNameTag extends BukkitRunnable {

    private final Player player;
    private final NameTagPlugin plugin;

    private final ChatColor[] colors = new ChatColor[] {
            ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD,
            ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN,
            ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_BLUE
    };

    private ChatColor currentColor = colors[0];
    private boolean active = false;

    /**
     * Constructor to make a new rainbow
     *
     * @param player the player the nametag is for
     */
    public RainbowNameTag(Player player, NameTagPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    /**
     * Toggle the status of the runnable
     */
    public void toggle() {
        if(this.active) {
            this.active = false;
            this.currentColor = this.colors[0];

            this.cancel();
        } else {
            this.active = true;

            this.runTaskTimer(plugin, 0L, this.plugin.getIterateTime());
        }
    }

    @Override
    public void run() {
        final int ordinal = this.currentColor.ordinal();
        final ChatColor color = (this.currentColor = colors[ordinal >= colors.length ? 0 : ordinal + 1]);

        player.setPlayerListName(color + player.getName());

        for (Player target : Bukkit.getOnlinePlayers()) {
            plugin.getNametagManager().setupNameTag(player, target, color);
        }
    }
}