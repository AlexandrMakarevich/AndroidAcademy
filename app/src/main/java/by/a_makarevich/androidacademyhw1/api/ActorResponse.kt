package by.a_makarevich.androidacademyhw1.data

import kotlinx.serialization.Serializable

@Serializable
data class ListActors(
    val cast: List<Actor>
)