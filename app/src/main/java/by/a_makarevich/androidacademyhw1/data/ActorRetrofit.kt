package by.a_makarevich.androidacademyhw1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListActors(
    val cast: List<ActorRetrofit>
) {
    @Serializable
    data class ActorRetrofit(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val picture: String?
    )
}