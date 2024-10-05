package com.example.tvapp.presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.tvapp.R
import com.example.tvapp.models.MoviesResponse

class MoviePresenter: Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_movie, parent, false)

        val params = view.layoutParams

        params.width = getWidthInPercent(parent!!.context, 12)
        params.height = getHeightInPercent(parent!!.context, 32)

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

        val imageview = viewHolder?.view?.findViewById<ImageView>(R.id.poster_image)

        val url = content?.poster_url

         Glide.with(viewHolder?.view?.context!!)
            .load(url)
            .skipMemoryCache(false)
            .into(imageview!!)
    }


    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}