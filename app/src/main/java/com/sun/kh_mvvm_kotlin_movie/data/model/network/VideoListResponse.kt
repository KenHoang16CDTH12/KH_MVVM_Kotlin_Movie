package com.sun.kh_mvvm_kotlin_movie.data.model.network

import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel
import com.sun.kh_mvvm_kotlin_movie.data.model.Video

data class VideoListResponse(
    val id: Int,
    val results: List<Video>
) : NetworkResponseModel