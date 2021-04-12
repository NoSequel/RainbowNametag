package com.solexgames.rainbow.rainbow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

/**
 * A utility to scroll through {@link ChatColor}s.
 * <p>
 *
 * @author GrowlyX
 * @since 4/12/2021
 */

@Getter
@NoArgsConstructor
public class RainbowIterator {

    private final static List<ChatColor> ORDERED_COLORS = Arrays.asList(
            ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD,
            ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN,
            ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_BLUE
    );

    private int listPosition;
    private ChatColor currentChatColor;

    public ChatColor iterateAndGet() {
        this.increment();
        this.currentChatColor = RainbowIterator.ORDERED_COLORS.get(this.listPosition);

        return this.currentChatColor;
    }

    private void increment() {
        if (this.listPosition == 9) {
            this.listPosition = 0;
        } else {
            this.listPosition++;
        }
    }
}
