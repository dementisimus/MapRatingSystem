package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.events.bukkit.ValidInventoryClickEvent;
import dev.dementisimus.capi.core.injection.annotations.bukkit.BukkitListener;
import dev.dementisimus.capi.core.language.bukkit.BukkitTranslation;
import dev.dementisimus.capi.core.pools.BukkitSynchronousExecutor;
import dev.dementisimus.mrs.MapRatingSystemPlugin;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.api.events.PlayerRateMapEvent;
import dev.dementisimus.mrs.rating.CustomMapRating;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static dev.dementisimus.mrs.MapRatingSystemPlugin.Translations.MAP_RATING_ITEM_DISPLAY_NAME;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class ValidInventoryClickListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 26.09.2021:12:41
 */
@BukkitListener(isOptional = false)
public class ValidInventoryClickListener implements Listener {

    @Inject MapRatingSystemPlugin mapRatingSystemPlugin;
    @Inject CustomMapRating customMapRating;

    @EventHandler
    public void on(ValidInventoryClickEvent event) {
        Player player = event.getPlayer();
        String title = event.getTitle();
        String displayName = event.getCurrentItemDisplayName();

        if(new BukkitTranslation(MAP_RATING_ITEM_DISPLAY_NAME).matches(title)) {
            event.setCancelled(true);

            RatingType clickedRatingType = null;

            for(RatingType ratingType : RatingType.values()) {
                if(new BukkitTranslation(ratingType.getTranslationProperty()).matches(displayName)) {
                    clickedRatingType = ratingType;
                    break;
                }
            }

            if(clickedRatingType != null) {
                String mapName = this.customMapRating.getWildcardMapName(player);
                RatingType finalClickedRatingType = clickedRatingType;

                this.customMapRating.getRating(mapName, customRatedMap -> {
                    RatingType prevRatingType = customRatedMap.getPlayerVote(player);
                    boolean isNewVote = prevRatingType == null;

                    this.customMapRating.rate(player, mapName, finalClickedRatingType, success -> {
                        BukkitSynchronousExecutor.execute(this.mapRatingSystemPlugin, () -> {
                            PlayerRateMapEvent playerRateMapEvent = new PlayerRateMapEvent(player, mapName, finalClickedRatingType, success);

                            playerRateMapEvent.setNewVote(isNewVote);
                            if(prevRatingType != null) {
                                playerRateMapEvent.setChangedVote(!prevRatingType.equals(finalClickedRatingType));
                            }

                            Bukkit.getPluginManager().callEvent(playerRateMapEvent);
                        });
                    });
                });
            }
        }
    }
}
