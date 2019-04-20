package com.sun.kh_mvvm_kotlin_movie.data.source.remote.api

import androidx.lifecycle.LiveData
import com.sun.kh_mvvm_kotlin_movie.data.model.network.KeywordListResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.ReviewListResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvServicce {

  @GET("/3/tv/{tv_id}/keywords")
  fun fetchKeywords(@Path("tv_id") id: Int): LiveData<ApiResponse<KeywordListResponse>>

  @GET("/3/tv/{tv_id}/videos")
  fun fetchVideos(@Path("tv_id") id: Int): LiveData<ApiResponse<VideoListResponse>>

  @GET("/3/tv/{tv_id}/reviews")
  fun fetchReviews(@Path("tv_id") id: Int): LiveData<ApiResponse<ReviewListResponse>>
}