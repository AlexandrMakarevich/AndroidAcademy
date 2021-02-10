package by.a_makarevich.androidacademyhw1.data

import by.a_makarevich.androidacademyhw1.api.MovieDBApi
import by.a_makarevich.androidacademyhw1.api.MovieDetailsResponse
import by.a_makarevich.androidacademyhw1.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(private val movieDBApi: MovieDBApi) {

    @ExperimentalSerializationApi
    suspend fun loadMovie(id: Int): Movie = withContext(Dispatchers.IO) {

        val movieCast: ListActors = movieDBApi.getActors(id)
        val movieDetailsRetrofit = movieDBApi.getMovieDetails(id)

        return@withContext parseToMovie(movieDetailsRetrofit, movieCast)
    }

    private fun parseToMovie(
        movieDetailsRetrofit: MovieDetailsResponse,
        movieCast: ListActors
    ): Movie {
        return Movie(
            id = movieDetailsRetrofit.id,
            title = movieDetailsRetrofit.title,
            overview = movieDetailsRetrofit.overview,
            poster = "$base_url$poster_sizes${movieDetailsRetrofit.poster}",
            backdrop = "$base_url$backdrop_sizes${movieDetailsRetrofit.backdrop}",
            ratings = movieDetailsRetrofit.ratings,
            numberOfRatings = movieDetailsRetrofit.numberOfRatings,
            minimumAge = if (movieDetailsRetrofit.adult) "16+" else "13+",
            runtime = movieDetailsRetrofit.runtime,
            genres = movieDetailsRetrofit.genres.map { Genre(it.id, it.name) },
            actors = movieCast.cast.map {
                Actor(
                    it.id,
                    it.name,
                    "$base_url$profile_sizes${it.picture}"
                )
            }
        )
    }

    companion object {
        private const val base_url = "https://image.tmdb.org/t/p/"
        private const val backdrop_sizes = "w1280"
        private const val logo_sizes = "w500"
        private const val poster_sizes = "w780"
        private const val profile_sizes = "h632"
        private const val still_sizes = "w300"

    }
}