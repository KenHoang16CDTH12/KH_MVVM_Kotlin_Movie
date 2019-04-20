package com.sun.kh_mvvm_kotlin_movie.data.model.network

import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv

data class DiscoverTvResponse(
    val page: Int,
    val results: List<Tv>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel