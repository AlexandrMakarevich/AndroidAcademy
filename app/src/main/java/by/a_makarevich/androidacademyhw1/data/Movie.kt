package by.a_makarevich.androidacademyhw1.data

data class Movie(
    var id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: String,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>
)