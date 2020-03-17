package com.github.fo2rist.mclaren.utils

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.lang.RuntimeException

/**
 * Safely parse JSON with Gson.
 * DOESN'T validate parsed object completeness, missing fields set by Gson to null.
 * @return null if object can not be parsed.
 */
fun <T> parseJson(json: String, objectTypeToken: TypeToken<T>): T? {
    return try {
        Gson().fromJson<T>(json, objectTypeToken.type)
    } catch (exc: JsonParseException) {
        return null
    } catch (exc: JsonSyntaxException) {
        return null
    }
}
