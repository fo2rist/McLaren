package com.github.fo2rist.mclaren.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ImageUrlTest {
    private static final String FIXED_SIZE_URL = "http://url.com/image.jpg";
    private static final String DYNAMIC_SIZE_URL = String.format("http://url.com/image_%s$1_%s$2.jpg",
            ImageUrl.DYNAMIC_WIDTH_PLACEHOLDER, ImageUrl.DYNAMIC_HEIGHT_PLACEHOLDER);
    private static final String ORIGINAL_SIZE_URL = "http://url.com/image_original.jpg";
    private static final String MEDIUM_SIZE_URL = "http://url.com/image_medium.jpg";
    private static final String SMALL_SIZE_URL = "http://url.com/image_small.jpg";
    private static final Size SIZE = Size.valueOf(800, 600);
    private static final Size MEDIUM_SIZE = Size.valueOf(600, 400);
    private static final Size SMALL_SIZE = Size.valueOf(300, 200);

    @Test
    public void testFixedSizeUrlConstruction() throws Exception {
        ImageUrl imageUrl = ImageUrl.createUrl(FIXED_SIZE_URL, SIZE);

        assertTrue(imageUrl instanceof ImageUrl.FixedSizeUrl);
        assertEquals(SIZE, imageUrl.getSize());
    }

    @Test
    public void testDynamicSizeUrlConstruction() throws Exception {
        ImageUrl imageUrl = ImageUrl.createUrl(DYNAMIC_SIZE_URL, SIZE);

        assertTrue(imageUrl instanceof ImageUrl.DynamicSizeUrl);
        assertEquals(SIZE, imageUrl.getSize());
    }

    @Test
    public void testMultiSizeUrlConstruction() throws Exception {
        ImageUrl imageUrl = ImageUrl.createUrl(ORIGINAL_SIZE_URL, SIZE, MEDIUM_SIZE_URL, MEDIUM_SIZE, SMALL_SIZE_URL, SMALL_SIZE);

        assertTrue(imageUrl instanceof ImageUrl.MultipleSizeUrl);
        assertEquals(SIZE, imageUrl.getSize());
    }
}
