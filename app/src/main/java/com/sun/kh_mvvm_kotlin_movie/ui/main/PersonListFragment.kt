package com.sun.kh_mvvm_kotlin_movie.ui.main


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.sun.kh_mvvm_kotlin_movie.R
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.Status
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.extension.observeLiveData
import com.sun.kh_mvvm_kotlin_movie.ui.adapter.PeopleAdapter
import com.sun.kh_mvvm_kotlin_movie.ui.detail.person.PersonDetailActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class PersonListFragment : Fragment(), PeopleAdapter.PeopleViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: MainActivityViewModel

  private val adapter = PeopleAdapter(this)
  private lateinit var paginator: RecyclerViewPaginator

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.main_fragment_star, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
  }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
    observeViewModel()
  }

  private fun initializeUI() {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = GridLayoutManager(context, 2)
    paginator = RecyclerViewPaginator(
      recyclerView = recyclerView,
      isLoading = { viewModel.getPeopleValues()?.status == Status.LOADING },
      loadMore = { loadMore(it) },
      onLast = { viewModel.getPeopleValues()?.onLastPage!! })
  }

  private fun observeViewModel() {
    observeLiveData(viewModel.getPeopleObservable()) { updatePeople(it) }
    viewModel.postPeoplePage(1)
  }

  private fun updatePeople(resource: Resource<List<Person>>) {
    when (resource.status) {
      Status.LOADING -> Unit
      Status.SUCCESS -> adapter.addPeople(resource)
      Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
    }
  }

  private fun loadMore(page: Int) {
    viewModel.postPeoplePage(page)
  }

  override fun onItemClick(person: Person, view: View) {
    activity?.let {
      PersonDetailActivity.startActivity(this, it, person, view)
    } ?: startActivity<PersonDetailActivity>("person" to person)
  }
}
