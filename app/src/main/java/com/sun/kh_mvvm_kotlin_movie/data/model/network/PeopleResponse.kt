package com.sun.kh_mvvm_kotlin_movie.data.model.network

import android.app.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel

data class PeopleResponse(
    val page: Int,
    val results: List<Person>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel