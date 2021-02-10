package by.a_makarevich.androidacademyhw1.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.MovieDetailsRepository
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FragmentMovieDetailsViewModel @ViewModelInject constructor (private val repository: MovieDetailsRepository) :
    ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _status = MutableLiveData<StatusResult>()
    val status: LiveData<StatusResult> get() = _status

    private val _itemmovie = MutableLiveData<Movie>()
    val itemmovie: LiveData<Movie> get() = _itemmovie


    fun getMovie(id: Int) {
        scope.launch {
            _status.postValue(StatusResult.Loading)
            _itemmovie.postValue(repository.loadMovie(id))
            _status.postValue(StatusResult.Success)
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}