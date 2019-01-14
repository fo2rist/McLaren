package com.github.fo2rist.mclaren.web

import android.content.Context
import com.github.fo2rist.mclaren.utils.CacheUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object OkHttpClientFactory {

    private const val WEB_CACHE_SIZE = 5 * 1024 * 1024L // 5 Mb
    private const val IMAGE_CACHE_SIZE = 25 * 1024 * 1024L //25 Mb

    /**
     * Create OkHttp Client to be used for generic web requests.
     * This client will use it's own small cache.
     * Method should never be called more than one, b/c cache is not shareable.
     * @param interceptor to be used in case interceptor ir required (e.g for authentication or logging)
     */
    @JvmStatic
    @JvmOverloads
    fun createWebClient(context: Context, interceptor: Interceptor? = null): OkHttpClient {
        // WARN Two caching OkHTTP clients should not use the same directory
        // or at lest should never call the same endpoint
        val cache = CacheUtils.createCache(context, "web", WEB_CACHE_SIZE)
        return buildClient(cache, interceptor)
    }

    /**
     * Create OkHttp Client to be used for images loading.
     * This client will have medium-size cache suitable for images.
     * Method should never be called more than one, b/c cache is not shareable.
     * @param interceptor to be used in case interceptor ir required (e.g for authentication or logging)
     */
    @JvmStatic
    @JvmOverloads
    fun createPicassoClient(context: Context, interceptor: Interceptor? = null): OkHttpClient {
        val cache = CacheUtils.createCache(context, "images", IMAGE_CACHE_SIZE)
        return buildClient(cache, interceptor)
    }

    private fun buildClient(cache: Cache? = null, interceptor: Interceptor? = null): OkHttpClient {
        return OkHttpClient.Builder()
                .apply {
                    // set cache if provided
                    cache?.let { cache(it) }
                    interceptor?.let { addInterceptor(it) }
                }
                .build()
    }
}
