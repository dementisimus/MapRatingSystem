package dev.dementisimus.mrs.data;

/**
 * Copyright (c) by dementisimus
 */

public enum Rows {

    RATINGROW("map LONGTEXT, playerVotes LONGTEXT");

    public final String value;

    Rows(String value) {
        this.value = value;
    }

}
