package com.sun.kh_mvvm_kotlin_movie.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.sun.kh_mvvm_kotlin_movie.data.source.local.AppDatabase
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.MovieDao
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.PeopleDao
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.TvDao
import dagger.Provides
import javax.inject.Singleton

class PersistenceModule {

  @Provides
  @Singleton
  fun provideDatabase(@NonNull application: Application): AppDatabase =
      Room.databaseBuilder(application, AppDatabase::class.java, "TheMovies.db").allowMainThreadQueries().build()


  @Provides
  @Singleton
  fun provideMovieDao(@NonNull database: AppDatabase): MovieDao =
      database.movieDao()

  @Provides
  @Singleton
  fun provideTvDao(@NonNull database: AppDatabase): TvDao =
      database.tvDao()

  @Provides
  @Singleton
  fun providePeopleDao(@NonNull database: AppDatabase): PeopleDao =
      database.peopleDao()
}