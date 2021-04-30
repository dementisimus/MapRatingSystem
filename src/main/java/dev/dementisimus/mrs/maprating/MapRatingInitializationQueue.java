package dev.dementisimus.mrs.maprating;

import dev.dementisimus.capi.core.callback.Callback;
import dev.dementisimus.mrs.api.MapRating;
public class MapRatingInitializationQueue {

    private static MapRating mapRating;

    public static void setMapRating(MapRating mapRating) {
        MapRatingInitializationQueue.mapRating = mapRating;
    }

    public static void executeCallbackInQueue(Callback<MapRating> cb) {
        if(mapRating != null) {
            cb.done(mapRating);
            mapRating = null;
        }
    }
}
