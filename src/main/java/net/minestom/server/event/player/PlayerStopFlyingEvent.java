package net.minestom.server.event.player;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stop flying.
 */
public class PlayerStopFlyingEvent implements PlayerEvent {

    private final Player player;

    public PlayerStopFlyingEvent(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
