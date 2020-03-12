package com.github.fo2rist.mclaren.utils;

import android.content.res.Resources;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.github.fo2rist.mclaren.BuildConfig;
import java.util.Locale;

/**
 * Provides access to dynamically resolved resources.
 * All methods that resolve URIs don not validate if the resource exists.
 */
public class ResourcesUtils {
    @NonNull
    public static Uri getCircuitImageUriById(@NonNull String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s", id));
    }

    @NonNull
    public static Uri getCircuitDetailedImageUriById(@NonNull String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s", id));
    }

    @NonNull
    public static Uri getCircuitDrsImageUriById(@NonNull String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s_drs", id));
    }

    @NonNull
    public static Uri getCircuitSectorsImageUriById(@NonNull String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s_sectors", id));
    }

    @NonNull
    public static Uri getCircuitTurnsImageUriById(@NonNull String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s_turns", id));
    }

    @NonNull
    public static Uri getDriverPortraitResUri(String driverId) {
        return getDrawableUri("driver_" + driverId);
    }

    @NonNull
    private static Uri getDrawableUri(@NonNull String drawableName) {
        return Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/" + drawableName);
    }

    public static int getHelmetResId(@NonNull Resources resources, @NonNull String driverId) {
        return resources.getIdentifier("helmet_" + driverId, "drawable", BuildConfig.APPLICATION_ID);
    }

    /**
     * Resolves resource URI into resource ID.
     * @param resourceUri must be correct URI path with app package, res type and name
     * @return resource ID or 0 if not found.
     */
    @VisibleForTesting
    static int resourceUriToId(@NonNull Resources resources, @NonNull Uri resourceUri) {
        if (resourceUri.getPath() == null) {
            return 0;
        }
        String[] pathParts = resourceUri.getPath().split("/");
        if (pathParts.length < 3) {
            return 0;
        }
        String applicationPackage = resourceUri.getHost();
        String resourceType = pathParts[1]; //first part is empty b/c path starts with /
        StringBuilder name = new StringBuilder(pathParts[2]);
        for (int i=3; i<pathParts.length; i++) {
            name.append("/");
            name.append(pathParts[i]);
        }
        return resources.getIdentifier(name.toString(), resourceType, applicationPackage);
    }
}
