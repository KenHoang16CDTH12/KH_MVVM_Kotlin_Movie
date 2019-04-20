package com.sun.kh_mvvm_kotlin_movie.data.model.network

data class ErrorEnvelope(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)