package com.sun.kh_mvvm_kotlin_movie.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMovieList(movies: List<Movie>)

  @Update
  fun updateMovie(movie: Movie)

  @Query("SELECT * FROM Movie WHERE id = :id_")
  fun getMovie(id_: Int): Movie

  @Query("SELECT * FROM Movie WHERE page = :page_")
  fun getMovieList(page_: Int): LiveData<List<Movie>>
}