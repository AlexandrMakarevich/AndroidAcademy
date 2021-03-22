package by.a_makarevich.androidacademyhw1.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import by.a_makarevich.androidacademyhw1.api.ListMovieRetrofitResponse
import by.a_makarevich.androidacademyhw1.api.MovieDBApi
import by.a_makarevich.androidacademyhw1.api.MovieRetrofitResponse
import by.a_makarevich.androidacademyhw1.data.Genre
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.MovieRuntime
import by.a_makarevich.androidacademyhw1.data.RemoteKeys
import by.a_makarevich.androidacademyhw1.room.MoviesDatabase
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MoviesPagingMediator(
    private val movieDBApi: MovieDBApi,
    private val moviesDatabase: MoviesDatabase
) :
    RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val pageKeyData = getPageKeyData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val listMoviesResponse = movieDBApi.getMoviesNowPlayingPaginationMediator()

            val genresMap: List<Genre> =
                movieDBApi.getGenre().genres.map { Genre(id = it.id, name = it.name) }

            val listRuntimes: MutableList<MovieRuntime> = mutableListOf()
            listMoviesResponse.results.forEach {
                val runtime: Int = movieDBApi.getMovieDetails(it.id).runtime
                listRuntimes.add(MovieRuntime(it.id, runtime))
            }
            val movieList = pasreToMovies(listMoviesResponse, genresMap, listRuntimes)


            val isEndOfList = movieList.isEmpty()

            moviesDatabase.withTransaction {
                //clear all data in the database
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.getRepoDao().clearRemoteKeys()
                    moviesDatabase.getMovieDao().deleteAllMovies()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX_) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = movieList.map {
                    RemoteKeys(repoId = it.id, prefKey = prevKey, nextKey = nextKey)
                }
                moviesDatabase.getRepoDao().insertAll(keys)
                moviesDatabase.getMovieDao().insertAll(movieList)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    suspend fun getPageKeyData(loadType: LoadType, state: PagingState<Int, Movie>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX_
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                remoteKeys.prefKey ?: MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prefKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie ->
                moviesDatabase.getRepoDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages
            .lastOrNull() { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie ->
                moviesDatabase.getRepoDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                moviesDatabase.getRepoDao().remoteKeysMovieId(repoId)
            }
        }
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
                    poster = "${base_url}${poster_sizes}${moviesResponse.poster}",
                    backdrop = "${base_url}${backdrop_sizes}${moviesResponse.backdrop}",
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
    }

}

const val DEFAULT_PAGE_INDEX_ = 1