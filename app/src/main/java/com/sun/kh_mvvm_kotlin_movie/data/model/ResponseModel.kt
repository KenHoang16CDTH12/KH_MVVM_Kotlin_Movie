package com.sun.kh_mvvm_kotlin_movie.data.model

@Suppress("unused")
data class ResponseModel(
    val page: Int,
    val results: Any,
    val total_results: Int,
    val total_pages: Int
)