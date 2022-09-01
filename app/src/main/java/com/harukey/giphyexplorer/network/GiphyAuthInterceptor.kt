package com.harukey.giphyexplorer.network

import com.harukey.giphyexplorer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GiphyAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(addKeyQuery(chain.request()))
    }

    private fun addKeyQuery(request: Request): Request {
        val updatedUrl =
            request.url.newBuilder().addQueryParameter(KEY_QUERY, BuildConfig.GIPHY_KEY).build()
        return request.newBuilder().url(updatedUrl).build()
    }

    private companion object {
        const val KEY_QUERY = "api_key"
    }
}
