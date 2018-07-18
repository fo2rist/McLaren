package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.web.utils.OkHttpCallbackWrapper
import java.io.IOException
import java.net.URL

interface TransmissionWebService {
    /**
     * Callback for requests to [TransmissionWebService] methods.
     */
    interface TransmissionRequestCallback {

        /**
         * Called when response received.
         * @param url original request Url
         * @param responseCode Http response code
         * @param data Http response body as string.
         */
        fun onSuccess(url: URL, responseCode: Int, data: String?)

        /**
         * Called on any failure, networking or server side.
         * @param url original request Url
         * @param responseCode Http response code, non zero if server returned the code
         * @param connectionError Error in case of any exception, null of server returned the code
         */
        fun onFailure(url: URL, responseCode: Int, connectionError: IOException?)
    }

    /**
     * OkHttp callback wrapper.
     * Delivers callback in more convenient form.
     */
    class TransmissionCallbackWrapper(private val callback: TransmissionRequestCallback) : OkHttpCallbackWrapper() {
        override fun onOkHttpFailure(url: URL, responseCode: Int, connectionError: IOException?) =
                callback.onFailure(url, responseCode, connectionError)

        override fun onOkHttpSuccess(url: URL, responseCode: Int, responseBody: String?) =
                callback.onSuccess(url, responseCode, responseBody)
    }

    fun requestTransmission(callback: TransmissionRequestCallback)
}
