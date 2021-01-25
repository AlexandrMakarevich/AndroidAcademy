package by.a_makarevich.androidacademyhw1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.a_makarevich.androidacademyhw1.data.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import repositories.MovieDBRepositoryDetails

class ViewModelFragmentMovieDetails : ViewModel() {

    val repository = MovieDBRepositoryDetails()

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _itemmovie = MutableLiveData<Movie>()
    val itemmovie: LiveData<Movie> get() = _itemmovie

    /*fun getMovie(id: Int?, context: Context) {
        scope.launch {
            val list = loadMovies(context)
            for (movie in list) {
                if (movie.id == id)  _itemmovie.value = movie
            }
        }
    }*/

    fun getMovie(id: Int) {
        scope.launch {
            val movie = repository.loadMovie(id)
            _itemmovie.value = movie
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}