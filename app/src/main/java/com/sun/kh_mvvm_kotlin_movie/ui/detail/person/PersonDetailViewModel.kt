package com.sun.kh_mvvm_kotlin_movie.ui.detail.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.network.PersonDetail
import com.sun.kh_mvvm_kotlin_movie.data.repository.PeopleRepository
import com.sun.kh_mvvm_kotlin_movie.util.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class PersonDetailViewModel @Inject
constructor(private val repository: PeopleRepository) : ViewModel() {

  private val personIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val personLiveData: LiveData<Resource<PersonDetail>>

  init {
    Timber.d("Injection : PersonDetailViewModel")

    personLiveData = Transformations.switchMap(personIdLiveData) {
      personIdLiveData.value?.let { repository.loadPersonDetail(it) }
          ?: AbsentLiveData.create()
    }
  }

  fun getPersonObservable() = personLiveData
  fun postPersonId(id: Int) = personIdLiveData.postValue(id)
}