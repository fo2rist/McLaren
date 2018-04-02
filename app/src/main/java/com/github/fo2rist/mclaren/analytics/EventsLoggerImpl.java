package com.github.fo2rist.mclaren.analytics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    public void logViewEvent(Events event) {
        logContentViewEvent(new ContentViewEvent()
                .putContentName(event.name));
    }

    @Override
    public void logViewEvent(Events event, String contentId) {
        logContentViewEvent(new ContentViewEvent()
                .putContentName(event.name)
                .putContentId(contentId));
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
