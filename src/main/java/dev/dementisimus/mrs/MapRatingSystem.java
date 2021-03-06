package dev.dementisimus.mrs;

import dev.dementisimus.capi.core.config.Config;
import dev.dementisimus.capi.core.core.BukkitCoreAPI;
import dev.dementisimus.capi.core.core.CoreAPI;
import dev.dementisimus.capi.core.helpers.bukkit.BukkitHelper;
import dev.dementisimus.capi.core.setup.DefaultSetUpState;
import dev.dementisimus.capi.core.translations.CoreAPITranslations;
import dev.dementisimus.capi.core.translations.Translation;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.commands.COMMAND_maprating;
import dev.dementisimus.mrs.listeners.SetupDoneListener;
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
        this.init();
    }

    private void init() {
        this.bukkitCoreAPI = new BukkitCoreAPI(this);
        this.coreAPI = this.bukkitCoreAPI.getCoreAPI();
        BukkitHelper.registerEvents(this, new Listener[]{new SetupDoneListener(this), new SetUpStateChangeListener(this), new SetUpStatePrintInstructionsListener(this)});
        this.coreAPI.prepareInit(new String[]{DefaultSetUpState.LANGUAGE.name(), AdditionalSetUpState.USE_STANDALONE_VERSION.name(), DefaultSetUpState.STORAGE_TYPE.name(), DefaultSetUpState.MONGODB_CONNECTION_STRING.name(), DefaultSetUpState.MONGODB_DATABASE.name(), DefaultSetUpState.MARIADB_HOST.name(), DefaultSetUpState.MARIADB_PORT.name(), DefaultSetUpState.MARIADB_USER.name(), DefaultSetUpState.MARIADB_PASSWORD.name()}, new ResourceBundle[]{getBundle(this.coreAPI.getBaseName(), ENGLISH), getBundle(this.coreAPI.getBaseName(), GERMAN)}, capi -> capi.enableDatabaseUsage(new String[]{MAPRATINGS.value}, new String[]{RATINGROW.value}));
        this.coreAPI.init(initialized -> {
            this.createMapRatingAPIObject();
        });
    }

    public void createMapRatingAPIObject() {
        new Config(this.getCoreAPI().getConfigFile()).read(jsonDocument -> {
            if(jsonDocument != null) {
                boolean useStandaloneVersion = jsonDocument.getBoolean(AdditionalSetUpState.USE_STANDALONE_VERSION.name());
                if(useStandaloneVersion) {
                    boolean useDefaultWorld = jsonDocument.getBoolean(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name());
                    String mapName = Bukkit.getWorlds().get(0).getName();
                    if(!useDefaultWorld) {
                        mapName = jsonDocument.getString(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name());
                    }
                    new MapRating(mapName, new RatingType[]{RatingType.TERRIBLE, RatingType.BAD, RatingType.OKAY, RatingType.GOOD, RatingType.AMAZING}, new Integer[]{0, 2, 4, 6, 8}, 4, Material.MAP);
                }else {
                    String pluginName = this.getName();
                    AtomicBoolean pluginUsed = new AtomicBoolean(false);
                    for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                        PluginDescriptionFile pluginDescriptionFile = plugin.getDescription();
                        if(pluginDescriptionFile.getLoadBefore().contains(pluginName) || pluginDescriptionFile.getDepend().contains(pluginName) || pluginDescriptionFile.getSoftDepend().contains(pluginName)) {
                            pluginUsed.set(true);
                            break;
                        }
                    }
                    if(!pluginUsed.get()) {
                        System.out.println(new Translation(CoreAPITranslations.CONSOLE_ERROR_NO_PLUGIN_FOUND.id).get(true));
                        Bukkit.getPluginManager().disablePlugin(this);
                    }
                }
                MapRatingInitializationQueue.executeCallbackInQueue(mapRating -> {
                    if(mapRating != null) {
                        this.mapRating = mapRating;
                        this.bukkitCoreAPI.addListenersToRegisterOnSetUpDone(new Listener[]{new InventoryClickListener(this), new PlayerLanguageListener(this), new PlayerInteractListener(this)});
                        this.bukkitCoreAPI.addCommandToRegisterOnSetUpDone("maprating", new COMMAND_maprating(this));
                        this.bukkitCoreAPI.addCommandToRegisterOnSetUpDone("mr", new COMMAND_maprating(this));
                        this.bukkitCoreAPI.registerCommandsAndListeners();
                    }
                });
            }
        });
    }

    public BukkitCoreAPI getBukkitCoreAPI() {
        return this.bukkitCoreAPI;
    }

    public CoreAPI getCoreAPI() {
        return this.coreAPI;
    }

    public MapRating getMapRating() {
        return this.mapRating;
    }
}