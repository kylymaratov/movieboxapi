package com.example.tvapp.models

data class SeasonsResponse(
    val seasons: List<Seasons> = listOf(),
    val description: String = "",
    val movie_id: String = ""
) {
    data class Seasons (
        val seasons_id: Number = 0,
        val episodes: List<Episodes> = listOf()
    ) {
        data class Episodes (
            val episode_id: Number = 1,
            val episode_title: String = "",
            val quality: String ="",
            val duration: String = "",
            val episode_source_id: String = ""
        ) {}
    }
}
