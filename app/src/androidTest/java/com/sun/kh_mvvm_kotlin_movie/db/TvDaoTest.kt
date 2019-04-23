package com.sun.kh_mvvm_kotlin_movie.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv
import com.sun.kh_mvvm_kotlin_movie.util.LiveDataTestUtil.getValue
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockTv
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TvDaoTest : DbTest() {

    @Test
    fun insertAndRead() {
        val tvList = ArrayList<Tv>()
        val tv = mockTv()
        tvList.add(tv)

        db.tvDao().insertTv(tvList)
        val loadFromDB = getValue(db.tvDao().getTvList(tv.page))[0]
        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loadFromDB.id, CoreMatchers.`is`(123))
    }

    @Test
    fun updateAndReadTest() {
        val tvList = ArrayList<Tv>()
        val tv = mockTv()
        tvList.add(tv)
        db.tvDao().insertTv(tvList)

        val loadFromDB = db.tvDao().getTv(tv.id)
        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))

        tv.page = 10
        db.tvDao().updateTv(tv)

        val updated = db.tvDao().getTv(tv.id)
        MatcherAssert.assertThat(updated.page, CoreMatchers.`is`(10))
    }
}