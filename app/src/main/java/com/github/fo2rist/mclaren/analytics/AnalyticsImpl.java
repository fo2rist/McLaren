package com.github.fo2rist.mclaren.analytics;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.fo2rist.mclaren.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;
import javax.inject.Inject;
import timber.log.Timber;

public class AnalyticsImpl implements Analytics {

    @NonNull
    private FirebaseAnalytics firebaseAnalyticsInstance;

    @NonNull
    private Activity activity;

    @Inject
    AnalyticsImpl(Activity activity) {
        this.activity = activity;
        this.firebaseAnalyticsInstance = FirebaseAnalytics.getInstance(activity.getApplicationContext());
    }

    @Override
    public void overrideScreenName(Events.Screen screen) {
        firebaseAnalyticsInstance.setCurrentScreen(activity, screen.name(), null);
    }

    @Override
    public void logInternalAction(Events.Action event) {
        logNavigation(event.getActionName(), null);
    }

    @Override
    public void logExternalNavigation(Events.Screen event, String destination) {
        logNavigation(event.getScreenName(), destination);
    }

    private void logNavigation(@NonNull String eventName, @Nullable String destination) {
        Bundle viewEvent = new Bundle();
        viewEvent.putString(FirebaseAnalytics.Param.METHOD, eventName);

        if (destination != null) {
            viewEvent.putString(FirebaseAnalytics.Param.DESTINATION, truncateContentId(destination));
        }

        logDebug(eventName, destination);
        firebaseAnalyticsInstance.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewEvent);
    }

    private String truncateContentId(@NonNull String contentId) {
        // Parameter value limitation is 100 characters https://support.google.com/firebase/answer/9237506?hl=en
        if (contentId.length() > 98) {
            return contentId.substring(0, 98);
        } else {
            return contentId;
        }
    }

    private void logDebug(String eventName, String eventExtraData) {
        if (BuildConfig.DEBUG) {
            Timber.d("Analytics. Logged: '%s' with data '%s'", eventName, eventExtraData);
        }
    }
}
