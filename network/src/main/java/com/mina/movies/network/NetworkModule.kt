package com.mina.movies.network

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Reusable
    fun apiClient(httpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .client(httpClient)
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    fun httpClient(interceptor: OkHttpClientInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

}