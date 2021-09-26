package dev.dementisimus.mrs.listeners;

import dev.dementisimus.capi.core.injection.annotations.bukkit.BukkitListener;
import dev.dementisimus.capi.core.language.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.MapRatingSystemPlugin;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.api.events.PlayerRateMapEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import static dev.dementisimus.mrs.MapRatingSystemPlugin.Translations.RATE_MAP_FAILURE;
import static dev.dementisimus.mrs.MapRatingSystemPlugin.Translations.RATE_MAP_SUCCESS;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class PlayerRateMapListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 26.09.2021:13:30
 */
@BukkitListener(isOptional = false)
public class PlayerRateMapListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerRateMapEvent event) {
        if(!event.isCancelled()) {
            Player player = event.getPlayer();
            String ratedMap = event.getRatedMap();
            RatingType ratingType = event.getRatingType();

            String translationProperty = RATE_MAP_FAILURE;
            Sound sound = Sound.ENTITY_VILLAGER_NO;

            if(event.isSuccess()) {
                translationProperty = RATE_MAP_SUCCESS;
                sound = Sound.ENTITY_VILLAGER_YES;
            }

            String[] targets = new String[]{"$prefix$", "$map$", "$rating$"};
            String[] replacements = new String[]{MapRatingSystemPlugin.Strings.PREFIX, ratedMap, new BukkitTranslation(ratingType.getTranslationProperty()).get(player)};

            player.sendMessage(new BukkitTranslation(translationProperty).get(player, targets, replacements));
            player.playSound(player.getLocation(), sound, 10, 1);
            player.closeInventory();
        }
    }
}
