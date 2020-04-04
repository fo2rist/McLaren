package com.github.fo2rist.mclaren.testutilities.fakes

import twitter4j.URLEntity

class FakeTwitterUrlEntity(
    private val _url: String,
    private val _displayUrl: String?
) : URLEntity {
    override fun getStart(): Int = 0

    override fun getEnd(): Int = 1

    override fun getURL(): String = _url

    override fun getDisplayURL(): String? = _displayUrl

    override fun getText(): String = _url.replace("""^https?:\/\/""".toRegex(), "")

    override fun getExpandedURL(): String = _url

}
