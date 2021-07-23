package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.annotations.bukkit.BukkitListener;
import dev.dementisimus.capi.core.creators.InventoryCreator;
import dev.dementisimus.capi.core.creators.ItemCreator;
import dev.dementisimus.capi.core.databases.DataManagement;
import dev.dementisimus.capi.core.translations.Translation;
import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.MapRatingSystem;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.data.Key;
import dev.dementisimus.mrs.data.TablesOrCollections;
import dev.dementisimus.mrs.maprating.DefaultRatingData;
import dev.dementisimus.mrs.translation.Translations;
import org.bson.Document;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Objects;
/**
 * Copyright (c) by dementisimus
 *
 * Class PlayerInteractListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 22.06.2020:22:27
 */
@BukkitListener(additionalModulesToInject = {MapRatingSystem.class})
public class PlayerInteractListener implements Listener {

    @Inject private MapRatingSystem mapRatingSystem;

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        DataManagement dataManagement = this.mapRatingSystem.getCoreAPI().getDataManagement();
        MapRating mapRating = this.mapRatingSystem.getMapRating();
        String uuid = player.getUniqueId().toString();
        Action action = event.getAction();
        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getItem() != null) {
                String title = event.getItem().getItemMeta().getDisplayName();
                if(title != null) {
                    if(new Translation(Translations.MAPRATING_ITEM_RATEMAP).matches(title)) {
                        event.setCancelled(true);
                        new InventoryCreator(9, new BukkitTranslation(Translations.MAPRATING_INVENTORY_TITLE).get(player, "$map$", mapRating.getMapName()), inventoryCreator -> {
                            dataManagement.setRequirements(TablesOrCollections.MAPRATINGS.value, Key.MAP.value, mapRating.getMapName());
                            dataManagement.get(new String[]{Key.PLAYER_VOTES.value}, result -> {
                                Document votesByMapRatings = mapRating.getRatingData(result);
                                int avaSlot = 0;
                                for(RatingType ratingType : mapRating.getRatingTypes()) {
                                    Enchantment ench = null;
                                    if(votesByMapRatings != null && !votesByMapRatings.isEmpty()) {
                                        if(votesByMapRatings.get(ratingType.toString()) != null) {
                                            ArrayList<String> votes = votesByMapRatings.get(ratingType.toString(), ArrayList.class);
                                            if(votes.contains(uuid)) ench = Enchantment.DAMAGE_ALL;
                                        }
                                    }
                                    int sl = mapRating.getRatingTypeSlot(ratingType);
                                    int slot = ((sl == -1) ? avaSlot : sl);
                                    inventoryCreator.setItem(slot, new ItemCreator(Objects.requireNonNull(DefaultRatingData.getMaterial(ratingType))).setDisplayName(DefaultRatingData.getDisplayName(ratingType, player)).addEnchantment(ench, 1).addFlag(ItemFlag.HIDE_ENCHANTS).apply());
                                    avaSlot++;
                                }
                            });
                            player.openInventory(inventoryCreator.apply());
                        });
                    }
                }
            }
        }
    }
}
