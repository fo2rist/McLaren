package com.github.fo2rist.mclaren.testutilities.fakes

import twitter4j.GeoLocation
import twitter4j.HashtagEntity
import twitter4j.MediaEntity
import twitter4j.Place
import twitter4j.RateLimitStatus
import twitter4j.Scopes
import twitter4j.Status
import twitter4j.SymbolEntity
import twitter4j.URLEntity
import twitter4j.User
import twitter4j.UserMentionEntity
import java.util.Date

class FakeTwitterStatus(
    private val _id: Long = 1,
    private val _text: String = "",
    private val _createdAt: Date = Date(),
    private val _inReplyToStatusId: Long = -1L,
    private val _quotedStatus: Status? = null,
    private val _retweetedStatus: Status? = null,
    private val _mediaEntities: Array<MediaEntity> = emptyArray(),
    private val _urlEntities: Array<URLEntity> = emptyArray(),
    private val _user: User = FakeTwitterUser()
) : Status {

    override fun getId(): Long = _id

    override fun getText(): String = _text

    override fun getCreatedAt(): Date = _createdAt

    override fun getInReplyToStatusId(): Long = _inReplyToStatusId

    override fun getRetweetedStatus(): Status? = _retweetedStatus

    override fun getQuotedStatus(): Status? = _quotedStatus

    override fun getMediaEntities(): Array<MediaEntity> = _mediaEntities

    override fun getURLEntities(): Array<URLEntity> = _urlEntities

    override fun getUserMentionEntities(): Array<UserMentionEntity> = throw NotImplementedError()

    override fun getContributors(): LongArray = throw NotImplementedError()

    override fun isFavorited(): Boolean = throw NotImplementedError()

    override fun getInReplyToScreenName(): String = throw NotImplementedError()

    override fun getDisplayTextRangeStart(): Int = throw NotImplementedError()

    override fun getGeoLocation(): GeoLocation = throw NotImplementedError()

    override fun getSource(): String = throw NotImplementedError()

    override fun getWithheldInCountries(): Array<String> = throw NotImplementedError()

    override fun getCurrentUserRetweetId(): Long = throw NotImplementedError()

    override fun getSymbolEntities(): Array<SymbolEntity> = throw NotImplementedError()

    override fun getAccessLevel(): Int = throw NotImplementedError()

    override fun getInReplyToUserId(): Long = throw NotImplementedError()

    override fun getPlace(): Place = throw NotImplementedError()

    override fun isRetweetedByMe(): Boolean = throw NotImplementedError()

    override fun getUser(): User = _user

    override fun isRetweeted(): Boolean = throw NotImplementedError()

    override fun getLang(): String = throw NotImplementedError()

    override fun getRateLimitStatus(): RateLimitStatus = throw NotImplementedError()

    override fun getDisplayTextRangeEnd(): Int = throw NotImplementedError()

    override fun compareTo(other: Status): Int = throw NotImplementedError()

    override fun getQuotedStatusId(): Long = throw NotImplementedError()

    override fun isRetweet(): Boolean = throw NotImplementedError()

    override fun getFavoriteCount(): Int = throw NotImplementedError()

    override fun isPossiblySensitive(): Boolean = throw NotImplementedError()

    override fun getScopes(): Scopes = throw NotImplementedError()

    override fun isTruncated(): Boolean = throw NotImplementedError()

    override fun getQuotedStatusPermalink(): URLEntity = throw NotImplementedError()

    override fun getHashtagEntities(): Array<HashtagEntity> = throw NotImplementedError()

    override fun getRetweetCount(): Int = throw NotImplementedError()
}
