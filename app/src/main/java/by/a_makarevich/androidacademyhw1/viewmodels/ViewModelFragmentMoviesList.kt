package by.a_makarevich.androidacademyhw1.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import repositories.MovieDBRepository

class ViewModelFragmentMoviesList : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val repository = MovieDBRepository()

    private val _status = MutableLiveData<StatusResult>()
    val status: LiveData<StatusResult> get() = _status

    private val _movieList = MutableLiveData<List<Movie>>(emptyList())
    val movieList: LiveData<List<Movie>> get() = _movieList


    @ExperimentalSerializationApi
    fun getMoviesRetrofit() {
        try {
            scope.launch {
                _status.value = StatusResult.Loading
                val list = repository.loadMovies()
                Log.d("MyLog", "fromViewModel=========================" + list[1].minimumAge)
                _movieList.value = list
                _status.value = StatusResult.Success
            }
        } catch (excepcion: Exception) {
            _status.value = StatusResult.Error
        }
    }
    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}
