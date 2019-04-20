package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.network.VideoListResponse

class VideoResponseMapper : NetworkResponseMapper<VideoListResponse> {
  override fun onLastPage(response: VideoListResponse): Boolean {
    return true
  }
}
