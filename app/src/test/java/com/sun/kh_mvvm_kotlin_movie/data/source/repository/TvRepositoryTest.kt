package com.sun.kh_mvvm_kotlin_movie.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.sun.kh_mvvm_kotlin_movie.data.model.Keyword
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.Review
import com.sun.kh_mvvm_kotlin_movie.data.model.Video
import com.sun.kh_mvvm_kotlin_movie.data.model.network.KeywordListResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.ReviewListResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.VideoListResponse
import com.sun.kh_mvvm_kotlin_movie.data.repository.TvRepository
import com.sun.kh_mvvm_kotlin_movie.data.source.local.dao.TvDao
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.ApiUtil.successCall
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.TvService
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockKeywordList
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockTv
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TvRepositoryTest {

  private lateinit var repository: TvRepository
  private val tvDao = mock<TvDao>()
  private val service = mock<TvService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    repository = TvRepository(service, tvDao)
  }

  @Test
  fun loadKeywordListFromNetwork() {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

    val mockResponse = KeywordListResponse(123, MockTestUtil.mockKeywordList())
    val call = successCall(mockResponse)
    whenever(service.fetchKeywords(123)).thenReturn(call)

    val data = repository.loadKeywordList(123)
    verify(tvDao).getTv(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Keyword>>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(MockTestUtil.mockKeywordList(), true))

    val updatedTv = mockTv()
    updatedTv.keywords = mockKeywordList()
    verify(tvDao).updateTv(updatedTv)
  }

  @Test
  fun loadVideoListFromNetwork() {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

    val mockResponse = VideoListResponse(123, MockTestUtil.mockVideoList())
    val call = successCall(mockResponse)
    whenever(service.fetchVideos(123)).thenReturn(call)

    val data = repository.loadVideoList(123)
    verify(tvDao).getTv(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Video>>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(MockTestUtil.mockVideoList(), false))

    val updatedTv = mockTv()
    updatedTv.videos = MockTestUtil.mockVideoList()
    verify(tvDao).updateTv(updatedTv)
  }

  @Test
  fun loadReviewListFromNetwork() {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(123)).thenReturn(loadFromDB)

    val mockResponse = ReviewListResponse(123, 1, MockTestUtil.mockReviewList(), 100, 100)
    val call = successCall(mockResponse)
    whenever(service.fetchReviews(123)).thenReturn(call)

    val data = repository.loadReviewsList(123)
    verify(tvDao).getTv(123)
    verifyNoMoreInteractions(service)

    val observer = mock<Observer<Resource<List<Review>>>>()
    data.observeForever(observer)
    verify(observer).onChanged(Resource.success(MockTestUtil.mockReviewList(), false))

    val updatedTv = mockTv()
    updatedTv.reviews = MockTestUtil.mockReviewList()
    verify(tvDao).updateTv(updatedTv)
  }
}
