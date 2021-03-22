package by.a_makarevich.androidacademyhw1.data

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    var id: Int,
    var name: String
)