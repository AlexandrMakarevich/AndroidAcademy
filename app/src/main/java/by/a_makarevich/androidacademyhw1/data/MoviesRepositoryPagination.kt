package by.a_makarevich.androidacademyhw1.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.a_makarevich.androidacademyhw1.api.MovieDBApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryPagination @Inject constructor (private val movieDBApi: MovieDBApi) {

    fun letMoviesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviesPagingSource(movieDBApi) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 1
    }
}