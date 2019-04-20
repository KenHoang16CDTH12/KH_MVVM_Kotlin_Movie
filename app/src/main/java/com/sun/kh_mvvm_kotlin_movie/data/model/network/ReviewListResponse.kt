package com.sun.kh_mvvm_kotlin_movie.data.model.network

import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel
import com.sun.kh_mvvm_kotlin_movie.data.model.Review

class ReviewListResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
) : NetworkResponseModel