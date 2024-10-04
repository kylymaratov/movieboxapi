package com.example.tvapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.tvapp.api.Response
import com.example.tvapp.api.RetrofitHelper
import com.example.tvapp.api.TsKgRepo
import com.example.tvapp.models.MoviesResponse
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
        dataList.result.forEachIndexed { index, result ->
            val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())


            result.details.forEach {
                arrayObjectAdapter.add(it)
            }

            val headerItem = HeaderItem(result.title)
            val listRow = ListRow(headerItem, arrayObjectAdapter)
            rootAdapter.add(listRow)
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
                        item.seasons = null
                        handler?.removeCallbacks(it)
                    }

                    runnable = Runnable {
                        lifecycleScope.launch {
                            val seasons = repository.getMovieSeasons(item.movie_id)

                            item.seasons = seasons
                            itemSelectedListener?.invoke(item)
                        }
                    }

                    handler?.postDelayed(runnable!!, 1000)
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
                itemClickListener?.invoke(item)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacks(runnable!!)
    }
}