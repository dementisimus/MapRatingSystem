package dev.dementisimus.mrs.api;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class RatedMap @ MapRatingSystem
 *
 * @author dementisimus
 * @since 26.09.2021:14:49
 */
public interface RatedMap {

    /**
     * gets all votes by {@link RatingType}
     *
     * @param ratingType all votes to get by {@link RatingType}
     */
    List<UUID> getPlayerVotes(RatingType ratingType);

    /**
     * gets the rating type the player has voted for
     *
     * @param player player to check
     */
    RatingType getPlayerVote(Player player);
}
