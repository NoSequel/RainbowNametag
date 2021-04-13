package com.solexgames.rainbow.listener;

import com.solexgames.rainbow.NameTagPlugin;
import com.solexgames.rainbow.rainbow.RainbowNameTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author GrowlyX
 * @since 4/12/2021
 */

public class PlayerListener implements Listener {

    private final NameTagPlugin plugin;

    /**
     * Constructor to make a new player listener object
     *
     * @param plugin the plugin it's registered to
     */
    public PlayerListener(NameTagPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final RainbowNameTag rainbowNametag = new RainbowNameTag(player, plugin);

        plugin.getNametagManager().getPlayerRainbowMap().put(player, rainbowNametag);
    }
}