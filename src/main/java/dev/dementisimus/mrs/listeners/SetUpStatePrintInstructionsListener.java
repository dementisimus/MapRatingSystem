package dev.dementisimus.mrs.listeners;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.annotations.bukkit.BukkitSetupListener;
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
@BukkitSetupListener(additionalModulesToInject = {MapRatingSystem.class})
public class SetUpStatePrintInstructionsListener implements Listener {

    @Inject MapRatingSystem mapRatingSystem;

    @EventHandler
    public void on(BukkitPrintCustomInstructionsMessageEvent event) {
        String state = event.getCurrentState();
        SetUpState setUpState = this.mapRatingSystem.getCoreAPI().getSetUpState();
        if(state.equalsIgnoreCase(AdditionalSetUpState.USE_STANDALONE_VERSION.name())) {
            System.out.println(new Translation(Translations.CONSOLE_USE_STANDALONE_VERSION).get(true));
            System.out.println(new Translation(Translations.CONSOLE_USE_STANDALONE_VERSION_NOTE).get(true));
            setUpState.setCurrentSetUpState(AdditionalSetUpState.USE_STANDALONE_VERSION.name(), false);
        }else if(state.equalsIgnoreCase(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name())) {
            System.out.println(new Translation(Translations.CONSOLE_USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING).get(true));
            setUpState.setCurrentSetUpState(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name(), false);
        }else if(state.equalsIgnoreCase(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name())) {
            System.out.println(new Translation(Translations.CONSOLE_WORLD_NAME_FOR_RATING).get(true));
            setUpState.setCurrentSetUpState(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name(), false);
        }
    }

}
