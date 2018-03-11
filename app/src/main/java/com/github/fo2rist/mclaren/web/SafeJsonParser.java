package com.github.fo2rist.mclaren.web;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import timber.log.Timber;

/**
 * Base parser that can handle any json and return empty object in case of parsing failure.
 * @param <T> class to be parsed. Class should have empty public constructor to be used ass fallback in case of
 *          parsing error.
 */
public class SafeJsonParser<T> {
    private T emptyResult;
    private Class<? extends T> classOfResult;
    private static Gson gson = new Gson();

    public SafeJsonParser(Class<? extends T> classOfResult) {
        this.classOfResult = classOfResult;
        try {
            emptyResult = classOfResult.newInstance();
        } catch (InstantiationException | IllegalAccessException exc) {
            throw new IllegalArgumentException("", exc);
        }
    }

    /**
     * Parse json.
     * @return parsed object or empty instance of {@link T}
     */
    public T parse(String data) {
        try {
            T result = gson.fromJson(data, classOfResult);
            return result != null ? result : emptyResult;
        } catch (JsonSyntaxException exc) {
            Timber.e(exc);
            return emptyResult;
        }
    }
}