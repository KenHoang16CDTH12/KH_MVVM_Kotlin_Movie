package com.sun.kh_mvvm_kotlin_movie.util

import androidx.lifecycle.LiveData

class AbsentLiveData<T> : LiveData<T>() {
  init {
    postValue(null)
  }

  companion object {
    fun <T> create() = AbsentLiveData<T>()
  }
}