package dev.dementisimus.mrs.maprating;

import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.api.RatingType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static dev.dementisimus.mrs.translation.Translations.*;

/**
 * Copyright (c) by dementisimus
 *
 * Class DefaultRatingType @ MapRatingSystem
 *
 * @author dementisimus
 * @since 27.06.2020:12:40
 */
public class DefaultRatingData {

    public static String getDisplayName(RatingType ratingType, Player player) {
        String translation = switch(ratingType) {
            case TERRIBLE -> MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE;
            case BAD -> MAPRATING_INVENTORY_ITEM_TYPE_BAD;
            case OKAY -> MAPRATING_INVENTORY_ITEM_TYPE_OKAY;
            case GOOD -> MAPRATING_INVENTORY_ITEM_TYPE_GOOD;
            case AMAZING -> MAPRATING_INVENTORY_ITEM_TYPE_AMAZING;
        };
        return new BukkitTranslation(translation).get(player);
    }

    public static Material getMaterial(RatingType ratingType) {
        return switch(ratingType) {
            case TERRIBLE -> Material.RED_TERRACOTTA;
            case BAD -> Material.ORANGE_TERRACOTTA;
            case OKAY -> Material.YELLOW_TERRACOTTA;
            case GOOD -> Material.GREEN_TERRACOTTA;
            case AMAZING -> Material.LIME_TERRACOTTA;
        };
    }

    public static RatingType matchAndReturn(String translation) {
        return switch(translation) {
            case MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE -> RatingType.TERRIBLE;
            case MAPRATING_INVENTORY_ITEM_TYPE_BAD -> RatingType.BAD;
            case MAPRATING_INVENTORY_ITEM_TYPE_OKAY -> RatingType.OKAY;
            case MAPRATING_INVENTORY_ITEM_TYPE_GOOD -> RatingType.GOOD;
            case MAPRATING_INVENTORY_ITEM_TYPE_AMAZING -> RatingType.AMAZING;
            default -> null;
        };
    }

}
