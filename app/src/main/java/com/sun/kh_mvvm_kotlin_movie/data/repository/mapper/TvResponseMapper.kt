package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.network.DiscoverTvResponse
import timber.log.Timber

class TvResponseMapper : NetworkResponseMapper<DiscoverTvResponse> {
  override fun onLastPage(response: DiscoverTvResponse): Boolean {
    Timber.d("loadPage : ${response.page}/${response.total_pages}")
    return response.page > response.total_pages
  }
}
