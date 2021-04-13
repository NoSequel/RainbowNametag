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

import java.util.HashMap;
import java.util.Map;

/**
 * @author GrowlyX
 * @since 1/7/2021
 */
@Getter
@NoArgsConstructor
public class NameTagManager {

    private final String prefix = "nt_team_";
    private final Map<Player, RainbowNameTag> playerRainbowMap = new HashMap<>();

    /**
     * Set up the nametag for the player
     *
     * @param player the player to send the tag to
     * @param target the target to set it up for
     * @param color  the color
     */
    public void setupNameTag(Player player, Player target, ChatColor color) {
        final Scoreboard scoreboard = this.getScoreboard(player);
        final Team team = this.getTeam(color, scoreboard);

        team.setPrefix(color.toString());

        if (!team.hasEntry(target.getName())) {
            this.resetNameTag(player, target);
            team.addEntry(target.getName());
        }

        player.setScoreboard(scoreboard);
    }

    /**
     * Get the scoreboard to use for the player
     *
     * @param player the player to change the scoreboard for
     * @return the scoreboard of the player
     */
    private Scoreboard getScoreboard(Player player) {
        final Scoreboard scoreboard = player.getScoreboard();

        return scoreboard.equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard())
                ? Bukkit.getServer().getScoreboardManager().getNewScoreboard()
                : scoreboard;
    }

    /**
     * Get the team to change the prefix of for the player
     *
     * @param color      the color to get the team for
     * @param scoreboard the scoreboard to get the team from
     * @return the team
     */
    private Team getTeam(ChatColor color, Scoreboard scoreboard) {
        final Team team = scoreboard.getTeam(this.getTeamName(color));

        return team == null
                ? scoreboard.registerNewTeam(this.getTeamName(color))
                : team;
    }

    /**
     * Reset the name tag for a player
     *
     * @param player the player to send it to
     * @param target the target to reset it for
     */
    public void resetNameTag(Player player, Player target) {
        if (player != null && target != null && !player.equals(target)) {
            final Scoreboard scoreboard = this.getScoreboard(player);
            final Objective objective = scoreboard.getObjective(DisplaySlot.BELOW_NAME);

            if (objective != null) {
                objective.unregister();
            }

            for (ChatColor color : ChatColor.values()) {
                final Team team = scoreboard.getTeam(this.getTeamName(color));

                if (team != null) {
                    team.removeEntry(target.getName());
                }
            }
        }
    }

    /**
     * Get the team name per color
     *
     * @param color the color to get the team name from
     * @return the name of the team
     */
    private String getTeamName(ChatColor color) {
        return this.prefix + color.ordinal();
    }
}