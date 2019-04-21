package com.sun.kh_mvvm_kotlin_movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.network.PersonDetail
import com.sun.kh_mvvm_kotlin_movie.data.repository.PeopleRepository
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.PeopleDao
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.ApiUtil.successCall
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.PeopleService
import com.sun.kh_mvvm_kotlin_movie.ui.detail.person.PersonDetailViewModel
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockPerson
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockPersonDetail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PersonDetailViewModelTest {

  private lateinit var viewModel: PersonDetailViewModel

  private lateinit var repository: PeopleRepository
  private val peopleDao = mock<PeopleDao>()
  private val service = mock<PeopleService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    repository = PeopleRepository(service, peopleDao)
    viewModel = PersonDetailViewModel(repository)
  }

  @Test
  fun loadPersonDetail() {
    val loadFromDB = mockPerson()
    whenever(peopleDao.getPerson(123)).thenReturn(loadFromDB)

    val mockResponse = mockPersonDetail()
    val call = successCall(mockResponse)
    whenever(service.fetchPersonDetail(123)).thenReturn(call)

    val data = repository.loadPersonDetail(123)
    val observer = mock<Observer<Resource<PersonDetail>>>()
    data.observeForever(observer)

    viewModel.postPersonId(123)
    verify(peopleDao, times(3)).getPerson(123)
    verify(observer).onChanged(
        Resource.success(mockPersonDetail(), true))

    val updatedPerson = mockPerson()
    updatedPerson.personDetail = mockPersonDetail()
    verify(peopleDao).updatePerson(updatedPerson)
  }
}
