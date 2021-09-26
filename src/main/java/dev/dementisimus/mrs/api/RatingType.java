package dev.dementisimus.mrs.api;

import lombok.Getter;
import org.bukkit.Material;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class RatingType @ MapRatingSystem
 *
 * @author dementisimus
 * @since 25.09.2021:18:28
 */
public enum RatingType {

    /**
     * A terrible map, equals to 1 star
     */
    TERRIBLE(Material.RED_TERRACOTTA, 9, "ratingtype.terrible"),
    /**
     * A bad map, equals to 2 stars
     */
    BAD(Material.ORANGE_TERRACOTTA, 11, "ratingtype.bad"),
    /**
     * An okay map, equals to 3 stars
     */
    OKAY(Material.YELLOW_TERRACOTTA, 13, "ratingtype.okay"),
    /**
     * A good map, equals to 4 stars
     */
    GOOD(Material.GREEN_TERRACOTTA, 15, "ratingtype.good"),
    /**
     * An amazing map, equals to 5 stars
     */
    AMAZING(Material.LIME_TERRACOTTA, 17, "ratingtype.amazing");

    @Getter private final Material material;
    @Getter private final int slot;
    @Getter private final String translationProperty;

    RatingType(Material material, int slot, String translationProperty) {
        this.material = material;
        this.slot = slot;
        this.translationProperty = translationProperty;
    }
}
