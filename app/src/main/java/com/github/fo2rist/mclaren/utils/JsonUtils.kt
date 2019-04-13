package com.github.fo2rist.mclaren.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @throws com.google.gson.JsonParseException see [Gson.fromJson] for details.
 */
fun <T> parseJson(json: String, objectTypeToken: TypeToken<T>): T? {
    return Gson().fromJson<T>(json, objectTypeToken.type)
}
