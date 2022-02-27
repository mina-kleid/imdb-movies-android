package com.mina.movies.storage

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
internal abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}