package com.mina.movies.storage

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mina.common.models.Movie
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public object MovieStorageModule {

    @Provides
    @Reusable
    internal fun movieDao(application: Application): RoomDatabase =
        MovieDatabaseBuilder(application = application)
            .build()

    @Provides
    @Reusable
    fun movieFavoriteRepository(database: RoomDatabase): MovieFavoriteRepository {
        val movieDao = (database as MovieDatabase).movieDao()

        val movieEntityConverter = MovieEntityConverter()
        return MovieFavoriteRepositoryImpl(
            movieDao = movieDao,
            movieEntityConverter = movieEntityConverter
        )
    }

    @Provides
    @Reusable
    fun movieHiddenRepository(database: RoomDatabase): MovieHiddenRepository {
        val movieDao = (database as MovieDatabase).movieDao()

        val movieEntityConverter = MovieEntityConverter()
        return MovieHiddenRepositoryImpl(
            movieDao = movieDao,
            movieEntityConverter = movieEntityConverter
        )
    }
}