package com.sun.kh_mvvm_kotlin_movie.data.model.network

import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel