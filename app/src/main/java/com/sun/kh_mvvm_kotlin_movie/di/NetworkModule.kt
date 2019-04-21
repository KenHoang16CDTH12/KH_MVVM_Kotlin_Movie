package com.sun.kh_mvvm_kotlin_movie.di

import androidx.annotation.NonNull
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideHttpClient(): OkHttpClient =
      OkHttpClient.Builder()
          .addInterceptor(RequestInterceptor())
          .addNetworkInterceptor(StethoInterceptor())
          .build()

  @Provides
  @Singleton
  fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit =
      Retrofit.Builder()
          .client(okHttpClient)
          .baseUrl("https://api.themoviedb.org/")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(LiveDataCallAdapterFactory())
          .build()

  @Provides
  @Singleton
  fun provideDiscoverService(@NonNull retrofit: Retrofit): TheDiscoverService =
      retrofit.create(TheDiscoverService::class.java)


  @Provides
  @Singleton
  fun providePeopleService(@NonNull retrofit: Retrofit): PeopleService =
      retrofit.create(PeopleService::class.java)

  @Provides
  @Singleton
  fun provideMovieService(@NonNull retrofit: Retrofit): MovieService =
      retrofit.create(MovieService::class.java)

  @Provides
  @Singleton
  fun provideTvService(@NonNull retrofit: Retrofit): TvService =
      retrofit.create(TvService::class.java)
}