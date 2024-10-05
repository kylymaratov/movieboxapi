package com.example.tvapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
 import com.example.tvapp.R
import com.example.tvapp.api.TsKgRepo
import com.example.tvapp.models.MoviesResponse



class DetailsFragment : Fragment() {
    lateinit var repository: TsKgRepo
    lateinit var txtTitle: TextView
    lateinit var txtCountry: TextView
    lateinit var txtDescription: TextView
    lateinit var txtInfo: TextView
    lateinit var imgBanner: ImageView
    lateinit var seriesListFragment: SeriesListFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBanner = view.findViewById(R.id.img_banner)
        txtTitle = view.findViewById(R.id.title)
        txtCountry = view.findViewById(R.id.country)
        txtDescription = view.findViewById(R.id.desciption)
        txtInfo = view.findViewById(R.id.info)

        seriesListFragment = SeriesListFragment()

        arguments?.getParcelable<MoviesResponse.Result.Detail>("movie")?.let { movie ->
            bindDetailsData(movie)
            movie.seasons?.let { seasons ->
                seriesListFragment.bindSeriesData(movie.movie_id, seasons)
                init()
            }
        }
    }

    fun init() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.series_list_fragment, seriesListFragment)
        transaction.commit()
    }



    fun bindDetailsData(movie: MoviesResponse.Result.Detail) {
        txtTitle.text = movie.title
        txtCountry.text = "Страна · " + movie.country
        txtDescription.text = movie.seasons?.description
        txtInfo.text = "Год: " + movie.year + " | " + "Сезонов: " + movie.seasons?.seasons?.size + " | " + "Качество: " + movie.seasons?.seasons!![0].episodes!![0].quality

        val url = movie.poster_url

        Glide.with(this).load(url).into(imgBanner)
    }

}