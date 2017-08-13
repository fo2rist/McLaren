package com.github.fo2rist.mclaren.web.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Date;
import timber.log.Timber;

/**
 * Parse raw network responses into web-data models.
 */
public class ResponseParser {
    private static final McLarenFeed EMPTY_FEED = new McLarenFeed();

    private static Gson plainGson = new Gson();
    /** Silent Date deserializer that ignores errors. */
    private static final JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return plainGson.fromJson(jsonElement.getAsString(), Date.class);
            } catch (JsonSyntaxException exc) {
                return null;
            }
        }
    };

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, dateJsonDeserializer)
            .create();

    private ResponseParser() {
    }

    public static McLarenFeed parseFeed(String responseBody) {
        try {
            McLarenFeed feed = gson.fromJson(responseBody, McLarenFeed.class);
            return feed != null ? feed : EMPTY_FEED;
        } catch (JsonSyntaxException exc) {
            Timber.e(exc);
            return EMPTY_FEED;
        }
    }
}
