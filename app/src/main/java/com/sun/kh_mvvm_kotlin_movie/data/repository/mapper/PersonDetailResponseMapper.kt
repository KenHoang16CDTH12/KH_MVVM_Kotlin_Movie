package com.sun.kh_mvvm_kotlin_movie.data.repository.mapper

import com.sun.kh_mvvm_kotlin_movie.data.model.network.PersonDetail

class PersonDetailResponseMapper : NetworkResponseMapper<PersonDetail> {
  override fun onLastPage(response: PersonDetail): Boolean {
    return true
  }
}
