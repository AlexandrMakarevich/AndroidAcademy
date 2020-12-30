package by.a_makarevich.androidacademyhw1.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.loadMovies
import kotlinx.coroutines.launch

class ViewModelFragmentMovieDetails : ViewModel() {

    private val _itemmovie = MutableLiveData<Movie>()
    val itemmovie: LiveData<Movie> get() = _itemmovie

    fun getMovie(id: Int?, context: Context) {
        viewModelScope.launch {
            val list = loadMovies(context)
            for (movie in list) {
                if (movie.id == id)  _itemmovie.value = movie
            }
        }
    }

}