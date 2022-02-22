package com.mina.movies.storage


import android.app.Application
import android.content.Context
import androidx.room.Room
import javax.inject.Inject

internal class MovieDatabaseBuilder @Inject constructor(private val application: Application) {

    fun build(): MovieDatabase = Room.databaseBuilder(
            application,
            MovieDatabase::class.java, "movies-db"
        ).build()
}