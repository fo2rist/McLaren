package com.github.fo2rist.mclaren.web.utils

import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.net.URL

/**
 * Wrapper that handles both network errors and bad responses and delivers them to single failure handler.
 */
abstract class OkHttpCallbackWrapper : okhttp3.Callback {
    /**
     * Called on network failure or 4xx-5xx response.
     * @param responseCode non zero if response received
     * @param connectionError non null if networking error occurs
     */
    abstract fun onOkHttpFailure(url: URL, responseCode: Int = 0, connectionError: IOException? = null)

    /**
     * Called on success (e.g 2xx response).
     */
    abstract fun onOkHttpSuccess(url: URL, responseCode: Int, responseBody: String?)

    override fun onFailure(call: Call, exc: IOException?) {
        onOkHttpFailure(getUrl(call), connectionError = exc)
    }

    override fun onResponse(call: Call, response: Response) {
        val url = getUrl(call)
        if (response.isSuccessful) {
            onOkHttpSuccess(url, response.code(), response.body()?.string())
        } else {
            onOkHttpFailure(url, response.code())
        }
    }

    private fun getUrl(call: Call): URL {
        return call.request().url().url()
    }
}
