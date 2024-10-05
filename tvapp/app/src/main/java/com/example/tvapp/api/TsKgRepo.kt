package com.example.tvapp.api

 import androidx.lifecycle.MutableLiveData
import com.example.tvapp.models.MoviesResponse
import com.example.tvapp.models.SeasonsResponse
 import com.example.tvapp.models.VideoInfoResponse

class TsKgRepo(private val service: ApiService) {

    val movies = MutableLiveData<Response<MoviesResponse>>()
    private val movieSeasonsCache: MutableLiveData<Map<String, Response<SeasonsResponse>>> = MutableLiveData(mutableMapOf())

    suspend fun getHome() {
        if (movies.value != null) {
            movies.postValue(movies.value)
        }else {
            try {
                val result = service.getHome()

                if (result.isSuccessful) {
                    movies.postValue(Response.Success(result.body()))

                }else {
                    movies.postValue(Response.Success(null))
                }
            }catch (e: Exception) {
                movies.postValue(Response.Error(e.message.toString()))
            }
        }
    }

    suspend fun getMovieSeasons(movie_id: String): SeasonsResponse? {
         val cachedResponse = movieSeasonsCache.value?.get(movie_id)

         if (cachedResponse is Response.Success) {
            return cachedResponse.data
        }
        return try {
             val result = service.getMovieSeasons(MovieIdRequest(movie_id))

            if (result.isSuccessful) {
                result.body()?.also { seasonsResponse ->
                     val updatedCache = movieSeasonsCache.value?.toMutableMap() ?: mutableMapOf()
                    updatedCache[movie_id] = Response.Success(seasonsResponse)

                     if (updatedCache.size > 20) {
                         val oldestKey = updatedCache.keys.first()
                        updatedCache.remove(oldestKey)
                    }

                    movieSeasonsCache.postValue(updatedCache)
                }
            } else {
                null
            }
        } catch (e: Exception) {
             null
        }
    }


    suspend fun watchMovie(watch: WatchRequest): VideoInfoResponse? {
        return try {
            val result = service.watchMovie(watch)

            if (result.isSuccessful) {
                result.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}