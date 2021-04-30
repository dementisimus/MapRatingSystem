package dev.dementisimus.mrs.listeners;

import dev.dementisimus.capi.core.core.CoreAPI;
import dev.dementisimus.capi.core.events.bukkit.BukkitPrintCustomInstructionsMessageEvent;
import dev.dementisimus.capi.core.setup.SetUpState;
import dev.dementisimus.capi.core.translations.Translation;
import dev.dementisimus.mrs.MapRatingSystem;
import dev.dementisimus.mrs.setup.AdditionalSetUpState;
import dev.dementisimus.mrs.translation.Translations;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
/**
 * Copyright (c) by dementisimus
 *
 * Class SetUpStatePrintInstructionsListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 13.07.2020:12:29
 */
public class SetUpStatePrintInstructionsListener implements Listener {

    private final MapRatingSystem mapRatingSystem;
    private final CoreAPI coreAPI;

    public SetUpStatePrintInstructionsListener(MapRatingSystem mapRatingSystem) {
        this.mapRatingSystem = mapRatingSystem;
        this.coreAPI = mapRatingSystem.getCoreAPI();
    }

    @EventHandler
    public void on(BukkitPrintCustomInstructionsMessageEvent event) {
        String state = event.getCurrentState();
        SetUpState setUpState = this.coreAPI.getSetUpState();
        if(state.equalsIgnoreCase(AdditionalSetUpState.USE_STANDALONE_VERSION.name())) {
            System.out.println(new Translation(Translations.CONSOLE_USE_STANDALONE_VERSION.id).get(true));
            System.out.println(new Translation(Translations.CONSOLE_USE_STANDALONE_VERSION_NOTE.id).get(true));
            setUpState.setCurrentSetUpState(AdditionalSetUpState.USE_STANDALONE_VERSION.name(), false);
        }else if(state.equalsIgnoreCase(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name())) {
            System.out.println(new Translation(Translations.CONSOLE_USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.id).get(true));
            setUpState.setCurrentSetUpState(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name(), false);
        }else if(state.equalsIgnoreCase(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name())) {
            System.out.println(new Translation(Translations.CONSOLE_WORLD_NAME_FOR_RATING.id).get(true));
            setUpState.setCurrentSetUpState(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name(), false);
        }
    }

}
