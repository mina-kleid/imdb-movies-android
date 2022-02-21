package com.mina.movies.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
internal abstract class MovieDatabase {
    abstract fun movieDao(): MovieDao

}