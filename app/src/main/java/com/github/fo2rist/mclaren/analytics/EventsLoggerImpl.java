package com.github.fo2rist.mclaren.analytics;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.fo2rist.mclaren.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;
import javax.inject.Inject;
import timber.log.Timber;

// TODO Firebase analytics automatically track screen views, so manual tracking is only required for items on screens.
//  and set screen names manually via https://firebase.google.com/docs/analytics/screenviews#java
//  2020.06.13
public class EventsLoggerImpl implements EventsLogger {

    @NonNull
    private FirebaseAnalytics analyticsInstance;

    @Inject
    EventsLoggerImpl(Context context) {
        analyticsInstance = FirebaseAnalytics.getInstance(context.getApplicationContext());
    }

    @Override
    public void logViewEvent(@NonNull Events event) {
        logViewEvent(event, null);
    }

    @Override
    public void logViewEvent(@NonNull Events event, @Nullable String contentId) {
        Bundle viewEvent = new Bundle();
        viewEvent.putString(FirebaseAnalytics.Param.METHOD, event.name);

        if (contentId != null) {
            viewEvent.putString(FirebaseAnalytics.Param.CONTENT_TYPE, truncateContentId(contentId));
        }

        logDebug(event.name, contentId);
        analyticsInstance.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewEvent);
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
