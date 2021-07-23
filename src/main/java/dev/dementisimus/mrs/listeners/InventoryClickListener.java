package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.annotations.bukkit.BukkitListener;
import dev.dementisimus.capi.core.databases.DataManagement;
import dev.dementisimus.capi.core.translations.Translation;
import dev.dementisimus.mrs.MapRatingSystem;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.api.event.RateMapEvent;
import dev.dementisimus.mrs.data.Key;
import dev.dementisimus.mrs.data.TablesOrCollections;
import dev.dementisimus.mrs.maprating.DefaultRatingData;
import dev.dementisimus.mrs.translation.Translations;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Objects;
/**
 * Copyright (c) by dementisimus
 *
 * Class InventoryClickListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 23.06.2020:20:30
 */
@BukkitListener(additionalModulesToInject = {MapRatingSystem.class, MapRating.class})
public class InventoryClickListener implements Listener {

    @Inject private MapRatingSystem mapRatingSystem;

    @Inject private MapRating mapRating;

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String uuid = player.getUniqueId().toString();
        DataManagement dataManagement = this.mapRatingSystem.getCoreAPI().getDataManagement();

        if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
            String title = event.getView().getTitle();
            String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            if(new Translation(Translations.MAPRATING_INVENTORY_TITLE).matches(title, "$map$", this.mapRating.getMapName())) {
                event.setCancelled(true);
                RatingType currentRatingType = null;
                for(RatingType ratingType : this.mapRating.getRatingTypes()) {
                    if(Objects.requireNonNull(DefaultRatingData.getDisplayName(ratingType, player)).equalsIgnoreCase(displayName)) {
                        currentRatingType = ratingType;
                    }
                }
                if(currentRatingType != null) {
                    dataManagement.setRequirements(TablesOrCollections.MAPRATINGS.value, Key.MAP.value, this.mapRating.getMapName());
                    RatingType finalCurrentRatingType = currentRatingType;
                    dataManagement.get(new String[]{Key.PLAYER_VOTES.value}, result -> {
                        if(result == null || result.isEmpty() || result.get(Key.PLAYER_VOTES.value) == null) {
                            this.setVote(dataManagement, result, null, player, finalCurrentRatingType, new ArrayList<>(), true, false);
                        }else {
                            if(result.get(Key.PLAYER_VOTES.value) != null) {
                                Document ratingTypes = this.mapRating.getRatingData(result);
                                ArrayList<String> votes = ratingTypes.get(finalCurrentRatingType.toString(), ArrayList.class);

                                boolean changedVote = false;
                                boolean newVote = false;
                                if(votes != null) {
                                    if(!votes.contains(uuid)) {
                                        newVote = true;
                                    }
                                }else {
                                    votes = new ArrayList<>();
                                    newVote = true;
                                }

                                for(String rT : ratingTypes.keySet()) {
                                    ArrayList<String> vo = ratingTypes.get(rT, ArrayList.class);
                                    if(vo != null && vo.contains(uuid)) {
                                        newVote = false;
                                        if(!rT.equalsIgnoreCase(finalCurrentRatingType.toString())) {
                                            changedVote = true;
                                            vo.remove(uuid);
                                        }
                                    }
                                    ratingTypes.put(rT, vo);
                                }

                                this.setVote(dataManagement, result, ratingTypes, player, finalCurrentRatingType, votes, newVote, changedVote);
                            }
                        }
                    });
                }
                player.closeInventory();
            }
        }
    }

    private void setVote(DataManagement dataManagement, Document result, Document ratingTypes, Player player, RatingType ratingType, ArrayList<String> playerVotes, boolean newVote, boolean changedVote) {
        if(newVote) changedVote = false;

        Document iData = new Document();
        iData.put(Key.MAP.value, this.mapRating.getMapName());
        Document ratingData = new Document();

        if(newVote || changedVote) {
            String uuid = player.getUniqueId().toString();
            playerVotes.remove(uuid);
            playerVotes.add(uuid);
        }

        for(RatingType rT : this.mapRating.getRatingTypes()) {
            String type = rT.toString();
            if(rT.equals(ratingType)) {
                ratingData.put(type, playerVotes);
            }else {
                if(ratingTypes != null && ratingTypes.get(type) != null) {
                    ratingData.put(type, ratingTypes.get(type, ArrayList.class));
                }
            }
        }

        iData.put(Key.PLAYER_VOTES.value, ratingData);
        boolean finalChangedVote = changedVote;
        if(result == null || result.isEmpty() || result.get(Key.PLAYER_VOTES.value) == null) {
            dataManagement.write(iData, re -> this.callEvent(re, player, ratingType, finalChangedVote, newVote));
        }else {
            dataManagement.update(iData, re -> this.callEvent(re, player, ratingType, finalChangedVote, newVote));
        }
    }

    private void callEvent(boolean re, Player player, RatingType ratingType, boolean changedVote, boolean newVote) {
        if(re) {
            RateMapEvent rateMapEvent = new RateMapEvent(player, this.mapRating.getMapName(), ratingType, changedVote, newVote);
            Bukkit.getPluginManager().callEvent(rateMapEvent);
        }
    }
}
