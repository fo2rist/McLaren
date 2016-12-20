package fo2rist.github.com.mclaren.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent single item in the feed.
 */
public class FeedItem implements Serializable {
    public enum Type {
        Photo,
        Gallery,
        Text
    }

    public enum SourceType {
        Twitter,
        Instagram,
        Other
    }

    Date dateTime;
    Type type;
    @NonNull
    String text;
    SourceType sourceType;
    String sourceName;
    List<URL> imageUrls = new ArrayList<URL>();

    public Date getDateTime() {
        return dateTime;
    }

    public Type getType() {
        return type;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getSourceName() {
        return sourceName;
    }

    @NonNull
    public List<URL> getImageUrls() {
        return imageUrls;
    }

    /**
     * Get single image. Should be null for text posts.
     * @return the first image if there are many or null
     */
    @Nullable
    public URL getImage() {
        if (imageUrls.isEmpty()) {
            return null;
        } else {
            return imageUrls.get(0);
        }
    }
}
