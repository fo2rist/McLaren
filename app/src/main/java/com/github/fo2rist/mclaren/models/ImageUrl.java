package com.github.fo2rist.mclaren.models;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.io.Serializable;

/**
 * URL for image that contains image size info.
 */
public abstract class ImageUrl implements Serializable {
    public static final String DYNAMIC_WIDTH_PLACEHOLDER = "%WIDTH%";
    public static final String DYNAMIC_HEIGHT_PLACEHOLDER = "%HEIGHT%";

    @VisibleForTesting
    static class FixedSizeUrl extends ImageUrl {
        @NonNull private final String url;
        @NonNull private final Size size;

        private FixedSizeUrl(@NonNull String url, @NonNull Size size) {
            this.url = url;
            this.size = size;
        }

        @NonNull
        @Override
        public String getUrl() {
            return url;
        }

        @NonNull
        @Override
        public String getUrl(final int width, final int height) {
            return getUrl();
        }

        @NonNull
        @Override
        public Size getSize() {
            return size;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            FixedSizeUrl that = (FixedSizeUrl) other;

            if (!url.equals(that.url)) {
                return false;
            }
            return size.equals(that.size);
        }

        @Override
        public int hashCode() {
            int result = url.hashCode();
            result = 31 * result + size.hashCode();
            return result;
        }
    }

    @VisibleForTesting
    static class MultipleSizeUrl extends ImageUrl {
        @NonNull private final String originalSizeUrl;
        @NonNull private final Size originalSize;
        @NonNull private final String mediumSizeUrl;
        @NonNull private final Size mediumSize;
        @NonNull private final String smallSizeUrl;
        @NonNull private final Size smallSize;

        private MultipleSizeUrl(String originalSizeUrl, Size originalSize,
                String mediumSizeUrl, Size mediumSize,
                String smallSizeUrl, Size smallSize) {

            this.originalSizeUrl = originalSizeUrl;
            this.originalSize = originalSize;

            if (mediumSize == Size.UNKNOWN && mediumSizeUrl.isEmpty()) {
                //safety check for mallformed URLs, if the size if unknown and URL absent - default to original size
                this.mediumSizeUrl = originalSizeUrl;
                this.mediumSize = originalSize;
            } else {
                this.mediumSizeUrl = mediumSizeUrl;
                this.mediumSize = mediumSize;
            }

            if (smallSize == Size.UNKNOWN && smallSizeUrl.isEmpty()) {
                this.smallSizeUrl = originalSizeUrl;
                this.smallSize = originalSize;
            } else {
                this.smallSizeUrl = smallSizeUrl;
                this.smallSize = smallSize;
            }
        }

        @NonNull
        @Override
        public String getUrl() {
            return originalSizeUrl;
        }

        @NonNull
        @Override
        public String getUrl(final int width, final int height) {
            Size requestedSize = Size.valueOf(width, height);
            if (originalSize.isUnknown()) {
                return originalSizeUrl;
            } else if (requestedSize.exceeds(originalSize) || requestedSize.equals(originalSize)) {
                return originalSizeUrl;
            } else if (requestedSize.exceeds(mediumSize) || requestedSize.equals(mediumSize)) {
                return mediumSizeUrl;
            } else {
                return smallSizeUrl;
            }
        }

        @NonNull
        @Override
        public Size getSize() {
            return originalSize;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            MultipleSizeUrl that = (MultipleSizeUrl) other;

            if (!originalSizeUrl.equals(that.originalSizeUrl)) {
                return false;
            }
            if (!originalSize.equals(that.originalSize)) {
                return false;
            }
            if (!mediumSizeUrl.equals(that.mediumSizeUrl)) {
                return false;
            }
            if (!mediumSize.equals(that.mediumSize)) {
                return false;
            }
            if (!smallSizeUrl.equals(that.smallSizeUrl)) {
                return false;
            }
            return smallSize.equals(that.smallSize);
        }

        @Override
        public int hashCode() {
            int result = originalSizeUrl.hashCode();
            result = 31 * result + originalSize.hashCode();
            result = 31 * result + mediumSizeUrl.hashCode();
            result = 31 * result + mediumSize.hashCode();
            result = 31 * result + smallSizeUrl.hashCode();
            result = 31 * result + smallSize.hashCode();
            return result;
        }
    }

    @VisibleForTesting
    static class DynamicSizeUrl extends ImageUrl {
        static final private Size DEFAULT_MIN_SIZE = Size.valueOf(128, 128);

        @NonNull private final String url;
        @NonNull private final Size size;

        private DynamicSizeUrl(@NonNull String url, @NonNull Size size) {
            this.url = url;
            this.size = size;
        }

        @NonNull
        @Override
        public String getUrl() {
            return generateDynamicUrlForSize(size);
        }

        @NonNull
        @Override
        public String getUrl(final int width, final int height) {
            return generateDynamicUrlForSize(Size.valueOf(width, height));
        }

        private String generateDynamicUrlForSize(final Size requestedSize) {
            Size sizeToLoad = applySizeRestrictions(requestedSize);

            return url.replace(DYNAMIC_WIDTH_PLACEHOLDER, String.valueOf(sizeToLoad.width))
                    .replace(DYNAMIC_HEIGHT_PLACEHOLDER, String.valueOf(sizeToLoad.height));
        }

        private Size applySizeRestrictions(final Size requestedSize) {
            Size result = requestedSize;
            if (requestedSize.exceeds(size)) {
                //Original size is know check requested size is correct and does not exceed it
                // and apply top restriction
                result = size;
            }

            //If original size was unknown apply bottom restriction
            if (result.isUnknown()) {
                result = DEFAULT_MIN_SIZE;
            }

            return result;
        }

        @NonNull
        @Override
        public Size getSize() {
            return size;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            DynamicSizeUrl that = (DynamicSizeUrl) other;

            if (!url.equals(that.url)) {
                return false;
            }
            return size.equals(that.size);
        }

        @Override
        public int hashCode() {
            int result = url.hashCode();
            result = 31 * result + size.hashCode();
            return result;
        }
    }

    public static ImageUrl empty() {
        return new FixedSizeUrl("", Size.UNKNOWN);
    }

    public static ImageUrl create(@NonNull String url, @NonNull Size size) {
        if (isDynamic(url)) {
            return new DynamicSizeUrl(url, size);
        } else {
            return new FixedSizeUrl(url,size);
        }
    }


    public static ImageUrl create(@NonNull String originalSizeUrl, @NonNull Size originalSize,
            @NonNull String mediumSizeUrl, @NonNull Size mediumSize,
            @NonNull String smallSizeUrl, @NonNull Size smallSize) {
        return new MultipleSizeUrl(originalSizeUrl, originalSize,
                mediumSizeUrl, mediumSize,
                smallSizeUrl, smallSize);
    }

    private static boolean isDynamic(@NonNull String url) {
        return url.contains(DYNAMIC_WIDTH_PLACEHOLDER)
                || url.contains(DYNAMIC_HEIGHT_PLACEHOLDER);
    }

    public boolean isEmpty() {
        return getUrl().isEmpty();
    }

    /** Get default image address. */
    @NonNull
    public abstract String getUrl();

    /**
     * Get image address that works better for given size.
     */
    @NonNull
    public abstract String getUrl(final int width, final int height);

    /**
     * Same as {@link #getUrl(int, int)}.
     */
    @NonNull
    public String getUrl(Size size) {
        return getUrl(size.width, size.height);
    }

    /**
     * Get original image size.
     */
    @NonNull
    public abstract Size getSize();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getUrl() + " " + getSize();
    }
}
