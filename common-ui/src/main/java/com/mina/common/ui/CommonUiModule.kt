package com.mina.common.ui

import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object CommonUiModule {

    @Provides
    fun glideRequestOptions(): RequestOptions =
        RequestOptions()
            .placeholder(R.drawable.ic_image_loading)
            .error(R.drawable.ic_image_loading_error)
            .fitCenter()
}