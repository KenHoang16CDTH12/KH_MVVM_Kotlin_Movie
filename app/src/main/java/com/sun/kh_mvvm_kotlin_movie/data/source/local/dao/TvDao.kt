package com.sun.kh_mvvm_kotlin_movie.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv

interface TvDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertTv(tvs: List<Tv>)

  @Update
  fun updateTv(tv: Tv)

  @Query("SELECT * FROM Tv WHERE id = :id_")
  fun getTv(id_: Int): Tv

  @Query("SELECT * FROM Tv WHERE page = :page_")
  fun getTvList(page_: Int): LiveData<List<Tv>>
}