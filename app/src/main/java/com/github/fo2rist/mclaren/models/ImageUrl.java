package com.github.fo2rist.mclaren.models;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

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
        public String getUrl(int width, int height) {
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
            this.mediumSizeUrl = mediumSizeUrl;
            this.mediumSize = mediumSize;
            this.smallSizeUrl = smallSizeUrl;
            this.smallSize = smallSize;
        }

        @NonNull
        @Override
        public String getUrl() {
            return originalSizeUrl;
        }

        @NonNull
        @Override
        public String getUrl(int width, int height) {
            return originalSizeUrl; //TODO
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
        @NonNull private final String url;
        @NonNull private final Size size;

        private DynamicSizeUrl(@NonNull String url, @NonNull Size size) {
            this.url = url;
            this.size = size;
        }

        @NonNull
        @Override
        public String getUrl() {
            return generateDynamicUrlForSize(size.width, size.height); //TODO support unknown size
        }

        @NonNull
        @Override
        public String getUrl(int width, int height) {
            return generateDynamicUrlForSize(width, height);
        }

        private String generateDynamicUrlForSize(int width, int height) {
            //TODO limit size by original size
            return url.replace(DYNAMIC_WIDTH_PLACEHOLDER, String.valueOf(width))
                    .replace(DYNAMIC_HEIGHT_PLACEHOLDER, String.valueOf(height));
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

    public static ImageUrl createUrl(@NonNull String url, @NonNull Size size) {
        if (isDynamic(url)) {
            return new DynamicSizeUrl(url, size);
        } else {
            return new FixedSizeUrl(url,size);
        }
    }


    public static ImageUrl createUrl(@NonNull String originalSizeUrl, @NonNull Size originalSize,
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
    public abstract String getUrl(int width, int height);

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
