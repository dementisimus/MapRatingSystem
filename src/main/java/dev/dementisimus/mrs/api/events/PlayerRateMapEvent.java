package dev.dementisimus.mrs.api.events;

import dev.dementisimus.mrs.api.RatingType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class PlayerRateMapEvent @ MapRatingSystem
 *
 * @author dementisimus
 * @since 26.09.2021:13:28
 */
public class PlayerRateMapEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * The player who issued the map vote
     */
    @Getter private final Player player;
    /**
     * The map which has been rated
     */
    @Getter private final String ratedMap;
    /**
     * The {@link RatingType} the map has been rated with
     */
    @Getter private final RatingType ratingType;
    /**
     * success-state
     */
    @Getter private final boolean success;

    @Getter
    @Setter
    private boolean cancelled;

    @Getter
    @Setter
    private boolean newVote;

    @Getter
    @Setter
    private boolean changedVote;

    public PlayerRateMapEvent(Player player, String ratedMap, RatingType ratingType, boolean success) {
        super(false);
        this.player = player;
        this.ratedMap = ratedMap;
        this.ratingType = ratingType;
        this.success = success;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
