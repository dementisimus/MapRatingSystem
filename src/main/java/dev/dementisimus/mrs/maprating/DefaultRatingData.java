package dev.dementisimus.mrs.maprating;

import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.translation.Translations;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        Translations translations = null;
        switch(ratingType) {
            case TERRIBLE:
                translations = Translations.MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE;
                break;
            case BAD:
                translations = Translations.MAPRATING_INVENTORY_ITEM_TYPE_BAD;
                break;
            case OKAY:
                translations = Translations.MAPRATING_INVENTORY_ITEM_TYPE_OKAY;
                break;
            case GOOD:
                translations = Translations.MAPRATING_INVENTORY_ITEM_TYPE_GOOD;
                break;
            case AMAZING:
                translations = Translations.MAPRATING_INVENTORY_ITEM_TYPE_AMAZING;
                break;
        }
        if(translations == null) return null;
        return new BukkitTranslation(translations.id).get(player);
    }

    public static Material getMaterial(RatingType ratingType) {
        switch(ratingType) {
            case TERRIBLE:
                return Material.RED_TERRACOTTA;
            case BAD:
                return Material.ORANGE_TERRACOTTA;
            case OKAY:
                return Material.YELLOW_TERRACOTTA;
            case GOOD:
                return Material.GREEN_TERRACOTTA;
            case AMAZING:
                return Material.LIME_TERRACOTTA;
            default:
                return null;
        }
    }

    public static RatingType matchAndReturn(Translations translations) {
        switch(translations) {
            case MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE:
                return RatingType.TERRIBLE;
            case MAPRATING_INVENTORY_ITEM_TYPE_BAD:
                return RatingType.BAD;
            case MAPRATING_INVENTORY_ITEM_TYPE_OKAY:
                return RatingType.OKAY;
            case MAPRATING_INVENTORY_ITEM_TYPE_GOOD:
                return RatingType.GOOD;
            case MAPRATING_INVENTORY_ITEM_TYPE_AMAZING:
                return RatingType.AMAZING;
            default:
                return null;
        }
    }

}
