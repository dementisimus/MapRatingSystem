package dev.dementisimus.mrs.listeners;

import dev.dementisimus.capi.core.events.bukkit.BukkitSetUpDoneEvent;
import dev.dementisimus.mrs.MapRatingSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupDoneListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 16.07.2021:21:35
 */
public record SetupDoneListener(MapRatingSystem mapRatingSystem) implements Listener {

    @EventHandler
    public void on(BukkitSetUpDoneEvent event) {
        if(event.isDone()) {
            this.mapRatingSystem.createMapRatingAPIObject();
        }
    }

}
