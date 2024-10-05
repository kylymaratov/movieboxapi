package com.example.tvapp.fragments


 import android.os.Bundle
 import android.view.View

import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import com.example.tvapp.models.MoviesResponse
import com.example.tvapp.models.SeasonsResponse
 import com.example.tvapp.presenters.SeriaPresenter
 import android.content.Intent
 import androidx.lifecycle.lifecycleScope
 import com.example.tvapp.MyApplication
 import com.example.tvapp.VideoPlayerActivity
 import com.example.tvapp.api.TsKgRepo
 import com.example.tvapp.api.WatchRequest
 import kotlinx.coroutines.launch

class SeriesListFragment : RowsSupportFragment() {
    private var itemSelectedListener: ((MoviesResponse.Result.Detail) -> Unit)? = null
    private lateinit var movie_id: String

    private lateinit var repository: TsKgRepo
    private val listRowPresenter = object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL) {
        override fun isUsingDefaultListSelectEffect(): Boolean {
            return true
        }
    }.apply {
        shadowEnabled = false
    }

    private val rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(listRowPresenter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = rootAdapter

        repository = (requireActivity().application as MyApplication).tsKgRepo


        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()
    }

    fun bindSeriesData(icnoming_movie_id: String, seasons: SeasonsResponse) {
        movie_id = icnoming_movie_id
        seasons.seasons.forEachIndexed { index, season ->
            val arrayObjectAdapter = ArrayObjectAdapter(SeriaPresenter())


            season.episodes.forEach {
                arrayObjectAdapter.add(it)
            }
            val headerItem = HeaderItem("Сезон: ${season.season_id}")
            val listRow = ListRow(headerItem, arrayObjectAdapter)

            rootAdapter.add(listRow)
        }
    }


    private fun openVideo(m3u8Link: String) {
        val intent = Intent(context, VideoPlayerActivity::class.java).apply {
            putExtra("VIDEO_URL", m3u8Link)
        }
        startActivity(intent)
    }


    inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is MoviesResponse.Result.Detail) {
                itemSelectedListener?.invoke(item)

                }

            }

        }

    inner class ItemViewClickListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is SeasonsResponse.Episodes) {
                lifecycleScope.launch {
                    val info = repository.watchMovie(WatchRequest(movie_id = movie_id, episode_source_id = item.episode_source_id))

                    if ( info?.video?.url != null) {
                        openVideo(info.video.url)
                    }

                }
            }
        }

    }
}