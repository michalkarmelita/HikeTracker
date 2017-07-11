package com.michalkarmelita.busarrivals.network.interceptors

import com.michalkarmelita.hiketracker.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    private val api_key = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain?): Response {
        val originalRequest = chain!!.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", api_key)
                .addQueryParameter("method", "flickr.photos.search")
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .build()

        val request = originalRequest.newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }

}