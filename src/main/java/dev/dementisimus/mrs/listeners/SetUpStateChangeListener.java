package dev.dementisimus.mrs.listeners;

import dev.dementisimus.capi.core.core.CoreAPI;
import dev.dementisimus.capi.core.events.bukkit.BukkitChangeSetUpStateEvent;
import dev.dementisimus.capi.core.helpers.SetUpHelper;
import dev.dementisimus.capi.core.setup.DefaultSetUpState;
import dev.dementisimus.capi.core.setup.SetUpData;
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
 * Class SetUpStateChangeListener @ MapRatingSystem
 *
 * @author dementisimus
 * @since 13.07.2020:13:03
 */
public class SetUpStateChangeListener implements Listener {

    private final MapRatingSystem mapRatingSystem;
    private final CoreAPI coreAPI;

    public SetUpStateChangeListener(MapRatingSystem mapRatingSystem) {
        this.mapRatingSystem = mapRatingSystem;
        this.coreAPI = mapRatingSystem.getCoreAPI();
    }

    @EventHandler
    public void on(BukkitChangeSetUpStateEvent event) {
        String currentState = event.getCurrentState();
        SetUpState setUpState = this.coreAPI.getSetUpState();
        SetUpData setUpData = this.coreAPI.getSetUpData();
        SetUpHelper setUpHelper = new SetUpHelper(currentState, event.getIssuedCommand(), setUpState, setUpData);
        if(currentState.equalsIgnoreCase(AdditionalSetUpState.USE_STANDALONE_VERSION.name())) {
            setUpHelper.evalYesOrNo(Translations.CONSOLE_USE_STANDALONE_VERSION.id, AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING, true, false);
            Boolean yesOrNo = SetUpHelper.getYesNoFromCommand(event.getIssuedCommand());
            if(yesOrNo != null) {
                if(yesOrNo) {
                    setUpState.setCurrentSetUpState(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name(), true);
                }else {
                    goToStorageSetup();
                }
            }
        }else if(currentState.equalsIgnoreCase(AdditionalSetUpState.USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.name())) {
            setUpHelper.evalYesOrNo(Translations.CONSOLE_USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING.id, AdditionalSetUpState.WORLD_NAME_FOR_RATING, true, false);
            Boolean yesOrNo = SetUpHelper.getYesNoFromCommand(event.getIssuedCommand());
            if(yesOrNo != null) {
                if(yesOrNo) {
                    goToStorageSetup();
                }else {
                    setUpState.setCurrentSetUpState(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name(), true);
                }
            }
        }else if(currentState.equalsIgnoreCase(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name())) {
            if(event.getIssuedCommand() != null) {
                setUpData.setData(AdditionalSetUpState.WORLD_NAME_FOR_RATING.name(), event.getIssuedCommand());
                goToStorageSetup();
            }else {
                System.out.println(new Translation(Translations.CONSOLE_WORLD_NAME_FOR_RATING.id).get(true));
            }
        }
    }

    private void goToStorageSetup() {
        this.coreAPI.getSetUpState().setCurrentSetUpState(DefaultSetUpState.STORAGE_TYPE.name(), true);
    }

}
