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
import com.sun.kh_mvvm_kotlin_movie.data.model.Video
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.Api
import kotlinx.android.synthetic.main.item_video.view.*

class VideoListAdapter(private val delegate: VideoListViewHolder.Delegate)
  : BaseAdapter() {

  init {
    addSection(ArrayList<Video>())
  }

  fun addVideoList(resource: Resource<List<Video>>) {
    resource.data?.let {
      sections()[0].addAll(it)
    }
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow): Int {
    return R.layout.item_video
  }

  override fun viewHolder(layout: Int, view: View): BaseViewHolder {
    return VideoListViewHolder(view, delegate)
  }

  class VideoListViewHolder(val view: View, private val delegate: Delegate)
    : BaseViewHolder(view) {

    interface Delegate {
      fun onItemClicked(video: Video)
    }

    private lateinit var video: Video

    override fun bindData(data: Any) {
      if (data is Video) {
        video = data
        drawItem()
      }
    }

    private fun drawItem() {
      itemView.run {
        item_video_title.text = video.name
        Glide.with(context)
            .load(Api.getYoutubeThumbnailPath(video.key))
            .listener(GlidePalette.with(Api.getYoutubeThumbnailPath(video.key))
                .use(BitmapPalette.Profile.VIBRANT)
                .intoBackground(item_video_palette)
                .crossfade(true))
            .into(item_video_cover)
      }
    }

    override fun onClick(v: View?) {
      delegate.onItemClicked(video)
    }

    override fun onLongClick(v: View?) = false
  }
}
