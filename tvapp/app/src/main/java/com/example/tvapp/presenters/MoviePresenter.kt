package com.example.tvapp.presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.tvapp.R
import com.example.tvapp.models.MoviesResponse

class MoviePresenter: Presenter() {

    lateinit var movie_title: TextView
    lateinit var movie_cover: ImageView

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_movie, parent, false)

        val params = view.layoutParams

        params.width = getWidthInPercent(parent!!.context, 12)

        movie_title = view.findViewById(R.id.movie_title)
        movie_cover= view.findViewById(R.id.poster_image)

        return ViewHolder(view)
    }


    fun getWidthInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.widthPixels ?: 0
        return (width * percent) / 100
    }

    fun getHeightInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.heightPixels ?: 0
        return (width * percent) / 100
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val content = item as? MoviesResponse.Result.Detail

        val url = content?.poster_url

        if (content?.search_title != null) {
            movie_title.text = content.title
            movie_title.visibility = View.VISIBLE
        }

        if (content?.search_title == null && viewHolder?.view != null) {
            val params = viewHolder.view.layoutParams
            params.height =  getHeightInPercent(viewHolder.view?.context!!, 32)
            viewHolder.view.layoutParams = params
        }

         Glide.with(viewHolder?.view?.context!!)
            .load(url)
            .skipMemoryCache(false)
            .into(movie_cover)
    }


    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}