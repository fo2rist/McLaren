package com.github.fo2rist.mclaren.testutilities.fakes

import com.github.fo2rist.mclaren.web.remoteconfig.RemoteConfigService

/** [RemoteConfigService] implementation that provides default empty values for every field. */
class FakeRemoteConfigService internal constructor(
    override val calendar: String = "",
    override val circuits: String = "",
    override val drivers: String = "",
    override val driversOrderList: String = ""
) : RemoteConfigService {
    override fun fetchConfig() {}
}
