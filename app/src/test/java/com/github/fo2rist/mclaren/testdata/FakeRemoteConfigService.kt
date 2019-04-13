package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.web.RemoteConfigService

/** [RemoteConfigService] implementation that provides default empty values for every field. */
class FakeRemoteConfigService internal constructor(
    override val calendar: String = "",
    override val circuits: String = "",
    override val drivers: String = ""
) : RemoteConfigService {
    override fun fetchConfig() {}
}