package com.mina.movies.storage

import androidx.room.Entity

@Entity(primaryKeys = ["title", "year"])
internal data class Movie(
    val title: String,
    val year: String,
    val isFavorite: Boolean
)
