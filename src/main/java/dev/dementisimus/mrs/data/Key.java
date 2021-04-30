package dev.dementisimus.mrs.data;

/**
 * Copyright (c) by dementisimus
 *
 * Class Key @ MapRatingSystem
 *
 * @author dementisimus
 * @since 23.06.2020:20:36
 */
public enum Key {
    MAP("map"),
    PLAYER_VOTES("playerVotes");

    public final String value;

    Key(String value) {
        this.value = value;
    }
}
