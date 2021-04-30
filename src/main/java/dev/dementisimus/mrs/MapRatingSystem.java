package dev.dementisimus.mrs;

import dev.dementisimus.capi.core.config.Config;
import dev.dementisimus.capi.core.core.BukkitCoreAPI;
import dev.dementisimus.capi.core.core.CoreAPI;
import dev.dementisimus.capi.core.setup.DefaultSetUpState;
import dev.dementisimus.capi.core.translations.CoreAPITranslations;
import dev.dementisimus.capi.core.translations.Translation;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.commands.COMMAND_maprating;
import dev.dementisimus.mrs.listeners.InventoryClickListener;
import dev.dementisimus.mrs.listeners.PlayerInteractListener;
import dev.dementisimus.mrs.listeners.PlayerLanguageListener;
import dev.dementisimus.mrs.listeners.SetUpStateChangeListener;
import dev.dementisimus.mrs.listeners.SetUpStatePrintInstructionsListener;
import dev.dementisimus.mrs.maprating.MapRatingInitializationQueue;
import dev.dementisimus.mrs.setup.AdditionalSetUpState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static dev.dementisimus.mrs.data.Rows.RATINGROW;
import static dev.dementisimus.mrs.data.TablesOrCollections.MAPRATINGS;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.ResourceBundle.getBundle;

/**
 * Copyright (c) by dementisimus
 */

public class MapRatingSystem extends JavaPlugin {

    private BukkitCoreAPI bukkitCoreAPI;
    private CoreAPI coreAPI;
    private MapRating mapRating;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        bukkitCoreAPI = new BukkitCoreAPI(this);
        coreAPI = bukkitCoreAPI.getCoreAPI();
        coreAPI.prepareInit(new String[]{DefaultSetUpState.LANGUAGE.name(), AdditionalSetUpState.USE_STANDALONE_VERSION.name(),
                                         DefaultSetUpState.STORAGE_TYPE.name(), DefaultSetUpState.MONGODB_CONNECTION_STRING.name(),
                                         DefaultSetUpState.MONGODB_DATABASE.name(), DefaultSetUpState.MARIADB_HOST.name(),
                                         DefaultSetUpState.MARIADB_PORT.name(), DefaultSetUpState.MARIADB_USER.name(),
                                         DefaultSetUpState.MARIADB_PASSWORD.name()},
                            new ResourceBundle[]{getBundle(coreAPI.getBaseName(), ENGLISH), getBundle(coreAPI.getBaseName(), GERMAN)},
                            capi -> capi.enableDatabaseUsage(new String[]{MAPRATINGS.value}, new String[]{RATINGROW.value}).init(initialized -> {
                                createMapRatingAPIObject();
                                MapRatingInitializationQueue.executeCallbackInQueue(mapRating -> {
                                    if(mapRating != null) {
                                        this.mapRating = mapRating;
                                        bukkitCoreAPI.addListenersToRegisterOnSetUpDone(new Listener[]{new InventoryClickListener(this),
                                                                                                       new PlayerLanguageListener(this),
                                                                                                       new PlayerInteractListener(this),
                                                                                                       new SetUpStateChangeListener(this),
                                                                                                       new SetUpStatePrintInstructionsListener(this)});
                                        bukkitCoreAPI.addCommandToRegisterOnSetUpDone("maprating", new COMMAND_maprating(this));
                                        bukkitCoreAPI.addCommandToRegisterOnSetUpDone("mr", new COMMAND_maprating(this));
                                        bukkitCoreAPI.registerCommandsAndListeners();
                                    }
                                });
                            }));
    }

    private void createMapRatingAPIObject() {
        new Config(getCoreAPI().getConfigFile()).read(jsonDocument -> {
            if(jsonDocument != null) {
                boolean useStandaloneVersion = jsonDocument.getBoolean(AdditionalSetUpState.USE_STANDALONE_VERSION.name());
                if(useStandaloneVersion) {
                    boolean useDefaultWorld = jsonDocument.getBoolean(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name());
                    String mapName = Bukkit.getWorlds().get(0).getName();
                    if(!useDefaultWorld) {
                        mapName = jsonDocument.getString(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name());
                    }
                    new MapRating(mapName,
                                  new RatingType[]{RatingType.TERRIBLE, RatingType.BAD, RatingType.OKAY, RatingType.GOOD, RatingType.AMAZING},
                                  new Integer[]{0, 2, 4, 6, 8},
                                  4,
                                  Material.MAP);
                }else {
                    String pluginName = this.getName();
                    AtomicBoolean pluginUsed = new AtomicBoolean(false);
                    for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                        PluginDescriptionFile pluginDescriptionFile = plugin.getDescription();
                        if(pluginDescriptionFile.getLoadBefore().contains(pluginName) || pluginDescriptionFile.getDepend()
                                                                                                              .contains(pluginName) || pluginDescriptionFile
                                .getSoftDepend()
                                .contains(pluginName)) {
                            pluginUsed.set(true);
                            break;
                        }
                    }
                    if(!pluginUsed.get()) {
                        System.out.println(new Translation(CoreAPITranslations.CONSOLE_ERROR_NO_PLUGIN_FOUND.id).get(true));
                        Bukkit.getPluginManager().disablePlugin(this);
                    }
                }
            }
        });
    }

    public BukkitCoreAPI getBukkitCoreAPI() {
        return bukkitCoreAPI;
    }

    public CoreAPI getCoreAPI() {
        return coreAPI;
    }

    public MapRating getMapRating() {
        return mapRating;
    }
}