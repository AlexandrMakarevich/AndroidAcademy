package by.a_makarevich.androidacademyhw1.api

import by.a_makarevich.androidacademyhw1.data.ListActors
import by.a_makarevich.androidacademyhw1.data.ListGenres
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {
    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(): ListMovieRetrofitResponse

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlayingPagination(
        @Query("page") page: Int,
        @Query("limit") size: Int
    ): ListMovieRetrofitResponse


    @GET("genre/movie/list")
    suspend fun getGenre(): ListGenres

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path("movie_id") movie_id: Int): ListActors
}