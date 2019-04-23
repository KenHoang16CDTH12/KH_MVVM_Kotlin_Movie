package com.sun.kh_mvvm_kotlin_movie.extension

import android.view.View

fun View.visible() { visibility = View.VISIBLE }

fun View.inVisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }