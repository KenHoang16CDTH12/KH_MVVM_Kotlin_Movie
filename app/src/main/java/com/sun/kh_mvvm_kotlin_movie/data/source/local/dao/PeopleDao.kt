package com.sun.kh_mvvm_kotlin_movie.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person

@Dao
interface PeopleDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertPeople(people: List<Person>)

  @Update
  fun updatePerson(person: Person)

  @Query("SELECT * FROM people WHERE id = :id_")
  fun getPerson(id_: Int): Person

  @Query("SELECT * FROM People WHERE page = :page_")
  fun getPeople(page_: Int): LiveData<List<Person>>
}