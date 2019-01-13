package com.github.fo2rist.mclaren.web;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class SafeJsonParserTest {
    private static final String EMPTY_STRING = "";
    private static final String EMPTY_JSON_ARRAY = "[]";
    private static final String EMPTY_JSON_OBJECT = "{}";
    private static final String TWO_ITEM_JSON_ARRAY = "[1, 2]";
    private static final String TWO_FIELD_JSON_OBJECT = "{\"a\":1, \"b\":2}";

    private SafeJsonParser<ArrayList> arrayParser = new SafeJsonParser<>(ArrayList.class);
    private SafeJsonParser<HashMap> objectParser = new SafeJsonParser<>(HashMap.class);

    @Test
    public void testNullParsedToCorrectEmptyObject() {
        HashMap object = objectParser.parse(null);

        assertTrue(object.isEmpty());
    }

    @Test
    public void testEmptyStringParsed() {
        ArrayList array = arrayParser.parse(EMPTY_STRING);
        HashMap object = objectParser.parse(EMPTY_STRING);

        assertTrue(array.isEmpty());
        assertTrue(object.isEmpty());
    }

    @Test
    public void testEmptyJsonArrayParsed() {
        ArrayList array = arrayParser.parse(EMPTY_JSON_ARRAY);
        HashMap object = objectParser.parse(EMPTY_JSON_OBJECT);

        assertTrue(array.isEmpty());
        assertTrue(object.isEmpty());
    }

    @Test
    public void testIncorrectJsonParsed() {
        ArrayList array = arrayParser.parse(TWO_FIELD_JSON_OBJECT); //should be array
        HashMap object = objectParser.parse(TWO_ITEM_JSON_ARRAY); //should be object

        assertTrue(array.isEmpty());
        assertTrue(object.isEmpty());
    }

    @Test
    public void testCorrectJsonParsed() {
        ArrayList array = arrayParser.parse(TWO_ITEM_JSON_ARRAY);
        HashMap object = objectParser.parse(TWO_FIELD_JSON_OBJECT);

        assertEquals(2, array.size());
        assertEquals(2, object.size());
    }
}
