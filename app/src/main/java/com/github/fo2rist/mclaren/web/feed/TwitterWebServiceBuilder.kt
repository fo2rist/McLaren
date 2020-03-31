package com.github.fo2rist.mclaren.web.feed

import com.github.fo2rist.mclaren.BuildConfig
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Builds and configures [Twitter] client.
 */
@Singleton //injected without explicit binding, so declared as a singleton here
class TwitterWebServiceBuilder @Inject constructor() {

    /**
     * Get [Twitter] instance.
     * Create one if doesn't exist yet.
     */
    fun getInstance(): Twitter {
        return TwitterFactory(ConfigurationBuilder()
                .setDebugEnabled(BuildConfig.DEBUG)
                .setOAuthConsumerKey(BuildConfig.TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(BuildConfig.TWITTER_CONSUMER_SECRET)
                .setOAuthAccessToken(BuildConfig.TWITTER_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
                .build()
        ).instance;
    }
}
