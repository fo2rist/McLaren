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
suspend fun Call.executeAsync(): String? {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : OkHttpCallbackWrapper() {
            override fun onOkHttpSuccess(url: URL, responseCode: Int, responseBody: String?) {
                continuation.resume(responseBody)
            }

            override fun onOkHttpFailure(url: URL, responseCode: Int, connectionError: IOException?) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) {
                    return
                }

                if (connectionError != null) {
                    continuation.resumeWithException(ConnectException(connectionError.message))
                } else {
                    continuation.resumeWithException(BadResponse(url, responseCode))
                }
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (exc: Throwable) {
                //Ignore cancel exception
            }
        }
    }
}

/**
 *
 */
class BadResponse(
    val url: URL,
    val responseCode: Int
): Exception()
