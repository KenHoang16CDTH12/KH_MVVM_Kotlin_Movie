package com.sun.kh_mvvm_kotlin_movie.ui.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.sun.kh_mvvm_kotlin_movie.R
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Movie
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.Api
import kotlinx.android.synthetic.main.item_poster.view.*

class MovieListAdapter(
    private val delegate: MovieListViewHolder.Delegate
) : BaseAdapter() {

  init {
    addSection(ArrayList<Movie>())
  }

  fun addMovieList(resource: Resource<List<Movie>>) {
    resource.data?.let {
      sections()[0].addAll(it)
      notifyDataSetChanged()
    }
  }

  override fun viewHolder(layout: Int, view: View): BaseViewHolder =
      MovieListViewHolder(view, delegate)

  override fun layout(sectionRow: SectionRow): Int =
      R.layout.item_poster

  class MovieListViewHolder(
      view: View,
      private val delegate: Delegate) : BaseViewHolder(view) {

    private lateinit var movie: Movie

    @Throws(Exception::class)
    override fun bindData(data: Any) {
      if (data is Movie) {
        movie = data
        drawItem()
      }
    }

    private fun drawItem() {
      itemView.run {
        item_poster_title.text = movie.title
        movie.poster_path?.let {
          Glide.with(context)
              .load(Api.getPosterPath(it))
              .listener(GlidePalette.with(Api.getPosterPath(it))
                  .use(BitmapPalette.Profile.VIBRANT)
                  .intoBackground(item_poster_palette)
                  .crossfade(true))
              .into(item_poster_post)
        }
      }
    }

    override fun onClick(v: View?) {
      delegate.onItemClick(movie)
    }

    override fun onLongClick(v: View?) = false

    interface Delegate {
      fun onItemClick(movie: Movie)
    }
  }
}