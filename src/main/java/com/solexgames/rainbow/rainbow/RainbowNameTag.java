package com.solexgames.rainbow.rainbow;

import com.solexgames.rainbow.NameTagPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * An object to setup rainbow name-tags for users, and update them for every player.
 * <p>
 *
 * @author GrowlyX
 * @since 4/12/2021
 */

@Getter
@RequiredArgsConstructor
public class RainbowNameTag {

    private final Player player;

    private RainbowIterator rainbowIterator;
    private Runnable rainbowRunnable;
    private BukkitTask rainbowTask;

    private boolean active;

    public void startProcess() {
        this.rainbowIterator = new RainbowIterator();
        this.rainbowRunnable = () -> {
            final ChatColor chatColor = this.rainbowIterator.iterateAndGet();

            player.setPlayerListName(chatColor + player.getName());

            Bukkit.getOnlinePlayers().forEach(otherPlayer -> NameTagPlugin.getInstance().getNametagManager().setupNameTag(this.player, otherPlayer, chatColor));
        };
        this.rainbowTask = Bukkit.getScheduler().runTaskTimer(NameTagPlugin.getInstance(), this.rainbowRunnable, 20L, 35L);

        this.active = true;
    }

    public void stopProcess() {
        this.rainbowTask.cancel();
        this.rainbowIterator = null;
        this.rainbowRunnable = null;

        this.player.setPlayerListName(this.player.getName());

        Bukkit.getOnlinePlayers().forEach(otherPlayer -> NameTagPlugin.getInstance().getNametagManager().resetNameTag(this.player, otherPlayer));

        this.active = false;
    }
}
