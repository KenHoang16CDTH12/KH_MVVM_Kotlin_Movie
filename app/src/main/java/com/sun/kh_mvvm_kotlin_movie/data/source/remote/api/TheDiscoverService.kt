package com.sun.kh_mvvm_kotlin_movie.data.source.remote.api

import androidx.lifecycle.LiveData
import com.sun.kh_mvvm_kotlin_movie.data.model.network.DiscoverMovieResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.DiscoverTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheDiscoverService {

  @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
  fun fetchDiscoverMovie(@Query("page") page: Int): LiveData<ApiResponse<DiscoverMovieResponse>>

  @GET("/3/discover/tv?language=en&sort_by=popularity.desc")
  fun fetchDiscoverTv(@Query("page") page: Int): LiveData<ApiResponse<DiscoverTvResponse>>
}