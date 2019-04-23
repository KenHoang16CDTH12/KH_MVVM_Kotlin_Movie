package com.sun.kh_mvvm_kotlin_movie.di

import com.sun.kh_mvvm_kotlin_movie.ui.main.MovieListFragment
import com.sun.kh_mvvm_kotlin_movie.ui.main.PersonListFragment
import com.sun.kh_mvvm_kotlin_movie.ui.main.TvListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityFragmentModule {

  @ContributesAndroidInjector
  abstract fun contributeMovieListFragment(): MovieListFragment

  @ContributesAndroidInjector
  abstract fun contributeTvListFragment(): TvListFragment

  @ContributesAndroidInjector
  abstract fun contributePersonListFragment(): PersonListFragment
}