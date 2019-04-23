package com.sun.kh_mvvm_kotlin_movie.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.sun.kh_mvvm_kotlin_movie.TestTheMoviesApplication


@Suppress("unused")
class AndroidJunitTestRunner : AndroidJUnitRunner() {
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestTheMoviesApplication::class.java.name, context)
    }
}