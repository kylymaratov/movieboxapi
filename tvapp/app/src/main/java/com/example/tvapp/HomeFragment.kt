package com.example.tvapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tvapp.models.MoviesResponse
import com.example.tvapp.api.Response
import com.example.tvapp.api.TsKgRepo
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var txtTitle: TextView
    lateinit var txtCountry: TextView
    lateinit var txtDescription: TextView
    lateinit var txtInfo: TextView

    lateinit var imgBanner: ImageView
    lateinit var listFragment: ListFragment
    private lateinit var repository: TsKgRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = (requireActivity().application as MyApplication).tsKgRepo

        lifecycleScope.launch {
            repository.getHome()
        }

        setMovies()
        init(view)
    }

      fun init(view: View) {
        imgBanner = view.findViewById(R.id.img_banner)
        txtTitle = view.findViewById(R.id.title)
        txtCountry = view.findViewById(R.id.country)
        txtDescription = view.findViewById(R.id.desciption)
          txtInfo = view.findViewById(R.id.info)


          listFragment = ListFragment()

          var transaction = childFragmentManager.beginTransaction()
          transaction.add(R.id.list_fragment, listFragment);
          transaction.commit()

          listFragment.setOnContentSelectedListener {
              updateBanner(it)
          }

    }


    fun setMovies() {
        repository.movies.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Response.Success -> {
                    if (response.data != null) {
                        listFragment.bindData(response.data)
                    }
                }
                is Response.Error -> {}

                is Response.Loading -> {}
            }
        })
    }


    fun fadeInImage(imageView: ImageView) {
        val fadeIn = AnimationUtils.loadAnimation(imageView.context, R.anim.fade_in)
        imageView.startAnimation(fadeIn)
    }

    fun fadeOutImage(imageView: ImageView) {
        val fadeOut = AnimationUtils.loadAnimation(imageView.context, R.anim.fade_out)
        imageView.startAnimation(fadeOut)
    }


    fun updateBanner(dataList:  MoviesResponse.Result.Detail) {
        txtTitle.text = dataList.title
        txtCountry.text = "Страна · " + dataList.country
        txtDescription.text = dataList.seasons?.description
        txtInfo.text = "Год: " + dataList.year + " | " + "Сезонов: " + dataList.seasons?.seasons?.size + " | " + "Качество: " + dataList.seasons?.seasons!![0].episodes!![0].quality

        val url = dataList.poster_url
        fadeOutImage(imgBanner)
        Glide.with(this).load(url).into(imgBanner)
        fadeInImage(imgBanner)
    }
    }
