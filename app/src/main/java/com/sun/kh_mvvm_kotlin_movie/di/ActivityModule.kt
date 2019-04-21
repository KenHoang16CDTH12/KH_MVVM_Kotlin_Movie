package com.sun.kh_mvvm_kotlin_movie.di

import com.sun.kh_mvvm_kotlin_movie.ui.detail.movie.MovieDetailActivity
import com.sun.kh_mvvm_kotlin_movie.ui.detail.person.PersonDetailActivity
import com.sun.kh_mvvm_kotlin_movie.ui.detail.tv.TvDetailActivity
import com.sun.kh_mvvm_kotlin_movie.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
  @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
  internal abstract fun contributeMainActivity(): MainActivity

  @ContributesAndroidInjector
  internal abstract fun contributeMovieDetailActivity(): MovieDetailActivity

  @ContributesAndroidInjector
  internal abstract fun contributeTvDetailActivity(): TvDetailActivity

  @ContributesAndroidInjector
  internal abstract fun contributePersonDetailActivity(): PersonDetailActivity
}