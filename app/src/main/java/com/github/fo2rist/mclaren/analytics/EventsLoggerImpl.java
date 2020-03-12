package com.github.fo2rist.mclaren.analytics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.AnswersEvent;
import com.crashlytics.android.answers.ContentViewEvent;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import timber.log.Timber;

public class EventsLoggerImpl implements EventsLogger {

    private final boolean isFabricInitialized;
    @Nullable
    private Answers answersInstance;

    @Inject
    EventsLoggerImpl() {
        isFabricInitialized = Fabric.isInitialized();
        if (isFabricInitialized) {
            answersInstance = Answers.getInstance();
        }
    }

    @Override
    public void logViewEvent(@NonNull Events event) {
        logViewEvent(event, null);
    }

    @Override
    public void logViewEvent(@NonNull Events event, @Nullable String contentId) {
        ContentViewEvent viewEvent = new ContentViewEvent().putContentName(event.name);
        if (contentId != null) {
            viewEvent.putContentId(truncateContentId(contentId));
        }
        logContentViewEvent(viewEvent);
    }

    private String truncateContentId(@NonNull String contentId) {
        //Actual fabric limitation is non null <100 symbols string
        if (contentId.length() > 98) {
            return contentId.substring(0, 98);
        } else {
            return contentId;
        }
    }

    private void logContentViewEvent(@NonNull ContentViewEvent event) {
        if (isFabricInitialized) {
            answersInstance.logContentView(event);
        } else {
            logDebug(event);
        }
    }

    private void logDebug(AnswersEvent<?> event) {
        Timber.d("Analytics. Logged: %s", event);
    }
}
