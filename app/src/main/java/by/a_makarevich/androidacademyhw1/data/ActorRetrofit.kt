package by.a_makarevich.androidacademyhw1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListActors(
    val actors: ActorRetrofit
)

@Serializable
data class ActorRetrofit(
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("picture")
    val picture: String
)