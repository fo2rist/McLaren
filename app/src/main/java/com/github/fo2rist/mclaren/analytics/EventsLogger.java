package com.github.fo2rist.mclaren.analytics;

/**
 * Interface of Analytics events.
 * It doesn't fully hide the nature of firebase under the hood, which tracks screens automatically,
 * so it offers a method to override screen name that would be logged by firebase analytics.
 */
public interface EventsLogger {
    void overrideScreenName(Events.Screen screen);

    void logInternalAction(Events.Action event);

    void logExternalNavigation(Events.Screen event, String contentId);
}
