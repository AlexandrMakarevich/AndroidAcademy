package by.a_makarevich.androidacademyhw1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRetrofit(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val poster: String,
    @SerialName("backdrop_path")
    val backdrop: String,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val numberOfRatings: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("genres")
    val genres: List<GenreJson>
)

@Serializable
data class GenreJson(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

