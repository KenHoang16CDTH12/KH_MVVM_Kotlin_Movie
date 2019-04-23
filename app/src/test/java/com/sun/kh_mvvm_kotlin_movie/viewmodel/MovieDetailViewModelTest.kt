package com.sun.kh_mvvm_kotlin_movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sun.kh_mvvm_kotlin_movie.data.model.Keyword
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.Review
import com.sun.kh_mvvm_kotlin_movie.data.model.Video
import com.sun.kh_mvvm_kotlin_movie.data.model.network.KeywordListResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.ReviewListResponse
import com.sun.kh_mvvm_kotlin_movie.data.model.network.VideoListResponse
import com.sun.kh_mvvm_kotlin_movie.data.repository.MovieRepository
import com.sun.kh_mvvm_kotlin_movie.local.dao.MovieDao
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.ApiUtil.successCall
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.MovieService
import com.sun.kh_mvvm_kotlin_movie.ui.detail.movie.MovieDetailViewModel
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockKeywordList
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockMovie
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockReviewList
import com.sun.kh_mvvm_kotlin_movie.util.MockTestUtil.Companion.mockVideoList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailViewModelTest {

  private lateinit var viewModel: MovieDetailViewModel

  private lateinit var repository: MovieRepository
  private val movieDao = mock<MovieDao>()

  private val service = mock<MovieService>()

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun init() {
    repository = MovieRepository(service, movieDao)
    viewModel = MovieDetailViewModel(repository)
  }

  @Test
  fun loadKeywordList() {
    val loadFromDB = mockMovie()
    whenever(movieDao.getMovie(123)).thenReturn(loadFromDB)

    val mockResponse = KeywordListResponse(123, mockKeywordList())
    val call = successCall(mockResponse)
    whenever(service.fetchKeywords(123)).thenReturn(call)

    val data = repository.loadKeywordList(123)
    val observer = mock<Observer<Resource<List<Keyword>>>>()
    data.observeForever(observer)

    viewModel.postKeywordId(123)
    verify(movieDao, times(3)).getMovie(123)
    verify(observer).onChanged(
        Resource.success(mockKeywordList(), true))

    val updatedMovie = mockMovie()
    updatedMovie.keywords = mockKeywordList()
    verify(movieDao).updateMovie(updatedMovie)
  }

  @Test
  fun loadVideoList() {
    val loadFromDB = mockMovie()
    whenever(movieDao.getMovie(123)).thenReturn(loadFromDB)

    val mockResponse = VideoListResponse(123, mockVideoList())
    val call = successCall(mockResponse)
    whenever(service.fetchVideos(123)).thenReturn(call)

    val data = repository.loadVideoList(123)
    val observer = mock<Observer<Resource<List<Video>>>>()
    data.observeForever(observer)

    viewModel.postVideoId(123)
    verify(movieDao, times(3)).getMovie(123)
    verify(observer).onChanged(
        Resource.success(mockVideoList(), true)
    )

    val updatedMovie = mockMovie()
    updatedMovie.videos = mockVideoList()
    verify(movieDao).updateMovie(updatedMovie)
  }

  @Test
  fun loadReviewList() {
    val loadFromDB = mockMovie()
    whenever(movieDao.getMovie(123)).thenReturn(loadFromDB)

    val mockResponse = ReviewListResponse(123, 1, mockReviewList(), 100, 100)
    val call = successCall(mockResponse)
    whenever(service.fetchReviews(123)).thenReturn(call)

    val data = repository.loadReviewsList(123)
    val observer = mock<Observer<Resource<List<Review>>>>()
    data.observeForever(observer)

    viewModel.postReviewId(123)
    verify(movieDao, times(3)).getMovie(123)
    verify(observer).onChanged(
        Resource.success(mockReviewList(), true)
    )

    val updatedMovie = mockMovie()
    updatedMovie.reviews = mockReviewList()
    verify(movieDao).updateMovie(updatedMovie)
  }
}
