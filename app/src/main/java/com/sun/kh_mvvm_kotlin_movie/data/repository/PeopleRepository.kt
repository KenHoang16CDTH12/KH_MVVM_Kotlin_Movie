package com.sun.kh_mvvm_kotlin_movie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.network.PeopleResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.PersonDetail
import com.sun.kh_mvvm_kotlin_movie.data.repository.mapper.PeopleResponseMapper
import com.sun.kh_mvvm_kotlin_movie.data.repository.mapper.PersonDetailResponseMapper
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.PeopleDao
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.ApiResponse
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.PeopleService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject
constructor(val peopleService: PeopleService, val peopleDao: PeopleDao)
  : Repository {

  init {
    Timber.d("Injection PeopleRepository")
  }

  fun loadPeople(page: Int): LiveData<Resource<List<Person>>> {
    return object : NetworkBoundRepository<List<Person>, PeopleResponse, PeopleResponseMapper>() {
      override fun saveFetchData(items: PeopleResponse) {
        for (item in items.results) {
          item.page = page
        }
        peopleDao.insertPeople(items.results)
      }

      override fun shouldFetch(data: List<Person>?): Boolean {
        return data == null || data.isEmpty()
      }

      override fun loadFromDb(): LiveData<List<Person>> {
        return peopleDao.getPeople(page_ = page)
      }

      override fun fetchService(): LiveData<ApiResponse<PeopleResponse>> {
        return peopleService.fetchPopularPeople(page = page)
      }

      override fun mapper(): PeopleResponseMapper {
        return PeopleResponseMapper()
      }

      override fun onFetchFailed(message: String?) {
        Timber.d("onFetchFailed : $message")
      }
    }.asLiveData()
  }

  fun loadPersonDetail(id: Int): LiveData<Resource<PersonDetail>> {
    return object : NetworkBoundRepository<PersonDetail, PersonDetail, PersonDetailResponseMapper>() {
      override fun saveFetchData(items: PersonDetail) {
        val person = peopleDao.getPerson(id_ = id)
        person.personDetail = items
        peopleDao.updatePerson(person = person)
      }

      override fun shouldFetch(data: PersonDetail?): Boolean {
        return data == null || data.biography.isEmpty()
      }

      override fun loadFromDb(): LiveData<PersonDetail> {
        val person = peopleDao.getPerson(id_ = id)
        val data: MutableLiveData<PersonDetail> = MutableLiveData()
        data.value = person.personDetail
        return data
      }

      override fun fetchService(): LiveData<ApiResponse<PersonDetail>> {
        return peopleService.fetchPersonDetail(id = id)
      }

      override fun mapper(): PersonDetailResponseMapper {
        return PersonDetailResponseMapper()
      }

      override fun onFetchFailed(message: String?) {
        Timber.d("onFetchFailed : $message")
      }
    }.asLiveData()
  }
}