package com.github.fo2rist.mclaren.models

/**
 * Twitter accounts recognized by the app.
 */
object FeedAccounts {
    /**
     * Pseudo-account name to be used with McLaren Feed API loading requests by default.
     * It allows distinguishing results to McLaren feed API from all other accounts.
     */
    const val MCLAREN_FEED_ACCOUNT_NAME = "_mclaren_feed_"

    /**
     * Pseudo-account name to be used with StoryStream API loading requests by default.
     * It allows distinguishing results to StoryStream feed API from all other accounts.
     */
    const val STORIES_FEED_ACCOUNT_NAME = "_story_stream_feed_"

    const val TWITTER_MCLAREN_F1 = "mclarenf1"
    const val TWITTER_LANDO_NORRIS = "LandoNorris"
    const val TWITTER_DANIEL_RICCIARDO = "danielricciardo"
}
