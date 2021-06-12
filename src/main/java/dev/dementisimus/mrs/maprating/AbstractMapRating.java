package dev.dementisimus.mrs.maprating;

import dev.dementisimus.capi.core.translations.Trans;
import dev.dementisimus.capi.core.translations.Translation;
import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslationUtils;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.data.Key;
import dev.dementisimus.mrs.translation.Translations;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
/**
 * Copyright (c) by dementisimus
 *
 * Class AbstractMapRating @ MapRatingSystem
 *
 * @author dementisimus
 * @since 23.06.2020:21:02
 */
abstract public class AbstractMapRating {

    public final String mapName;
    public final RatingType[] ratingTypes;
    public final Integer[] slots;
    public final HashMap<RatingType, Integer> ratingTypeSlots;
    public final int rateMapSlot;
    private final Material rateMapMaterial;
    private Locale locale;

    public AbstractMapRating(String mapName, RatingType[] ratingTypes, Integer[] slots, int rateMapSlot, Material rateMapMaterial) {
        this.mapName = mapName;
        this.ratingTypes = ratingTypes;
        this.slots = slots;
        this.ratingTypeSlots = new HashMap<>();
        this.rateMapSlot = rateMapSlot;
        this.rateMapMaterial = rateMapMaterial;
        boolean ex = (slots.length == ratingTypes.length);
        if(!ex) System.out.println(new Translation(Translations.MAPRATING_INVENTORY_SLOTS_NOT_SET_COMPLETELY.id).get(true));
        for(int i = 0; i < ratingTypes.length; i++) {
            int slot = -1;
            if(ex) {
                slot = slots[i];
            }
            this.ratingTypeSlots.put(ratingTypes[i], slot);
        }
    }

    public String getMapName() {
        return this.mapName;
    }

    public int getRatingTypeSlot(RatingType ratingType) {
        int slot = -1;
        if(!this.getRatingTypeSlot().isEmpty() && this.getRatingTypeSlot().get(ratingType) != null) {
            slot = this.getRatingTypeSlot().get(ratingType);
        }
        return slot;
    }

    public HashMap<RatingType, Integer> getRatingTypeSlot() {
        return this.ratingTypeSlots;
    }

    public Document getRatingData(Document result) {
        if(result != null) {
            return result.get(Key.PLAYER_VOTES.value, Document.class);
        }
        return null;
    }

    public void setPreconditionsForRatingDisplaying(Player player) {
        if(player == null) {
            this.locale = Trans.getDefaultLanguage();
            return;
        }
        this.locale = BukkitTranslationUtils.getLocaleFromPlayer(player);
    }

    public String getRatingDisplayTagByRatingEnum(RatingType ratingType) {
        if(ratingType != null) {
            if(ratingType.equals(RatingType.TERRIBLE)) return new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE.id).get(this.locale, false);
            if(ratingType.equals(RatingType.BAD)) return new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_BAD.id).get(this.locale, false);
            if(ratingType.equals(RatingType.OKAY)) return new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_OKAY.id).get(this.locale, false);
            if(ratingType.equals(RatingType.GOOD)) return new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_GOOD.id).get(this.locale, false);
            if(ratingType.equals(RatingType.AMAZING)) return new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_AMAZING.id).get(this.locale, false);
        }
        return null;
    }

    public RatingType getRatingDisplayTagByRatingString(String ratingType) {
        if(ratingType != null) {
            if((new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE.id).matches(ratingType))) return RatingType.TERRIBLE;
            if((new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_BAD.id).matches(ratingType))) return RatingType.BAD;
            if((new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_OKAY.id).matches(ratingType))) return RatingType.OKAY;
            if((new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_GOOD.id).matches(ratingType))) return RatingType.GOOD;
            if((new Translation(Translations.MAPRATING_INVENTORY_ITEM_TYPE_AMAZING.id).matches(ratingType))) return RatingType.AMAZING;
        }
        return null;
    }

    public RatingType[] getRatingTypes() {
        return this.ratingTypes;
    }

    public int getRateMapSlot() {
        return this.rateMapSlot;
    }

    public Material getRateMapMaterial() {
        return this.rateMapMaterial;
    }

}
