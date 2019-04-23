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
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Tv
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.Api
import kotlinx.android.synthetic.main.item_poster.view.*


class TvListAdapter(private val delegate: TvListViewHolder.Delegate)
  : BaseAdapter() {

  init {
    addSection(ArrayList<Tv>())
  }

  fun addTvList(resource: Resource<List<Tv>>) {
    resource.data?.let {
      sections()[0].addAll(it)
      notifyDataSetChanged()
    }
  }

  override fun layout(sectionRow: SectionRow): Int {
    return R.layout.item_poster
  }

  override fun viewHolder(layout: Int, view: View): BaseViewHolder {
    return TvListViewHolder(view, delegate)
  }

  class TvListViewHolder(val view: View, private val delegate: Delegate)
    : BaseViewHolder(view) {

    interface Delegate {
      fun onItemClick(tv: Tv)
    }

    private lateinit var tv: Tv

    @Throws(Exception::class)
    override fun bindData(data: Any) {
      if (data is Tv) {
        tv = data
        drawItem()
      }
    }

    private fun drawItem() {
      itemView.run {
        item_poster_title.text = tv.name
        tv.poster_path.let {
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
      delegate.onItemClick(tv)
    }

    override fun onLongClick(p0: View?) = false
  }
}
