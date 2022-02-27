package com.mina.movies.storage

import androidx.room.Entity

@Entity(primaryKeys = ["title", "year"], tableName = "Movie")
internal data class MovieEntity(
    val title: String,
    val year: String
)
