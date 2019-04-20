package com.sun.kh_mvvm_kotlin_movie.data.source.remote.api

object Api {
  private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
  private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
  private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
  private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

  fun getPosterPath(posterPath: String): String =
      BASE_POSTER_PATH + posterPath

  fun getBackdropPath(backdropPath: String): String =
      BASE_BACKDROP_PATH + backdropPath

  fun getYoutubeVideoPath(videoPath: String): String =
      YOUTUBE_VIDEO_URL + videoPath

  fun getYoutubeThumbnailPath(thumbnailPath: String): String =
      "$YOUTUBE_THUMBNAIL_URL + $thumbnailPath + /default.jpg"
}