package com.mina.movies.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class OkHttpClientInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val url = originalHttpUrl
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        request.url(url)
        return chain.proceed(request.build())
    }

}