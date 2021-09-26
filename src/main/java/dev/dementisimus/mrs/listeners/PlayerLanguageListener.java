package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.events.bukkit.BukkitPlayerLanguageEvent;
import dev.dementisimus.capi.core.injection.annotations.bukkit.BukkitListener;
import dev.dementisimus.mrs.rating.CustomMapRating;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class PlayerLanguageListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 25.09.2021:20:02
 */
@BukkitListener(isOptional = false)
public class PlayerLanguageListener implements Listener {

    @Inject CustomMapRating customMapRating;

    @EventHandler
    public void on(BukkitPlayerLanguageEvent event) {
        Player player = event.getPlayer();

        if(!CustomMapRating.isDoNotSetRateMapItemOnPlayerJoin()) {
            this.customMapRating.setRateMapItem(player);
        }
    }
}
