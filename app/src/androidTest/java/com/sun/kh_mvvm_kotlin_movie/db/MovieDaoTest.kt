package com.sun.kh_mvvm_kotlin_movie.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie
import com.sun.kh_mvvm_kotlin_movie.util.LiveDataTestUtil.getValue
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockMovie
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest : DbTest() {

    @Test
    fun insertAndReadTest() {
        val movieList = ArrayList<Movie>()
        val movie = mockMovie()
        movieList.add(movie)

        db.movieDao().insertMovieList(movieList)
        val loadFromDB = getValue(db.movieDao().getMovieList(movie.page))[0]
        assertThat(loadFromDB.page, `is`(1))
        assertThat(loadFromDB.id, `is`(123))
    }

    @Test
    fun updateAndReadTest() {
        val movieList = ArrayList<Movie>()
        val movie = mockMovie()
        movieList.add(movie)
        db.movieDao().insertMovieList(movieList)

        val loadFromDB = db.movieDao().getMovie(movie.id)
        assertThat(loadFromDB.page, `is`(1))

        movie.page = 10
        db.movieDao().updateMovie(movie)

        val updated = db.movieDao().getMovie(movie.id)
        assertThat(updated.page, `is`(10))
    }
}