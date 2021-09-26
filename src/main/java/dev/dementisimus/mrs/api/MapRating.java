package dev.dementisimus.mrs.api;

import dev.dementisimus.capi.core.callback.Callback;
import dev.dementisimus.mrs.rating.CustomRatedMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class MapRating @ MapRatingSystem
 *
 * @author dementisimus
 * @since 25.09.2021:18:20
 */
public interface MapRating {

    /**
     * Performs a player map vote
     *
     * @param player          the issuer
     * @param mapName         the map which should be rated for
     * @param ratingType      the {@link RatingType} which will be used for rating the given map
     * @param booleanCallback success-state
     */
    void rate(Player player, String mapName, RatingType ratingType, Callback<Boolean> booleanCallback);

    /**
     * Gets the rating for the given map name
     *
     * @param mapName          map name from which the rating should be retrieved from
     * @param ratedMapCallback {@link CustomRatedMap}
     */
    void getRating(String mapName, Callback<CustomRatedMap> ratedMapCallback);

    /**
     * @param material the {@link Material} used by {@link #setRateMapItem(Player)}
     */
    void setMapRatingItemMaterial(Material material);

    /**
     * @param slot the slot on which {@link #setRateMapItem(Player)} will place the rate map item
     */
    void setMapRatingItemSlot(int slot);

    /**
     * @param player the {@link Player} to which the rate map item will be given to
     */
    void setRateMapItem(Player player);

    /**
     * if used, no rate map item will be set on player join,
     * instead you may give the rate map item to any player at any time
     * by using {@link #setRateMapItem(Player)}
     */
    void doNotSetRateMapItemOnPlayerJoin();
}
