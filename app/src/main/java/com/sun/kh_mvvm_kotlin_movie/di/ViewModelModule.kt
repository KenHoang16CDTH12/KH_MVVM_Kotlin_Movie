package com.sun.kh_mvvm_kotlin_movie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sun.kh_mvvm_kotlin_movie.factory.AppViewModelFactory
import com.sun.kh_mvvm_kotlin_movie.ui.detail.movie.MovieDetailViewModel
import com.sun.kh_mvvm_kotlin_movie.ui.detail.person.PersonDetailViewModel
import com.sun.kh_mvvm_kotlin_movie.ui.detail.tv.TvDetailViewModel
import com.sun.kh_mvvm_kotlin_movie.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(MainActivityViewModel::class)
  internal abstract fun bindMainActivityViewModels(mainActivityViewModel: MainActivityViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MovieDetailViewModel::class)
  internal abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(TvDetailViewModel::class)
  internal abstract fun bindTvDetailViewModel(tvDetailViewModel: TvDetailViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(PersonDetailViewModel::class)
  internal abstract fun bindPersonDetailViewModel(personDetailViewModel: PersonDetailViewModel): ViewModel

  @Binds
  internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
