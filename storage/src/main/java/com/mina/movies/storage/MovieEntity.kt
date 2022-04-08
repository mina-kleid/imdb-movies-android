package com.mina.movies.storage

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["title", "year"], tableName = "Movie")
internal data class MovieEntity(
    val title: String,
    val year: String,
    val rating: String,
    val description: String,
    val posterUrl: String?,
    @ColumnInfo(defaultValue = "false") val isFavorite: Boolean = false,
    @ColumnInfo(defaultValue = "false") val isHidden: Boolean = false
)
