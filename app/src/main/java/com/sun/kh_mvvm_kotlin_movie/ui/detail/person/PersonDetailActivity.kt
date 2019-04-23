package com.sun.kh_mvvm_kotlin_movie.ui.detail.person

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.sun.kh_mvvm_kotlin_movie.R
import com.sun.kh_mvvm_kotlin_movie.data.model.Resource
import com.sun.kh_mvvm_kotlin_movie.data.model.Status
import com.sun.kh_mvvm_kotlin_movie.data.model.entity.Person
import com.sun.kh_mvvm_kotlin_movie.data.model.network.PersonDetail
import com.sun.kh_mvvm_kotlin_movie.data.source.remote.api.Api
import com.sun.kh_mvvm_kotlin_movie.extension.checkIsMaterialVersion
import com.sun.kh_mvvm_kotlin_movie.extension.observeLiveData
import com.sun.kh_mvvm_kotlin_movie.extension.visible
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
class PersonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(PersonDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        supportPostponeEnterTransition()

        initializeUI()
    }

    private fun initializeUI() {
        toolbar_home.setOnClickListener { onBackPressed() }
        toolbar_title.text = getPersonFromIntent().name
        getPersonFromIntent().profile_path?.let {
            Glide.with(this).load(Api.getPosterPath(it))
                .apply(RequestOptions().circleCrop())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }
                })
                .into(person_detail_profile)
        }
        person_detail_name.text = getPersonFromIntent().name
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getPersonObservable()) { updatePersonDetail(it) }
        viewModel.postPersonId(getPersonFromIntent().id)
    }

    private fun updatePersonDetail(resource: Resource<PersonDetail>) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.SUCCESS -> {
                resource.data?.let {
                    person_detail_biography.text = it.biography
                    detail_person_tags.tags = it.also_known_as
                    if (it.also_known_as.isNotEmpty()) {
                        detail_person_tags.visible()
                    }
                }
            }
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
        }
    }

    private fun getPersonFromIntent(): Person {
        return intent.getParcelableExtra("person") as Person
    }

    companion object {
        const val intent_requestCode = 1000

        fun startActivity(fragment: Fragment, activity: FragmentActivity, person: Person, view: View) {
            if (activity.checkIsMaterialVersion()) {
                val intent = Intent(activity, PersonDetailActivity::class.java)
                ViewCompat.getTransitionName(view)?.let {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
                    intent.putExtra("person", person)
                    activity.startActivityFromFragment(fragment, intent, intent_requestCode, options.toBundle())
                }
            } else {
                activity.startActivityForResult<PersonDetailActivity>(intent_requestCode, "person" to person)
            }
        }
    }
}

