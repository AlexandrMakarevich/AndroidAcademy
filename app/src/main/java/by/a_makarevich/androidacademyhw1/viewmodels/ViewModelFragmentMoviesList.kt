package by.a_makarevich.androidacademyhw1.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.loadMovies
import kotlinx.coroutines.launch

class ViewModelFragmentMoviesList : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> get() = _status

    private val _movieList = MutableLiveData<List<Movie>>(emptyList())
    val movieList: LiveData<List<Movie>> get() = _movieList

    fun getMovies(context: Context) {
        viewModelScope.launch {
            // job = CoroutineScope(Dispatchers.Main).launch {
            _status.value = true
            val list = loadMovies(context)
            _movieList.value = list
            _status.value = false
            // }
        }
    }
}