package repositories

import by.a_makarevich.androidacademyhw1.FragmentMoviesList
import by.a_makarevich.androidacademyhw1.data.*
import kotlinx.android.synthetic.main.movies_list_item.view.*
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit.RetrofitModule

class MoveDBRepository {

    @ExperimentalSerializationApi
    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {

        val listMoviesResponse = RetrofitModule.movieDBApi.getMoviesNowPlaying()

        val genresMap: List<Genre> =
            RetrofitModule.movieDBApi.getGenre().genres.map { Genre(id = it.id, name = it.name) }

        val listRuntimes: MutableList<MoveRuntime> = mutableListOf()
        listMoviesResponse.results.forEach {
            val runtime: Int = RetrofitModule.movieDBApi.getMovieDetails(it.id).runtime
            listRuntimes.add(MoveRuntime(it.id, runtime))
        }

        return@withContext pasreToMovies(listMoviesResponse, genresMap, listRuntimes)
    }

    private fun pasreToMovies(
        listMoviesResponse: ListMovieRetrofitResponse,
        genres: List<Genre>,
        listRuntimes: List<MoveRuntime>
    ): List<Movie> {
        val movies: MutableList<Movie> = mutableListOf()

        listMoviesResponse.results.forEach { moviesResponse ->
            movies.add(
                Movie(
                    id = moviesResponse.id,
                    title = moviesResponse.title,
                    overview = moviesResponse.overview,
                    poster = "$base_url$poster_sizes${moviesResponse.poster}",
                    backdrop = "$base_url$backdrop_sizes${moviesResponse.backdrop}",
                    ratings = moviesResponse.ratings,
                    numberOfRatings = moviesResponse.numberOfRatings,
                    minimumAge = if (moviesResponse.adult) "16+" else "13+",
                    runtime = getRuntime(listRuntimes, moviesResponse),
                    genres = genres.filter { it.id in moviesResponse.genres_ids },
                    actors = emptyList()
                )
            )
        }
        return movies
    }

    private fun getRuntime(listRuntimes: List<MoveRuntime>, movieResponse: MovieRetrofitResponse) : Int{
        val time = 0
        for (runtime in listRuntimes) {
            if (runtime.id_movie == movieResponse.id) {
                return runtime.time
            }
        }
        return time
    }

    companion object {
        private const val base_url = "https://image.tmdb.org/t/p/"
        private const val backdrop_sizes = "w1280"
        private const val logo_sizes = "w500"
        private const val poster_sizes = "w780"
        private const val profile_sizes = "h632"
        private const val still_sizes = "w300"

        private val TAG = FragmentMoviesList::class.java.simpleName
    }
}