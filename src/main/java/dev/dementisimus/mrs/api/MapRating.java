package dev.dementisimus.mrs.api;

import dev.dementisimus.capi.core.annotations.UserRequirement;
import dev.dementisimus.mrs.maprating.AbstractMapRating;
import dev.dementisimus.mrs.maprating.MapRatingInitializationQueue;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus
 *
 * Class MapRating @ MapRatingSystem
 *
 * @author dementisimus
 * @since 23.06.2020:21:07
 */
public class MapRating extends AbstractMapRating {

    /**
     * this function initializes the API! This is required to use this API!
     *
     * @param mapName         The map which will be rated
     * @param ratingTypes     {@link RatingType} RatingTypes which will be available for rating
     * @param slots           Slots on which the items will be placed on
     * @param rateMapSlot     Slot on which the rate-map-item will be placed on
     * @param rateMapMaterial A Material which will be used for the rate-map-item
     */
    @UserRequirement
    public MapRating(@NotNull String mapName, @NotNull RatingType[] ratingTypes, @NotNull Integer[] slots, int rateMapSlot, @NotNull Material rateMapMaterial) {
        super(mapName, ratingTypes, slots, rateMapSlot, rateMapMaterial);
        MapRatingInitializationQueue.setMapRating(this);
    }
}
