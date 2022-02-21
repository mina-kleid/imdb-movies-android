package com.mina.movies.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
internal interface MovieDao {
    @Query("SELECT * FROM movie where title = :title AND year = :year LIMIT 1")
    fun getMovie(title: String, year: String): Movie?

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Update
    fun update(vararg movie: Movie)

    fun updateOrInsert(movie: Movie) {
        val movieFromDatabase: Movie? = getMovie(title = movie.title, year = movie.year)
        if (movieFromDatabase != null) {
            update(movieFromDatabase)
        } else {
            insert(movie)
        }
    }
}