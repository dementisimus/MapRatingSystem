package dev.dementisimus.mrs.listeners;

import dev.dementisimus.capi.core.creators.ItemCreator;
import dev.dementisimus.capi.core.events.bukkit.BukkitPlayerLanguageEvent;
import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.MapRatingSystem;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.translation.Translations;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
/**
 * Copyright (c) by dementisimus
 *
 * Class PlayerLanguageListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 27.07.2020:16:47
 */
public class PlayerLanguageListener implements Listener {

    private final MapRatingSystem mapRatingSystem;
    private final MapRating mapRating;
    private final int slot;
    private final Material rateMapMaterial;

    public PlayerLanguageListener(MapRatingSystem mapRatingSystem) {
        this.mapRatingSystem = mapRatingSystem;
        this.mapRating = mapRatingSystem.getMapRating();
        this.slot = mapRating.getRateMapSlot();
        this.rateMapMaterial = mapRating.getRateMapMaterial();
    }

    @EventHandler
    public void on(BukkitPlayerLanguageEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(slot,
                                      new ItemCreator(rateMapMaterial).setDisplayName(new BukkitTranslation(Translations.MAPRATING_ITEM_RATEMAP.id).get(
                                              player)).apply());
    }
}
