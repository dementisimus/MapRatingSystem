package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.injection.annotations.bukkit.BukkitListener;
import dev.dementisimus.capi.core.language.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.rating.CustomMapRating;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static dev.dementisimus.mrs.MapRatingSystemPlugin.Translations.MAP_RATING_ITEM_DISPLAY_NAME;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class PlayerInteractListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 26.09.2021:11:26
 */
@BukkitListener(isOptional = false)
public class PlayerInteractListener implements Listener {

    @Inject CustomMapRating customMapRating;

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if(item != null && item.getItemMeta() != null) {
                String displayName = item.getItemMeta().getDisplayName();

                if(new BukkitTranslation(MAP_RATING_ITEM_DISPLAY_NAME).matches(displayName)) {
                    this.customMapRating.openRateMapInventory(player);
                }
            }
        }
    }
}
