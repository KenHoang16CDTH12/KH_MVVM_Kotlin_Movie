package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.network.ReviewListResponse

class ReviewResponseMapper : NetworkResponseMapper<ReviewListResponse> {
  override fun onLastPage(response: ReviewListResponse): Boolean {
    return true
  }
}