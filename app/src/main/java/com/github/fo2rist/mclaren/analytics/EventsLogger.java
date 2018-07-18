package com.github.fo2rist.mclaren.analytics;

/**
 * Interface of Analytics event logger.
 */
public interface EventsLogger {
    void logViewEvent(Events event);

    void logViewEvent(Events event, String contentId);
}
