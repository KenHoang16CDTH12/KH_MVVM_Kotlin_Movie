package com.sun.kh_mvvm_kotlin_movie.ui.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.sun.kh_mvvm_kotlin_movie.R
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.Api
import kotlinx.android.synthetic.main.item_person.view.*

class PeopleAdapter(val delegate: PeopleViewHolder.Delegate)
  : BaseAdapter() {

  init {
    addSection(ArrayList<Person>())
  }

  fun addPeople(resource: Resource<List<Person>>) {
    resource.data?.let {
      sections()[0].addAll(resource.data)
      notifyDataSetChanged()
    }
  }

  override fun layout(sectionRow: SectionRow): Int {
    return R.layout.item_person
  }

  override fun viewHolder(layout: Int, view: View): BaseViewHolder {
    return PeopleViewHolder(view, delegate)
  }

  class PeopleViewHolder(private val view: View, private val delegate: Delegate)
    : BaseViewHolder(view) {

    interface Delegate {
      fun onItemClick(person: Person, view: View)
    }

    private lateinit var person: Person

    @Throws(Exception::class)
    override fun bindData(data: Any) {
      if (data is Person) {
        person = data
        drawItem()
      }
    }

    private fun drawItem() {
      itemView.run {
        item_person_name.text = person.name
        person.profile_path?.let {
          Glide.with(context)
              .load(Api.getPosterPath(it))
              .apply(RequestOptions().circleCrop())
              .into(item_person_profile)
        }
      }
    }

    override fun onClick(p0: View?) {
      delegate.onItemClick(person, itemView.item_person_profile)
    }

    override fun onLongClick(p0: View?) = false
  }
}
