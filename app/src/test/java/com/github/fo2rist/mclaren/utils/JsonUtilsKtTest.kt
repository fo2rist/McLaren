package com.github.fo2rist.mclaren.utils

import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

private const val VALUE_1 = 1;
private const val VALUE_2 = "V2"
private const val DUMMY_OBJECT_JSON = """{"field1": $VALUE_1, "field2": "$VALUE_2"}"""
private val DUMMY_CLASS_TOKEN = object : TypeToken<DummyClass>() {}

private data class DummyClass(
    val field1: Int,
    val field2: String
)

class JsonUtilsKtTest {

    @Test
    fun `parseJson can parse JSON object `() {
        val parsedObject = parseJson(DUMMY_OBJECT_JSON, DUMMY_CLASS_TOKEN)

        assertEquals(VALUE_1, parsedObject?.field1)
        assertEquals(VALUE_2, parsedObject?.field2)
    }

    @Test
    fun `parseJson returns null on wrong data type`() {
        val parsedObject = parseJson("""[]""", DUMMY_CLASS_TOKEN)

        assertNull(parsedObject)
    }

    @Test
    fun `parseJson returns null on malformed JSON`() {
        val parsedObject = parseJson("""{"field1":""", DUMMY_CLASS_TOKEN)

        assertNull(parsedObject)
    }
}
