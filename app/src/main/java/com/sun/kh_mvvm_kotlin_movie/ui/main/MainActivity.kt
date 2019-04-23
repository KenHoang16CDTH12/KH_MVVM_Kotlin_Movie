package com.sun.kh_mvvm_kotlin_movie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sun.kh_mvvm_kotlin_movie.R
import com.sun.kh_mvvm_kotlin_movie.ui.adapter.MainPagerAdapter
import com.sun.kh_mvvm_kotlin_movie.util.MainNavigationUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initializeUI()
  }

  private fun initializeUI() {
    main_viewpager.adapter = MainPagerAdapter(supportFragmentManager)
    main_viewpager.offscreenPageLimit = 3
    MainNavigationUtil.setComponents(this, main_viewpager, main_bottom_navigation)
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return fragmentInjector
  }
}
