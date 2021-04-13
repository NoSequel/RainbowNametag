package com.solexgames.rainbow;

import com.solexgames.rainbow.command.NameTagCommand;
import com.solexgames.rainbow.listener.PlayerListener;
import com.solexgames.rainbow.nametag.NameTagManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author GrowlyX
 * @since 4/12/2021
 */

@Getter
public final class NameTagPlugin extends JavaPlugin {

    private NameTagManager nametagManager;

    private long iterateTime = 35L;

    private String enabledNameTags;
    private String disabledNameTags; // why is this in the main class????

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.iterateTime = this.getConfig().getLong("iterate-time");

        this.enabledNameTags = this.getConfig().getString("language.enabled-tags");
        this.disabledNameTags = this.getConfig().getString("language.disabled-tags");

        this.nametagManager = new NameTagManager();

        this.getCommand("nametag").setExecutor(new NameTagCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }
}
