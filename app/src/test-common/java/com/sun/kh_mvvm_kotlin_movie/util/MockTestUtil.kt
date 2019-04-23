package com.sun.kh_mvvm_kotlin_movie.util

import com.sun.kh_mvvm_kotlin_movie.data.model.Keyword
import com.sun.kh_mvvm_kotlin_movie.data.model.Review
import com.sun.kh_mvvm_kotlin_movie.data.model.Video
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv
import com.sun.kh_mvvm_kotlin_movie.data.model.network.PersonDetail

class MockTestUtil {
  companion object {
    fun mockMovie() = Movie(1, emptyList(), emptyList(), emptyList(), "", false, "", "", ArrayList(), 123, "", "", "", "", 0f, 0, false, 0f)
    fun mockTv() = Tv(1, emptyList(), emptyList(), emptyList(), "", 0f, 123, "", 0f, "", "", ArrayList(), ArrayList(), "", 1, "", "")
    fun mockPerson() = Person(1, mockPersonDetail(), "", false, 123, "", 0f)
    fun mockKeywordList(): List<Keyword> {
      val keywords = ArrayList<Keyword>()
      keywords.add(Keyword(100, "keyword0"))
      keywords.add(Keyword(101, "keyword1"))
      keywords.add(Keyword(102, "keyword2"))
      return keywords
    }

    fun mockVideoList(): List<Video> {
      val videos = ArrayList<Video>()
      videos.add(Video("123", "video0", "", "", 0, ""))
      videos.add(Video("123", "video0", "", "", 0, ""))
      return videos
    }

    fun mockReviewList(): List<Review> {
      val reviews = ArrayList<Review>()
      reviews.add(Review("123", "", "", ""))
      reviews.add(Review("123", "", "", ""))
      return reviews
    }

    fun mockPersonDetail() = PersonDetail("", "", "", emptyList(), "")
  }
}