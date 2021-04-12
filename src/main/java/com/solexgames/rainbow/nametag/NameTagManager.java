package com.solexgames.rainbow.nametag;

import com.solexgames.rainbow.rainbow.RainbowNameTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GrowlyX
 * @since 1/7/2021
 */

@Getter
@NoArgsConstructor
public class NameTagManager {

    private final String PREFIX = "nt_team_";

    private final Map<Player, RainbowNameTag> playerRainbowMap = new HashMap<>();

    public void setupNameTag(Player player, Player other, ChatColor color) {
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard.equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard()))
            scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

        Team team = player.getScoreboard().getTeam(this.getTeamName(color));
        if (team == null) {
            team = player.getScoreboard().registerNewTeam(this.getTeamName(color));
            team.setPrefix(color.toString());
        }

        if (!team.hasEntry(other.getName())) {
            this.resetNameTag(player, other);
            team.addEntry(other.getName());
        }

        player.setScoreboard(scoreboard);
    }

    public void resetNameTag(Player player, Player other) {
        if (player != null && other != null && !player.equals(other)) {
            Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

            if (objective != null) objective.unregister();

            Arrays.asList(ChatColor.values()).forEach(chatColor -> {
                Team team = player.getScoreboard().getTeam(this.getTeamName(chatColor));
                if (team != null) team.removeEntry(other.getName());
            });
        }
    }

    private String getTeamName(ChatColor color) {
        return PREFIX + color.ordinal();
    }
}
