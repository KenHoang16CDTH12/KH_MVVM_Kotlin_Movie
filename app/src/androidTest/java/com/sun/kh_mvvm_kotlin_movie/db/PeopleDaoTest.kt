package com.sun.kh_mvvm_kotlin_movie.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.util.LiveDataTestUtil.getValue
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockPerson
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PeopleDaoTest : DbTest() {

    @Test
    fun insertAndRead() {
        val people = ArrayList<Person>()
        val mockPerson = mockPerson()
        people.add(mockPerson)

        db.peopleDao().insertPeople(people)
        val loadFromDB = getValue(db.peopleDao().getPeople(1))[0]
        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(loadFromDB.id, CoreMatchers.`is`(123))
    }

    @Test
    fun updateAndRead() {
        val people = ArrayList<Person>()
        val mockPerson = mockPerson()
        people.add(mockPerson)
        db.peopleDao().insertPeople(people)

        val loadFromDB = db.peopleDao().getPerson(mockPerson.id)
        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))

        mockPerson.page = 10
        db.peopleDao().updatePerson(mockPerson)

        val updated = db.peopleDao().getPerson(mockPerson.id)
        MatcherAssert.assertThat(updated.page, CoreMatchers.`is`(10))
    }
}
