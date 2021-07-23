package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.annotations.bukkit.BukkitListener;
import dev.dementisimus.capi.core.creators.ItemCreator;
import dev.dementisimus.capi.core.events.bukkit.BukkitPlayerLanguageEvent;
import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.translation.Translations;
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
@BukkitListener(additionalModulesToInject = {MapRating.class})
public class PlayerLanguageListener implements Listener {

    @Inject MapRating mapRating;

    @EventHandler
    public void on(BukkitPlayerLanguageEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(this.mapRating.getRateMapSlot(), new ItemCreator(this.mapRating.getRateMapMaterial()).setDisplayName(new BukkitTranslation(Translations.MAPRATING_ITEM_RATEMAP).get(player)).apply());
    }
}
