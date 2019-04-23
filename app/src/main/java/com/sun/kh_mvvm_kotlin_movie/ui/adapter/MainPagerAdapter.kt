package com.sun.kh_mvvm_kotlin_movie.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.kh_mvvm_kotlin_movie.ui.main.MovieListFragment
import com.sun.kh_mvvm_kotlin_movie.ui.main.PersonListFragment
import com.sun.kh_mvvm_kotlin_movie.ui.main.TvListFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieListFragment()
            1 -> TvListFragment()
            else -> PersonListFragment()
        }

    override fun getCount() = 3
}