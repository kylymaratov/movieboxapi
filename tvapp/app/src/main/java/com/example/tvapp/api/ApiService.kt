package com.example.tvapp.api

import com.example.tvapp.models.MoviesResponse
import com.example.tvapp.models.SeriesResponse
import com.example.tvapp.models.VideoInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


data class MovieIdRequest(val movie_id: String)


data class WatchRequest(val movie_id: String, val episode_source_id: String)

interface ApiService {
    @GET("/api/tskg/home")
    suspend fun getHome():Response<MoviesResponse>


    @GET("/api/tskg/search")
    suspend fun search(@Query("query") query: String): Response<MoviesResponse>


    @POST("/api/tskg/episodes")
    suspend fun getMovieSeasons(@Body() movieId: MovieIdRequest): Response<SeriesResponse>


    @POST("/api/tskg/watch")
    suspend fun watchMovie(@Body() watch: WatchRequest): Response<VideoInfoResponse>


}

