package dev.dementisimus.mrs.translation;

/**
 * Copyright (c) by dementisimus
 *
 * Class Translations @ MapRatingSystem
 *
 * @author dementisimus
 * @since 23.06.2020:14:41
 */
public enum Translations {

    MAP("map"),
    RATING("rating"),
    VOTES("votes"),
    SG_VOTES("sg.votes"),
    MAPRATING_ITEM_RATEMAP("maprating.item.rateMap"),
    MAPRATING_INVENTORY_TITLE("maprating.inventory.title"),
    MAPRATING_INVENTORY_ITEM_TYPE_TERRIBLE("maprating.inventory.item.type.terrible"),
    MAPRATING_INVENTORY_ITEM_TYPE_BAD("maprating.inventory.item.type.bad"),
    MAPRATING_INVENTORY_ITEM_TYPE_OKAY("maprating.inventory.item.type.okay"),
    MAPRATING_INVENTORY_ITEM_TYPE_GOOD("maprating.inventory.item.type.good"),
    MAPRATING_INVENTORY_ITEM_TYPE_AMAZING("maprating.inventory.item.type.amazing"),
    MAPRATING_INVENTORY_SLOTS_NOT_SET_COMPLETELY("maprating.inventory.slots.not.set.completely"),
    COMMAND_MAPRATING_HELP("command.maprating.help"),
    COMMAND_MAPRATING_MAP_NULL("command.maprating.map.null"),
    COMMAND_MAPRATING_RATINGTYPE_NOT_FOUND("command.maprating.ratingtype.not.found"),
    CONSOLE_USE_STANDALONE_VERSION("console.use.standalone.version"),
    CONSOLE_USE_DEFAULT_WORLD_AS_WORLD_NAME_FOR_RATING("console.use.default.world.as.world.name.for.rating"),
    CONSOLE_WORLD_NAME_FOR_RATING("console.world.name.for.rating"),
    CONSOLE_USE_STANDALONE_VERSION_NOTE("console.use.standalone.version.note");

    public final String id;

    Translations(String id) {
        this.id = id;
    }

}
