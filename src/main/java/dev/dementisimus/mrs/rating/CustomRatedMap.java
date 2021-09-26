package dev.dementisimus.mrs.rating;

import com.google.common.reflect.TypeToken;
import dev.dementisimus.mrs.MapRatingSystemPlugin;
import dev.dementisimus.mrs.api.RatedMap;
import dev.dementisimus.mrs.api.RatingType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static dev.dementisimus.mrs.MapRatingSystemPlugin.DataSource;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class CustomRatedMap @ MapRatingSystem
 *
 * @author dementisimus
 * @since 25.09.2021:18:32
 */
public class CustomRatedMap implements RatedMap {

    private static final Type PLAYER_VOTES_TYPE_TOKEN = new TypeToken<Map<String, List<String>>>() {}.getType();

    private final Map<RatingType, List<UUID>> playerVotes = new HashMap<>();

    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private String mapName;

    protected static CustomRatedMap fromDocument(Document document) {
        CustomRatedMap customRatedMap = new CustomRatedMap();

        if(document != null) {
            if(document.containsKey(DataSource.MAP)) {
                customRatedMap.setMapName(document.getString(DataSource.MAP));
            }

            if(document.containsKey(DataSource.PLAYER_VOTES)) {
                Map<String, List<String>> rawPlayerVotes = (Map<String, List<String>>) document.get(DataSource.PLAYER_VOTES, PLAYER_VOTES_TYPE_TOKEN);

                rawPlayerVotes.forEach((rawRatingType, rawUUIDs) -> {
                    List<UUID> uuids = new ArrayList<>();

                    for(String rawUUID : rawUUIDs) {
                        uuids.add(UUID.fromString(rawUUID));
                    }

                    customRatedMap.setVotes(RatingType.valueOf(rawRatingType), uuids);
                });
            }
        }

        return customRatedMap;
    }

    @Override
    public List<UUID> getPlayerVotes(RatingType ratingType) {
        List<UUID> uuids = new ArrayList<>();

        if(this.playerVotes.get(ratingType) != null) {
            uuids = this.playerVotes.get(ratingType);
        }

        return uuids;
    }

    protected void setVote(UUID uuid, RatingType ratingType) {
        this.playerVotes.forEach((type, uuids) -> {
            uuids.remove(uuid);
            this.playerVotes.put(type, uuids);
        });

        List<UUID> votes = this.playerVotes.get(ratingType) == null ? new ArrayList<>() : this.playerVotes.get(ratingType);

        votes.add(uuid);

        this.playerVotes.put(ratingType, votes);
    }

    protected void setVotes(RatingType ratingType, List<UUID> uuids) {
        this.playerVotes.put(ratingType, uuids);
    }

    protected Document toDocument() {
        Document document = new Document();

        document.put(MapRatingSystemPlugin.DataSource.MAP, this.mapName);
        document.put(MapRatingSystemPlugin.DataSource.PLAYER_VOTES, this.toSerializedPlayerVotes());

        return document;
    }

    protected Map<String, List<String>> toSerializedPlayerVotes() {
        Map<String, List<String>> serializedPlayerVotes = new HashMap<>();

        this.playerVotes.forEach(((ratingType, uuids) -> {
            List<String> serializedUUIDs = new ArrayList<>();

            for(UUID uuid : uuids) {
                serializedUUIDs.add(uuid.toString());
            }

            serializedPlayerVotes.put(ratingType.name(), serializedUUIDs);
        }));

        return serializedPlayerVotes;
    }
}
