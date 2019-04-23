package com.sun.kh_mvvm_kotlin_movie.ui.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.sun.kh_mvvm_kotlin_movie.R
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewListAdapter : BaseAdapter() {

  init {
    addSection(ArrayList<Review>())
  }

  fun addReviewList(resource: Resource<List<Review>>) {
    resource.data?.let {
      sections()[0].addAll(it)
    }
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow): Int {
    return R.layout.item_review
  }

  override fun viewHolder(layout: Int, view: View): BaseViewHolder {
    return ReviewListViewHolder(view)
  }

  class ReviewListViewHolder(val view: View) : BaseViewHolder(view) {

    private lateinit var review: Review

    override fun bindData(data: Any) {
      if (data is Review) {
        review = data
        drawItem()
      }
    }

    private fun drawItem() {
      itemView.run {
        item_review_title.text = review.author
        item_review_content.text = review.content
      }
    }

    override fun onClick(v: View?) {
    }

    override fun onLongClick(v: View?) = false
  }
}
