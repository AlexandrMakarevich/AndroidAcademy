package by.a_makarevich.androidacademyhw1.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.MoviesListRepository
import kotlinx.coroutines.flow.Flow

class FragmentMoviesListViewModel @ViewModelInject constructor(private val repository: MoviesListRepository) :
    ViewModel() {

    fun fetchMovies(): Flow<PagingData<Movie>> {
        return repository.letMoviesFlow()
            .cachedIn(viewModelScope)
    }

    @ExperimentalPagingApi
    fun fetchMoviesDB(): Flow<PagingData<Movie>> {
        return repository.letMoviesDBFlow()
            .cachedIn(viewModelScope)
    }


}