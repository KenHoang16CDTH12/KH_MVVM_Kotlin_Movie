package com.sun.kh_mvvm_kotlin_movie.data.model.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.sun.kh_mvvm_kotlin_movie.data.model.Keyword
import com.sun.kh_mvvm_kotlin_movie.data.model.Review
import com.sun.kh_mvvm_kotlin_movie.data.model.Video

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Entity(primaryKeys = [("id")])
data class Movie(
    var page: Int,
    var keywords: List<Keyword>? = ArrayList(),
    var videos: List<Video>? = ArrayList(),
    var reviews: List<Review>? = ArrayList(),
    var poster_path: String?,
    var adult: Boolean,
    var overview: String,
    var release_date: String,
    var genre_ids: List<Int>,
    var id: Int,
    var original_title: String,
    var original_language: String,
    var title: String,
    var backdrop_path: String?,
    var popularity: Float,
    var vote_count: Int,
    var video: Boolean,
    var vote_average: Float
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      ArrayList<Keyword>().apply { source.readList(this, Keyword::class.java.classLoader) },
      ArrayList<Video>().apply { source.readList(this, Video::class.java.classLoader) },
      ArrayList<Review>().apply { source.readList(this, Review::class.java.classLoader) },
      source.readString(),
      1 == source.readInt(),
      source.readString(),
      source.readString(),
      ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readFloat(),
      source.readInt(),
      1 == source.readInt(),
      source.readFloat()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(page)
    writeList(keywords)
    writeList(videos)
    writeList(reviews)
    writeString(poster_path)
    writeByte(if (adult) 1 else 0)
    writeString(overview)
    writeString(release_date)
    writeInt(id)
    writeString(original_title)
    writeString(original_language)
    writeString(title)
    writeString(backdrop_path)
    writeFloat(popularity)
    writeInt(vote_count)
    writeByte(if (video) 1 else 0)
    writeFloat(vote_average)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
      override fun createFromParcel(source: Parcel): Movie = Movie(source)
      override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
    }
  }
}