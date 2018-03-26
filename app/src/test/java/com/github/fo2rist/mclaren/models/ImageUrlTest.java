package com.github.fo2rist.mclaren.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ImageUrlTest {
    private static final String FIXED_SIZE_URL = "http://url.com/image.jpg";
    private static final String DYNAMIC_SIZE_URL = String.format("http://url.com/image_%s$1_%s$2.jpg",
            ImageUrl.DYNAMIC_WIDTH_PLACEHOLDER, ImageUrl.DYNAMIC_HEIGHT_PLACEHOLDER);
    private static final String ORIGINAL_SIZE_URL = "http://url.com/image_original.jpg";
    private static final String MEDIUM_SIZE_URL = "http://url.com/image_medium.jpg";
    private static final String SMALL_SIZE_URL = "http://url.com/image_small.jpg";
    private static final Size OVERSIZE = Size.valueOf(4000, 3000);
    private static final Size ORIGINAL_SIZE = Size.valueOf(800, 600);
    private static final Size MEDIUM_SIZE = Size.valueOf(600, 400);
    private static final Size SMALL_SIZE = Size.valueOf(300, 200);
    private static final Size UNDERSIZE = Size.valueOf(15, 10);

    private ImageUrl imageUrlFromFixedAddress;
    private ImageUrl imageUrlFromDynamicAddress;
    private ImageUrl imageUrlMultiAddresses;

    @Before
    public void setUp() throws Exception {
        imageUrlFromFixedAddress = ImageUrl.create(FIXED_SIZE_URL, ORIGINAL_SIZE);
        imageUrlFromDynamicAddress = ImageUrl.create(DYNAMIC_SIZE_URL, ORIGINAL_SIZE);
        imageUrlMultiAddresses = ImageUrl.create(
                ORIGINAL_SIZE_URL, ORIGINAL_SIZE,
                MEDIUM_SIZE_URL, MEDIUM_SIZE,
                SMALL_SIZE_URL, SMALL_SIZE);
    }

    @Test
    public void testFixedSizeUrlConstruction() throws Exception {
        assertTrue(imageUrlFromFixedAddress instanceof ImageUrl.FixedSizeUrl);
        assertEquals(ORIGINAL_SIZE, imageUrlFromFixedAddress.getSize());
    }

    @Test
    public void testFixedSizeUrlAlwaysSame() throws Exception {
        String originalUrl = imageUrlFromFixedAddress.getUrl();

        String smallUrl = imageUrlFromFixedAddress.getUrl(SMALL_SIZE);
        assertEquals(originalUrl, smallUrl);

        String mediumUrl = imageUrlFromFixedAddress.getUrl(MEDIUM_SIZE);
        assertEquals(originalUrl, mediumUrl);
    }

    @Test
    public void testDynamicSizeUrlConstruction() throws Exception {
        assertTrue(imageUrlFromDynamicAddress instanceof ImageUrl.DynamicSizeUrl);
        assertEquals(ORIGINAL_SIZE, imageUrlFromDynamicAddress.getSize());
    }

    @Test
    public void testDynamicSizeUrlGeneration() throws Exception {
        testDynamicUrlForSize(imageUrlFromDynamicAddress, ORIGINAL_SIZE, ORIGINAL_SIZE);
        testDynamicUrlForSize(imageUrlFromDynamicAddress, MEDIUM_SIZE, MEDIUM_SIZE);
        testDynamicUrlForSize(imageUrlFromDynamicAddress, SMALL_SIZE, SMALL_SIZE);
    }

    private void testDynamicUrlForSize(ImageUrl imageUrl, Size requestedSize, Size expectedSize) {
        String generatedUrl = imageUrl.getUrl(requestedSize);

        assertTrue(generatedUrl.contains(String.valueOf(expectedSize.width)));
        assertTrue(generatedUrl.contains(String.valueOf(expectedSize.height)));
    }

    @Test
    public void testDynamicSizeUrlDoesNotExceedOriginalSize() throws Exception {
        testDynamicUrlForSize(imageUrlFromDynamicAddress, OVERSIZE, ORIGINAL_SIZE);
    }

    @Test
    public void testDynamicSizeUrlDoesNotFailWhenImageSizeUnknown() throws Exception {
        ImageUrl unknownSizeImageUrl = ImageUrl.create(DYNAMIC_SIZE_URL, Size.UNKNOWN);

        String generatedDefaultImageUrl = unknownSizeImageUrl.getUrl();
        assertFalse(generatedDefaultImageUrl.contains(String.valueOf(Size.UNKNOWN.width)));

        String generatedMediumImageUrl = unknownSizeImageUrl.getUrl(MEDIUM_SIZE);
        assertFalse(generatedMediumImageUrl.contains(String.valueOf(Size.UNKNOWN.width)));
    }

    @Test
    public void testMultiSizeUrlConstruction() throws Exception {
        assertTrue(imageUrlMultiAddresses instanceof ImageUrl.MultipleSizeUrl);
        assertEquals(ORIGINAL_SIZE, imageUrlMultiAddresses.getSize());
    }

    @Test
    public void testMultiSizeUrlDoesNotGiveTooBigImage() throws Exception {
        assertEquals(ORIGINAL_SIZE_URL, imageUrlMultiAddresses.getUrl(ORIGINAL_SIZE));
        assertEquals(MEDIUM_SIZE_URL, imageUrlMultiAddresses.getUrl(MEDIUM_SIZE));
        assertEquals(SMALL_SIZE_URL, imageUrlMultiAddresses.getUrl(SMALL_SIZE));
    }

    @Test
    public void testMultiSizeUrlDoesNotExceedSizeLimits() throws Exception {
        assertEquals(ORIGINAL_SIZE_URL, imageUrlMultiAddresses.getUrl(OVERSIZE));
        assertEquals(SMALL_SIZE_URL, imageUrlMultiAddresses.getUrl(UNDERSIZE));
    }

    @Test
    public void testMultiSizeUrlDefaultsToOriginalUrlWhenSizeUnknonw() throws Exception {
        ImageUrl unknownSizeImageUrl = ImageUrl.create(ORIGINAL_SIZE_URL, Size.UNKNOWN,
                MEDIUM_SIZE_URL, Size.UNKNOWN,
                SMALL_SIZE_URL, Size.UNKNOWN);

        assertEquals(ORIGINAL_SIZE_URL, unknownSizeImageUrl.getUrl(ORIGINAL_SIZE));
        assertEquals(ORIGINAL_SIZE_URL, unknownSizeImageUrl.getUrl(SMALL_SIZE));
        assertEquals(ORIGINAL_SIZE_URL, unknownSizeImageUrl.getUrl(OVERSIZE));
        assertEquals(ORIGINAL_SIZE_URL, unknownSizeImageUrl.getUrl(UNDERSIZE));
    }
}
