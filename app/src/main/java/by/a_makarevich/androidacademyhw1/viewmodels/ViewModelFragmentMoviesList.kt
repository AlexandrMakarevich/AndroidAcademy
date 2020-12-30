package by.a_makarevich.androidacademyhw1.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.loadMovies
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import kotlinx.coroutines.launch

class ViewModelFragmentMoviesList : ViewModel() {

    private val _status = MutableLiveData<StatusResult>()
    val status: LiveData<StatusResult> get() = _status

    private val _movieList = MutableLiveData<List<Movie>>(emptyList())
    val movieList: LiveData<List<Movie>> get() = _movieList

    fun getMovies(context: Context) {
        viewModelScope.launch {
            try {
                _status.value = StatusResult.Loading
                val list = loadMovies(context)
                _movieList.value = list

                _status.value = StatusResult.Success
            } catch (excepcion: Exception) {
                _status.value = StatusResult.Error
            }
        }
    }
}