package com.sun.kh_mvvm_kotlin_movie

import com.facebook.stetho.Stetho
import com.sun.kh_mvvm_kotlin_movie.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

@Suppress("unused")
class TheMoviesApplication : DaggerApplication() {

  private val appComponent = DaggerAppComponent.builder()
      .application(this)
      .build()

  override fun onCreate() {
    super.onCreate()
    appComponent.inject(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    Stetho.initializeWithDefaults(this)
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent
}