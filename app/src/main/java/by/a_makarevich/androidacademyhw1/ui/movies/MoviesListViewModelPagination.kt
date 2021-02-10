package by.a_makarevich.androidacademyhw1.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.MoviesRepositoryPagination
import kotlinx.coroutines.flow.Flow

class MoviesListViewModelPagination @ViewModelInject constructor(val repository: MoviesRepositoryPagination) :
    ViewModel() {

    fun fetchMovies(): Flow<PagingData<Movie>> {
        return repository.letMoviesFlow()
            .cachedIn(viewModelScope)
    }

}