package dev.dementisimus.mrs;

import dev.dementisimus.capi.core.BukkitCoreAPI;
import dev.dementisimus.capi.core.database.properties.DataSourceProperty;
import dev.dementisimus.capi.core.database.types.SQLTypes;
import dev.dementisimus.capi.core.setup.SetupManager;
import dev.dementisimus.capi.core.setup.states.type.SetupStateString;
import dev.dementisimus.mrs.rating.CustomMapRating;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

import static dev.dementisimus.mrs.MapRatingSystemPlugin.ExtraSetupStates.RATED_MAP;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class MapRatingSystemPlugin @ MapRatingSystemPlugin
 *
 * @author dementisimus
 * @since 18.09.2021:14:17
 */
public class MapRatingSystemPlugin extends JavaPlugin {

    @Getter
    @Setter
    private static MapRatingSystemPlugin mapRatingSystemPlugin;

    @Getter private BukkitCoreAPI bukkitCoreAPI;
    @Getter private CustomMapRating customMapRating;
    @Getter private String ratedMap;

    @Override
    public void onEnable() {
        MapRatingSystemPlugin.setMapRatingSystemPlugin(this);

        this.bukkitCoreAPI = new BukkitCoreAPI(this);

        this.bukkitCoreAPI.enableMainSetupStates();

        this.bukkitCoreAPI.enableExtraSetupState(RATED_MAP);

        this.bukkitCoreAPI.enableDatabase(DataSource.PROPERTY);

        this.bukkitCoreAPI.prepare(coreAPIInjector -> {
            coreAPIInjector.addInjectionModule(MapRatingSystemPlugin.class, this);

            this.bukkitCoreAPI.init(() -> {

                SetupManager setupManager = this.bukkitCoreAPI.getSetupManager();

                this.ratedMap = setupManager.getSetupState(RATED_MAP).getString();

                this.customMapRating = new CustomMapRating();

                coreAPIInjector.addInjectionModule(CustomMapRating.class, this.getCustomMapRating());
            });
        });
    }

    public static class ExtraSetupStates {

        public static final SetupStateString RATED_MAP = new SetupStateString("RATED_MAP", "setup.extra.state.rated.map");
    }

    public static class Strings {

        public static final String PREFIX = "§c§lMap§f§lRating §7§l»";

    }

    public static class DataSource implements DataSourceProperty {

        public static final DataSource PROPERTY = new DataSource();

        public static final String MAP = "map";
        public static final String PLAYER_VOTES = "playerVotes";

        @Override
        public String name() {
            return "mapRatings";
        }

        @Override
        public Map<String, String> fields() {
            return Map.ofEntries(Map.entry(MAP, SQLTypes.LONGTEXT), Map.entry(PLAYER_VOTES, SQLTypes.LONGTEXT));
        }
    }

    public static class Translations {

        public static final String MAP_RATINGS_INVENTORY_TITLE = "map.ratings.inventory.title";
        public static final String MAP_RATING_ITEM_DISPLAY_NAME = "map.rating.item.display.name";

        public static final String RATE_MAP_SUCCESS = "rate.map.success";
        public static final String RATE_MAP_FAILURE = "rate.map.failure";

    }
}
