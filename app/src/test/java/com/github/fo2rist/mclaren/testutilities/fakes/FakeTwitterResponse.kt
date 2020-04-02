package com.github.fo2rist.mclaren.testutilities.fakes

import twitter4j.RateLimitStatus
import twitter4j.ResponseList
import twitter4j.Status

class FakeTwitterResponse(
    vararg statuses: Status
) : java.util.ArrayList<Status>(statuses.asList()), ResponseList<Status> {
    override fun getAccessLevel(): Int = 0

    override fun getRateLimitStatus(): RateLimitStatus? = null
}
