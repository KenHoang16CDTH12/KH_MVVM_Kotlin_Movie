package com.sun.kh_mvvm_kotlin_movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv
import com.sun.kh_mvvm_kotlin_movie.data.model.network.DiscoverMovieResponse
import com.sun.kh_mvvm_kotlin_movie.data.repository.DiscoverRepository
import com.sun.kh_mvvm_kotlin_movie.data.repository.PeopleRepository
import com.sun.kh_mvvm_kotlin_movie.local.dao.MovieDao
import com.sun.kh_mvvm_kotlin_movie.local.dao.PeopleDao
import com.sun.kh_mvvm_kotlin_movie.local.dao.TvDao
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.ApiUtil.successCall
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.PeopleService
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.TheDiscoverService
import com.sun.kh_mvvm_kotlin_movie.ui.main.MainActivityViewModel
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockMovie
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockPerson
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockTv
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityViewModelTest {

  private lateinit var viewModel: MainActivityViewModel
  private lateinit var discoverRepository: DiscoverRepository
  private lateinit var peopleRepository: PeopleRepository

  private val movieDao = mock<MovieDao>()
  private val tvDao = mock<TvDao>()
  private val peopleDao = mock<PeopleDao>()
  private val discoverService = mock<TheDiscoverService>()
  private val peopleService = mock<PeopleService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    discoverRepository = DiscoverRepository(discoverService, movieDao, tvDao)
    peopleRepository = PeopleRepository(peopleService, peopleDao)
    viewModel = MainActivityViewModel(discoverRepository, peopleRepository)
  }

  @Test
  fun loadMovieList() {
    val loadFromDB = MutableLiveData<List<Movie>>()
    whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

    val mockResponse = DiscoverMovieResponse(1, emptyList(), 100, 10)
    val call = successCall(mockResponse)
    whenever(discoverService.fetchDiscoverMovie(1)).thenReturn(call)

    val data = viewModel.getMovieListObservable()
    val observer = mock<Observer<Resource<List<Movie>>>>()
    data.observeForever(observer)

    viewModel.postMoviePage(1)
    verify(movieDao).getMovieList(1)
    verifyNoMoreInteractions(discoverService)

    val mockMovieList = ArrayList<Movie>()
    mockMovieList.add(mockMovie())
    loadFromDB.postValue(mockMovieList)
    verify(observer).onChanged(
        Resource.success(viewModel.getMovieListValues()!!.data, false)
    )
  }

  @Test
  fun loadTvList() {
    val loadFromDB = MutableLiveData<List<Tv>>()
    whenever(tvDao.getTvList(1)).thenReturn(loadFromDB)

    val data = viewModel.getTvListObservable()
    val observer = mock<Observer<Resource<List<Tv>>>>()
    data.observeForever(observer)

    viewModel.postTvPage(1)
    verify(tvDao).getTvList(1)
    verifyNoMoreInteractions(discoverService)

    val mockTvList = ArrayList<Tv>()
    mockTvList.add(mockTv())
    loadFromDB.postValue(mockTvList)
    verify(observer).onChanged(
        Resource.success(viewModel.getTvListValues()!!.data, false)
    )
  }

  @Test
  fun loadPeople() {
    val loadFromDB = MutableLiveData<List<Person>>()
    whenever(peopleDao.getPeople(1)).thenReturn(loadFromDB)

    val data = viewModel.getPeopleObservable()
    val observer = mock<Observer<Resource<List<Person>>>>()
    data.observeForever(observer)

    viewModel.postPeoplePage(1)
    verify(peopleDao).getPeople(1)
    verifyNoMoreInteractions(peopleService)

    val mockPeople = ArrayList<Person>()
    mockPeople.add(mockPerson())
    loadFromDB.postValue(mockPeople)
    verify(observer).onChanged(
        Resource.success(viewModel.getPeopleValues()!!.data, false)
    )
  }
}