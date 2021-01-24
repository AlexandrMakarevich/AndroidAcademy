package by.a_makarevich.androidacademyhw1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListGenres(
    @SerialName("genres")
    val genres: List<GenreRetrofit>
)

@Serializable
data class GenreRetrofit(
    val id: Int,
    val name: String
)