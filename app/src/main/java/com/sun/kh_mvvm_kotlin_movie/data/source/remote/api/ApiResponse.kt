package com.sun.kh_mvvm_kotlin_movie.data.source.remote.api

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class ApiResponse<T> {
  private val mCode: Int
  private val mBody: T?
  private val mMessage: String?

  val isSuccessful: Boolean
    get() = mCode in 200..300
  private val isFailure: Boolean

  constructor(error: Throwable) {
    mCode = 500
    mBody = null
    mMessage = error.message
    isFailure = true
  }

  constructor(response: Response<T>) {
    mCode = response.code()
    if (response.isSuccessful) {
      mBody = response.body()
      mMessage = null
      isFailure = false
    } else {
      var errorMessage: String? = null
      response.errorBody()?.let {
        try {
          errorMessage = response.errorBody()!!.string()
        } catch (ignored: IOException) {
          Timber.e(ignored, "error while parsing response")
        }
      }

      errorMessage?.apply {
        if (isNullOrEmpty() || trim { it <= ' ' }.isEmpty()) {
          errorMessage = response.message()
        }
      }
      mBody = null
      mMessage = errorMessage
      isFailure = true
    }
  }
}