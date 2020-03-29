package com.github.fo2rist.mclaren.web.utils

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import java.io.IOException
import java.net.ConnectException
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Suspend extension that allows suspend [Call] inside coroutine.
 *
 * @return Response body
 * @throws ConnectException if networking error occurs
 * @throws BadResponse if server responds with 4xx-5xx code
 */
suspend fun Call.executeSuspend(): String? {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : OkHttpCallbackWrapper() {
            override fun onOkHttpSuccess(url: URL, responseCode: Int, responseBody: String?) {
                continuation.resume(responseBody)
            }

            override fun onOkHttpFailure(url: URL, responseCode: Int, connectionError: IOException?) {
                if (continuation.isCancelled) {
                    return // Don't bother with resuming the continuation if it is already cancelled.
                }

                if (connectionError != null) {
                    continuation.resumeWithException(ConnectException(connectionError.message))
                } else {
                    continuation.resumeWithException(BadResponse(url, responseCode))
                }
            }
        })

        @Suppress("Detekt.TooGenericExceptionCaught")
        continuation.invokeOnCancellation {
            try {
                cancel() // cancel OkHttp call
            } catch (exc: Throwable) {
                // we don't care about OkHttp exceptions on cancellation
            }
        }
    }
}

/**
 * Indicates that 4xx-5xx response is received from the server.
 */
class BadResponse(
    val url: URL,
    val responseCode: Int
) : IOException()
