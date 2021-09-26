package dev.dementisimus.mrs.api;

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
     * @param ratingType gets all votes by {@link RatingType}
     */
    List<UUID> getPlayerVotes(RatingType ratingType);
}
