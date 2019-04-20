package com.sun.kh_mvvm_kotlin_movie.data.model.network

import com.sun.kh_mvvm_kotlin_movie.data.model.Keyword
import com.sun.kh_mvvm_kotlin_movie.data.model.NetworkResponseModel

data class KeywordListResponse(
    val id: Int,
    val keywords: List<Keyword>
) : NetworkResponseModel