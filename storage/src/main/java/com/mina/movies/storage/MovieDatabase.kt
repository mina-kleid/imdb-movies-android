package com.mina.movies.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
internal abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}