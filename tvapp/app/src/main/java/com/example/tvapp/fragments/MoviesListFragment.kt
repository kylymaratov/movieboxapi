package com.example.tvapp.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.lifecycle.lifecycleScope
import com.example.tvapp.DetailsActivity
import com.example.tvapp.MyApplication
import com.example.tvapp.api.TsKgRepo
import com.example.tvapp.models.MoviesResponse
import com.example.tvapp.presenters.MoviePresenter
import kotlinx.coroutines.launch


class ListFragment : RowsSupportFragment() {
    private lateinit var repository: TsKgRepo
    private var handler: Handler? = null
    private var itemSelectedListener: ((MoviesResponse.Result.Detail) -> Unit)? = null
    private var itemClickListener: ((MoviesResponse.Result.Detail) -> Unit)? = null
    private var runnable: Runnable? = null

    private val listRowPresenter = object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM) {
        override fun isUsingDefaultListSelectEffect(): Boolean {
            return false
        }
    }.apply {
        shadowEnabled = true
    }

    private var rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = (requireActivity().application as MyApplication).tsKgRepo

        adapter = rootAdapter

        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()

        handler = Handler(Looper.getMainLooper());

    }


    fun bindData(dataList: MoviesResponse) {

        val existingHeaderTitles = mutableListOf<String>()

        for (i in 0 until rootAdapter.size()) {
            val row = rootAdapter.get(i) as ListRow
            existingHeaderTitles.add(row.headerItem.name)
        }

        dataList.result.forEachIndexed { index, result ->
            if (!existingHeaderTitles.contains(result.title)) {
                val arrayObjectAdapter = ArrayObjectAdapter(MoviePresenter())


                result.details.forEach {
                    arrayObjectAdapter.add(it)
                }

                val headerItem = HeaderItem(result.title)
                val listRow = ListRow(headerItem, arrayObjectAdapter)
                rootAdapter.add(listRow)
            }
        }
    }

    fun setOnContentSelectedListener(listener: (MoviesResponse.Result.Detail) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (MoviesResponse.Result.Detail) -> Unit) {
        this.itemClickListener = listener
    }

    inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is MoviesResponse.Result.Detail) {
                    runnable?.let {
                        handler?.removeCallbacks(it)
                    }

                    runnable = Runnable {
                        lifecycleScope.launch {
                            if (item.seasons != null) {
                                itemSelectedListener?.invoke(item)
                            }else {
                                val seasons = repository.getMovieSeasons(item.movie_id)

                                item.seasons = seasons
                                itemSelectedListener?.invoke(item)
                            }

                        }
                    }

                    handler?.postDelayed(runnable!!, 700)
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
            if (item is MoviesResponse.Result.Detail) {
                if (item.seasons != null) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("movie", item)
                    context?.startActivity(intent)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacks(runnable!!)
    }
}