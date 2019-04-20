package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.network.PeopleResponse
import timber.log.Timber

class PeopleResponseMapper : NetworkResponseMapper<PeopleResponse> {
  override fun onLastPage(response: PeopleResponse): Boolean {
    Timber.d("loadPage : ${response.page}/${response.total_pages}")
    return response.page > response.total_pages
  }
}
