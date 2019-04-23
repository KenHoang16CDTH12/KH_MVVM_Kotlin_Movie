package com.sun.kh_mvvm_kotlin_movie.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sun.kh_mvvm_kotlin_movie.data.model.Keyword
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.Review
import com.sun.kh_mvvm_kotlin_movie.data.model.Video
import com.sun.kh_mvvm_kotlin_movie.data.repository.TvRepository
import com.sun.kh_mvvm_kotlin_movie.util.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class TvDetailViewModel @Inject
constructor(private val repository: TvRepository) : ViewModel() {

  private val keywordIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val keywordListLiveData: LiveData<Resource<List<Keyword>>>

  private val videoIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val videoListLiveData: LiveData<Resource<List<Video>>>

  private val reviewIdLiveData: MutableLiveData<Int> = MutableLiveData()
  private val reviewListLiveData: LiveData<Resource<List<Review>>>

  init {
    Timber.d("Injection TvDetailViewModel")

    keywordListLiveData = Transformations.switchMap(keywordIdLiveData) {
      keywordIdLiveData.value?.let { repository.loadKeywordList(it) }
        ?: AbsentLiveData.create()
    }

    videoListLiveData = Transformations.switchMap(videoIdLiveData) {
      videoIdLiveData.value?.let { repository.loadVideoList(it) } ?: AbsentLiveData.create()
    }

    reviewListLiveData = Transformations.switchMap(reviewIdLiveData) {
      reviewIdLiveData.value?.let { repository.loadReviewsList(it) }
        ?: AbsentLiveData.create()
    }
  }

  fun getKeywordListObservable() = keywordListLiveData
  fun postKeywordId(id: Int) = keywordIdLiveData.postValue(id)

  fun getVideoListObservable() = videoListLiveData
  fun postVideoId(id: Int) = videoIdLiveData.postValue(id)

  fun getReviewListObservable() = reviewListLiveData
  fun postReviewId(id: Int) = reviewIdLiveData.postValue(id)
}