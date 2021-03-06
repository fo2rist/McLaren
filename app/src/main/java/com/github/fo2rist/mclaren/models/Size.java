package com.github.fo2rist.mclaren.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Image size as a pair of width and height in pixels.
 */
public class Size implements Serializable {

    /** Special value for unknown size of [-1, -1]. */
    public static final Size UNKNOWN = new Size(-1, -1);

    public final int width;
    public final int height;

    public static Size valueOf(int width, int height) {
        if (width <= 0 || height <= 0) {
            return UNKNOWN;
        } else {
            return new Size(width, height);
        }
    }

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isUnknown() {
        return this.width == UNKNOWN.width || this.height == UNKNOWN.height;
    }

    /**
     * Whether this size exceeds given by at least one dimension.
     * @return comparison result or false if other size is unknown.
     */
    boolean exceeds(Size other) {
        return !other.isUnknown() && (this.width > other.width || this.height > other.height);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Size another = (Size) other;

        if (width != another.width) {
            return false;
        }
        return height == another.height;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    @Override
    @NonNull
    public String toString() {
        if (this == UNKNOWN) {
            return "[Unknown Size]";
        } else {
            return String.format("[%s, %s]", width, height);
        }
    }
}
