package com.github.fo2rist.mclaren.analytics;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import javax.inject.Inject;

public class EventsLoggerImpl implements EventsLogger {
    @Inject
    EventsLoggerImpl() {
    }

    @Override
    public void logViewEvent(Events event) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(event.name));
    }

    @Override
    public void logViewEvent(Events event, String contentId) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(event.name)
                .putContentId(contentId));
    }
}
