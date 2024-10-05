package com.example.tvapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.tvapp.fragments.DetailsFragment
import com.example.tvapp.models.MoviesResponse

class DetailsActivity : FragmentActivity() {
    lateinit var detailsFragment: DetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie = intent.getParcelableExtra<MoviesResponse.Result.Detail>("movie")

        movie?.let {
            init(movie)
        }
    }

    fun init (movie: MoviesResponse.Result.Detail ) {
        detailsFragment = DetailsFragment()

        val bundle = Bundle().apply {
            putParcelable("movie", movie)
        }

        detailsFragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.details_container, detailsFragment)
        transaction.commit()
    }
}