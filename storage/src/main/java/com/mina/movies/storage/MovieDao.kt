package com.mina.movies.storage

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
internal interface MovieDao {
    @Query("SELECT * FROM movie where title = :title AND year = :year LIMIT 1")
    suspend fun getMovie(title: String, year: String): MovieEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Update
    suspend fun update(vararg movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    suspend fun updateOrInsert(movieEntity: MovieEntity) {
        val movieEntityFromDatabase: MovieEntity? = getMovie(title = movieEntity.title, year = movieEntity.year)
        if (movieEntityFromDatabase != null) {
            update(movieEntityFromDatabase)
        } else {
            insert(movieEntity)
        }
    }
}