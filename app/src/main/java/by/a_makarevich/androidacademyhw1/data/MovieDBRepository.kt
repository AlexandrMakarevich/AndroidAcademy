package by.a_makarevich.androidacademyhw1.data

import by.a_makarevich.androidacademyhw1.api.ListMovieRetrofitResponse
import by.a_makarevich.androidacademyhw1.api.MovieDBApi
import by.a_makarevich.androidacademyhw1.api.MovieRetrofitResponse
import by.a_makarevich.androidacademyhw1.ui.movies.FragmentMoviesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDBRepository @Inject constructor(private val movieDBApi: MovieDBApi) {


     suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {

         val listMoviesResponse = movieDBApi.getMoviesNowPlaying()

         val genresMap: List<Genre> =
             movieDBApi.getGenre().genres.map { Genre(id = it.id, name = it.name) }

         val listRuntimes: MutableList<MovieRuntime> = mutableListOf()
         listMoviesResponse.results.forEach {
             val runtime: Int = movieDBApi.getMovieDetails(it.id).runtime
             listRuntimes.add(MovieRuntime(it.id, runtime))
         }
         return@withContext pasreToMovies(listMoviesResponse, genresMap, listRuntimes)
     }

     private fun pasreToMovies(
         listMoviesResponse: ListMovieRetrofitResponse,
         genres: List<Genre>,
         listRuntimes: List<MovieRuntime>
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

     private fun getRuntime(
         listRuntimes: List<MovieRuntime>,
         movieResponse: MovieRetrofitResponse
     ): Int {
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