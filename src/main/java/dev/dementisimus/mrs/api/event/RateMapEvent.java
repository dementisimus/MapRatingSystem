package dev.dementisimus.mrs.api.event;

import dev.dementisimus.mrs.api.RatingType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus
 *
 * Class RateMapEvent @ MapRatingSystem
 *
 * @author dementisimus
 * @since 23.06.2020:21:09
 */
public class RateMapEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final boolean changedVote;
    private final boolean newVote;
    private final String map;
    private final RatingType ratingType;

    /**
     * this is the constructor for the RateMapEvent. It gets fired when a player votes for a specific map.
     *
     * @param player      The player which voted for a specific map
     * @param map         The map a player voted for
     * @param ratingType  The {@link RatingType} a player submitted for a map
     * @param changedVote =true when a player changed his vote on a map
     * @param newVote     =true when a player submitted a new vote on a map
     */
    public RateMapEvent(Player player, String map, RatingType ratingType, boolean changedVote, boolean newVote) {
        super(true);
        this.player = player;
        this.changedVote = changedVote;
        this.newVote = newVote;
        this.map = map;
        this.ratingType = ratingType;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * @return the player which voted for a specific map
     */
    public @NotNull Player getPlayer() {
        return this.player;
    }

    /**
     * @return the map a player voted for
     */
    public @NotNull String getMap() {
        return this.map;
    }

    /**
     * @return the {@link RatingType} a player submitted for a map
     */
    public @NotNull RatingType getRatingType() {
        return this.ratingType;
    }

    /**
     * @return true when a player changed his vote on a map
     */
    public boolean changedVote() {
        return this.changedVote;
    }

    /**
     * @return true when a player submitted a new vote on a map
     */
    public boolean newVote() {
        return this.newVote;
    }
}
