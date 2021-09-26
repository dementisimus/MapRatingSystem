package dev.dementisimus.mrs.rating;

import dev.dementisimus.capi.core.BukkitCoreAPI;
import dev.dementisimus.capi.core.callback.Callback;
import dev.dementisimus.capi.core.creators.InventoryCreator;
import dev.dementisimus.capi.core.creators.ItemCreator;
import dev.dementisimus.capi.core.creators.infiniteinventory.CustomInfiniteInventory;
import dev.dementisimus.capi.core.creators.infiniteinventory.InfiniteInventoryObject;
import dev.dementisimus.capi.core.database.Database;
import dev.dementisimus.capi.core.database.properties.ReadProperty;
import dev.dementisimus.capi.core.database.properties.UpdateProperty;
import dev.dementisimus.capi.core.language.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.MapRatingSystemPlugin;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.api.RatingType;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.dementisimus.mrs.MapRatingSystemPlugin.DataSource;
import static dev.dementisimus.mrs.MapRatingSystemPlugin.Translations.MAP_RATINGS_INVENTORY_TITLE;
import static dev.dementisimus.mrs.MapRatingSystemPlugin.Translations.MAP_RATING_ITEM_DISPLAY_NAME;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class CustomMapRating @ MapRatingSystem
 *
 * @author dementisimus
 * @since 25.09.2021:18:20
 */
public class CustomMapRating implements MapRating {

    @Getter private static Material mapRatingItemMaterial = Material.PAPER;
    @Getter private static int mapRatingItemSlot = 4;

    @Getter
    @Setter
    private static boolean doNotSetRateMapItemOnPlayerJoin;

    @Getter private final MapRatingSystemPlugin mapRatingSystemPlugin;
    @Getter private final BukkitCoreAPI bukkitCoreAPI;
    @Getter private final Database database;

    public CustomMapRating() {
        this.mapRatingSystemPlugin = MapRatingSystemPlugin.getMapRatingSystemPlugin();
        this.bukkitCoreAPI = this.mapRatingSystemPlugin.getBukkitCoreAPI();
        this.database = this.bukkitCoreAPI.getDatabase();
    }

    @Override
    public void rate(Player player, String mapName, RatingType ratingType, Callback<Boolean> booleanCallback) {
        mapName = this.getWildcardMapName(player);

        this.getRating(mapName, customRatedMap -> {

            customRatedMap.setVote(player.getUniqueId(), ratingType);

            Document document = customRatedMap.toDocument();
            UpdateProperty updateProperty = UpdateProperty.of(DataSource.MAP, customRatedMap.getMapName()).value(DataSource.PLAYER_VOTES, customRatedMap.toSerializedPlayerVotes());

            this.database.setDataSourceProperty(DataSource.PROPERTY);

            this.database.setDocument(document);
            this.database.setUpdateProperty(updateProperty);
            this.database.writeOrUpdate(booleanCallback);
        });
    }

    @Override
    public void getRating(String mapName, Callback<CustomRatedMap> ratedMapCallback) {
        this.database.setDataSourceProperty(DataSource.PROPERTY);

        this.database.setReadProperty(ReadProperty.of(DataSource.MAP, mapName));
        this.database.read(document -> {
            CustomRatedMap customRatedMap = CustomRatedMap.fromDocument(document);
            customRatedMap.setMapName(mapName);

            ratedMapCallback.done(customRatedMap);
        });
    }

    @Override
    public void setMapRatingItemMaterial(Material material) {
        CustomMapRating.mapRatingItemMaterial = material;
    }

    @Override
    public void setMapRatingItemSlot(int slot) {
        CustomMapRating.mapRatingItemSlot = slot;
    }

    @Override
    public void setRateMapItem(Player player) {
        ItemCreator itemCreator = new ItemCreator(CustomMapRating.getMapRatingItemMaterial());

        itemCreator.setDisplayName(player, MapRatingSystemPlugin.Translations.MAP_RATING_ITEM_DISPLAY_NAME);

        player.getInventory().setItem(CustomMapRating.getMapRatingItemSlot(), itemCreator.apply());
    }

    @Override
    public void doNotSetRateMapItemOnPlayerJoin() {
        CustomMapRating.setDoNotSetRateMapItemOnPlayerJoin(true);
    }

    public void openRatedMapsInventory(Player player) {
        new CustomInfiniteInventory(this.mapRatingSystemPlugin, player, customInfiniteInventory -> {
            customInfiniteInventory.setInfiniteInventoryObject(new InfiniteInventoryObject(player, infiniteInventoryObject -> {
                infiniteInventoryObject.setUseCache(false);
                infiniteInventoryObject.setInventorySize(54);
                infiniteInventoryObject.setMaxItemsOnPage(28);
                infiniteInventoryObject.setTitleTranslationProperty(MAP_RATINGS_INVENTORY_TITLE);
                infiniteInventoryObject.setPlaceholderMaterial(Material.WHITE_STAINED_GLASS_PANE);

                List<ItemStack> items = new ArrayList<>();

                this.database.setDataSourceProperty(DataSource.PROPERTY);
                this.database.disableCache();
                this.database.list(documents -> {
                    for(Document document : documents) {
                        CustomRatedMap customRatedMap = CustomRatedMap.fromDocument(document);
                        ItemCreator itemCreator = new ItemCreator(Material.FILLED_MAP).addAllFlags();

                        itemCreator.setDisplayName("§c" + customRatedMap.getMapName());
                        itemCreator.addLore(" ");

                        for(RatingType ratingType : RatingType.values()) {
                            itemCreator.addLore(new BukkitTranslation(ratingType.getTranslationProperty()).get(player) + ": §7§l" + customRatedMap.getPlayerVotes(ratingType).size());
                        }

                        itemCreator.addLore(" ");

                        items.add(itemCreator.apply());
                    }

                    infiniteInventoryObject.setItems(items);
                    customInfiniteInventory.setInfiniteInventoryObject(infiniteInventoryObject.build(customInfiniteInventory));

                    customInfiniteInventory.open(inventoryCreator -> {
                        inventoryCreator.apply(player);
                    });
                });
            }));
        });
    }

    public void openRateMapInventory(Player player) {
        new InventoryCreator(false, 27, new BukkitTranslation(MAP_RATING_ITEM_DISPLAY_NAME).get(player), inventoryCreator -> {
            inventoryCreator.setPlaceholders(Material.WHITE_STAINED_GLASS_PANE, true);

            this.getRating(this.getWildcardMapName(player), customRatedMap -> {
                for(RatingType ratingType : RatingType.values()) {
                    ItemCreator itemCreator = new ItemCreator(ratingType.getMaterial()).setDisplayName(player, ratingType.getTranslationProperty());

                    List<UUID> uuids = customRatedMap.getPlayerVotes(ratingType);

                    if(uuids != null && uuids.contains(player.getUniqueId())) {
                        itemCreator.enchantItemForAppearance();
                    }

                    inventoryCreator.setItem(ratingType.getSlot(), itemCreator.apply());
                }
            });

            inventoryCreator.apply(player);
        });
    }

    public String getWildcardMapName(Player player) {
        String mapName = this.mapRatingSystemPlugin.getRatedMap();

        return mapName.equalsIgnoreCase("*") ? player.getWorld().getName() : mapName;
    }
}
