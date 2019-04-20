package com.sun.kh_mvvm_kotlin_movie.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.MovieDao
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.PeopleDao
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.TvDao
import com.sun.kh_mvvm_kotlin_movie.util.*

@Database(entities = [(Movie::class), (Tv::class), (Person::class)],
    version = 3, exportSchema = false)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class),
  (KeywordListConverter::class), (VideoListConverter::class), (ReviewListConverter::class)])
abstract class AppDatabase : RoomDatabase() {
  abstract fun movieDao(): MovieDao
  abstract fun tvDao(): TvDao
  abstract fun peopleDao(): PeopleDao
}