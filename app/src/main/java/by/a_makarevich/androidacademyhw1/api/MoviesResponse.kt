package by.a_makarevich.androidacademyhw1.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListMovieRetrofitResponse(
    @SerialName("results")
    val results: List<MovieRetrofitResponse>
)

@Serializable
data class MovieRetrofitResponse(
    @SerialName("id")
    var id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    var poster: String,
    @SerialName("backdrop_path")
    val backdrop: String,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val numberOfRatings: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("genre_ids")
    val genres_ids: List<Int>
)

