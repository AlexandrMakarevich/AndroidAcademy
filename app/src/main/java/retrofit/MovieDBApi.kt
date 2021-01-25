package retrofit

import by.a_makarevich.androidacademyhw1.data.ListActors
import by.a_makarevich.androidacademyhw1.data.ListGenres
import by.a_makarevich.androidacademyhw1.data.ListMovieRetrofitResponse
import by.a_makarevich.androidacademyhw1.data.MovieDetailsRetrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBApi {
    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying() : ListMovieRetrofitResponse

    @GET("genre/movie/list")
    suspend fun getGenre() : ListGenres

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int) : MovieDetailsRetrofit

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path("movie_id") movie_id: Int) : ListActors
}