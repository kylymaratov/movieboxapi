package com.example.tvapp.models

data class MovieModel(
    val result: List<Result> = listOf()
) {
    data class Result(
        val details: List<Detail> = listOf(),
        val title: String = ""
    ) {
        data class Detail(
            val movie_id: String = "",
            val genre: String = "",
            val country: String = "",
            val title: String = "",
            val poster_url: String = "",
            val year: String = "",
            val description: String = ""
        )
    }
}