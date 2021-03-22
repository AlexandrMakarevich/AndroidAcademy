package by.a_makarevich.androidacademyhw1.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.a_makarevich.androidacademyhw1.api.MovieDBApi
import by.a_makarevich.androidacademyhw1.data.pagination.MoviesPagingMediator
import by.a_makarevich.androidacademyhw1.data.pagination.MoviesPagingSource
import by.a_makarevich.androidacademyhw1.room.MoviesDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesListRepository @Inject constructor (private val movieDBApi: MovieDBApi, private val moviesDatabase: MoviesDatabase) {

    fun letMoviesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviesPagingSource(movieDBApi) }
        ).flow
    }

    @ExperimentalPagingApi
    fun letMoviesDBFlow(pagingConfig: PagingConfig = getDefaultPageConfig()) :
            Flow<PagingData<Movie>> {
        if (movieDBApi == null) throw IllegalStateException("Database is not initialized")
            val pagingSourceFactory = {
                moviesDatabase.getMovieDao().getAll()
        }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = MoviesPagingMediator(movieDBApi, moviesDatabase)
        ).flow


    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 1
    }
}