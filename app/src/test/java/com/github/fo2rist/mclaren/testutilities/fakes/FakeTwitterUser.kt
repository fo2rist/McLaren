package com.github.fo2rist.mclaren.testutilities.fakes

import twitter4j.RateLimitStatus
import twitter4j.Status
import twitter4j.URLEntity
import twitter4j.User
import java.util.Date

class FakeTwitterUser : User {

    override fun getName(): String = "username"

    override fun getScreenName(): String = "userscreenname"

    override fun getEmail(): String = throw NotImplementedError()

    override fun getId(): Long = throw NotImplementedError()

    override fun getTimeZone(): String = throw NotImplementedError()

    override fun isDefaultProfileImage(): Boolean = throw NotImplementedError()

    override fun getProfileBackgroundImageURL(): String = throw NotImplementedError()

    override fun getProfileLinkColor(): String = throw NotImplementedError()

    override fun isVerified(): Boolean = throw NotImplementedError()

    override fun getProfileImageURLHttps(): String = throw NotImplementedError()

    override fun getProfileImageURL(): String = throw NotImplementedError()

    override fun getOriginalProfileImageURLHttps(): String = throw NotImplementedError()

    override fun getDescription(): String = throw NotImplementedError()

    override fun getBiggerProfileImageURLHttps(): String = throw NotImplementedError()

    override fun isGeoEnabled(): Boolean = throw NotImplementedError()

    override fun getBiggerProfileImageURL(): String = throw NotImplementedError()

    override fun getStatus(): Status = throw NotImplementedError()

    override fun getLang(): String = throw NotImplementedError()

    override fun isProtected(): Boolean = throw NotImplementedError()

    override fun isTranslator(): Boolean = throw NotImplementedError()

    override fun getLocation(): String = throw NotImplementedError()

    override fun get400x400ProfileImageURLHttps(): String = throw NotImplementedError()

    override fun getRateLimitStatus(): RateLimitStatus = throw NotImplementedError()

    override fun isShowAllInlineMedia(): Boolean = throw NotImplementedError()

    override fun getProfileBannerURL(): String = throw NotImplementedError()

    override fun getUtcOffset(): Int = throw NotImplementedError()

    override fun getProfileBannerIPadRetinaURL(): String = throw NotImplementedError()

    override fun getProfileBanner600x200URL(): String = throw NotImplementedError()

    override fun getProfileBannerMobileRetinaURL(): String = throw NotImplementedError()

    override fun getMiniProfileImageURL(): String = throw NotImplementedError()

    override fun getProfileBanner1500x500URL(): String = throw NotImplementedError()

    override fun getProfileBannerIPadURL(): String = throw NotImplementedError()

    override fun getProfileBannerMobileURL(): String = throw NotImplementedError()

    override fun getDescriptionURLEntities(): Array<URLEntity> = throw NotImplementedError()

    override fun getStatusesCount(): Int = throw NotImplementedError()

    override fun isContributorsEnabled(): Boolean = throw NotImplementedError()

    override fun getFollowersCount(): Int = throw NotImplementedError()

    override fun getWithheldInCountries(): Array<String> = throw NotImplementedError()

    override fun getProfileSidebarFillColor(): String = throw NotImplementedError()

    override fun getAccessLevel(): Int = throw NotImplementedError()

    override fun getProfileBackgroundColor(): String = throw NotImplementedError()

    override fun isFollowRequestSent(): Boolean = throw NotImplementedError()

    override fun getProfileTextColor(): String = throw NotImplementedError()

    override fun isProfileUseBackgroundImage(): Boolean = throw NotImplementedError()

    override fun getProfileBackgroundImageUrlHttps(): String = throw NotImplementedError()

    override fun isProfileBackgroundTiled(): Boolean = throw NotImplementedError()

    override fun getProfileBanner300x100URL(): String = throw NotImplementedError()

    override fun getURL(): String = throw NotImplementedError()

    override fun getMiniProfileImageURLHttps(): String = throw NotImplementedError()

    override fun getProfileSidebarBorderColor(): String = throw NotImplementedError()

    override fun compareTo(other: User?): Int = throw NotImplementedError()

    override fun getFavouritesCount(): Int = throw NotImplementedError()

    override fun getURLEntity(): URLEntity = throw NotImplementedError()

    override fun isDefaultProfile(): Boolean = throw NotImplementedError()

    override fun get400x400ProfileImageURL(): String = throw NotImplementedError()

    override fun getFriendsCount(): Int = throw NotImplementedError()

    override fun getCreatedAt(): Date = throw NotImplementedError()

    override fun getOriginalProfileImageURL(): String = throw NotImplementedError()

    override fun getListedCount(): Int = throw NotImplementedError()

    override fun getProfileBannerRetinaURL(): String = throw NotImplementedError()
}
