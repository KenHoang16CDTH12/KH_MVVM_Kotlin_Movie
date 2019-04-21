package com.sun.kh_mvvm_kotlin_movie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv
import com.sun.kh_mvvm_kotlin_movie.data.repository.DiscoverRepository
import com.sun.kh_mvvm_kotlin_movie.data.repository.PeopleRepository
import com.sun.kh_mvvm_kotlin_movie.util.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository,
    private val peopleRepository: PeopleRepository
) : ViewModel() {

  private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
  private val movieListLiveData: LiveData<Resource<List<Movie>>>

  private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
  private val tvListLiveData: LiveData<Resource<List<Tv>>>

  private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
  private val peopleLiveData: LiveData<Resource<List<Person>>>

  init {
    Timber.d("injection MainActivityViewModel")

    movieListLiveData = switchMap(moviePageLiveData) {
      moviePageLiveData.value?.let { discoverRepository.loadMovies(it) }
          ?: AbsentLiveData.create()
    }

    tvListLiveData = switchMap(tvPageLiveData) {
      tvPageLiveData.value?.let { discoverRepository.loadTvs(it) }
          ?: AbsentLiveData.create()
    }

    peopleLiveData = Transformations.switchMap(peoplePageLiveData) {
      peoplePageLiveData.value?.let { peopleRepository.loadPeople(it) }
          ?: AbsentLiveData.create()
    }
  }

  fun getMovieListObservable() = movieListLiveData
  fun getMovieListValues() = getMovieListObservable().value
  fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

  fun getTvListObservable() = tvListLiveData
  fun getTvListValues() = getTvListObservable().value
  fun postTvPage(page: Int) = tvPageLiveData.postValue(page)

  fun getPeopleObservable() = peopleLiveData
  fun getPeopleValues() = getPeopleObservable().value
  fun postPeoplePage(page: Int) = peoplePageLiveData.postValue(page)
}