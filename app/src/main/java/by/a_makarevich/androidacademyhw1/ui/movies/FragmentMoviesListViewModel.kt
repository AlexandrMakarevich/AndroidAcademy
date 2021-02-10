package by.a_makarevich.androidacademyhw1.ui.movies

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.MovieDBRepository
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class FragmentMoviesListViewModel @ViewModelInject constructor(
    private val repository: MovieDBRepository
) : ViewModel() {

    private val _status = MutableLiveData<StatusResult>()
    val status: LiveData<StatusResult> get() = _status

    private val _movieList = MutableLiveData<List<Movie>>(emptyList())
    val movieList: LiveData<List<Movie>> get() = _movieList


    @ExperimentalSerializationApi
    fun getMoviesRetrofit() {
        try {
            viewModelScope.launch {
                _status.postValue(StatusResult.Loading)
                _movieList.postValue(repository.loadMovies())
                _status.postValue(StatusResult.Success)
            }
        } catch (exception: Exception) {
            _status.postValue(StatusResult.Error)
            Log.d("MyLog", "Exeption $exception")
        }
    }

    init {
        getMoviesRetrofit()
    }
}
