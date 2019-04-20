package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.network.KeywordListResponse

class KeywordResponseMapper : NetworkResponseMapper<KeywordListResponse> {
  override fun onLastPage(response: KeywordListResponse): Boolean {
    return true
  }
}