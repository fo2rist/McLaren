package com.github.fo2rist.mclaren.models;

import java.io.Serializable;

public class Size implements Serializable {
    public static final Size UNKNOWN = new Size(-1, -1);

    final int width;
    final int height;

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
    public String toString() {
        if (this == UNKNOWN) {
            return "[Unknown Size]";
        } else {
            return String.format("[%s, %s]", width, height);
        }
    }
}
