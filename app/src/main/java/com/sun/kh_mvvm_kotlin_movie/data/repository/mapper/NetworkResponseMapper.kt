package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel

interface NetworkResponseMapper<in FROM : NetworkResponseModel> {
  fun onLastPage(response: FROM): Boolean
}