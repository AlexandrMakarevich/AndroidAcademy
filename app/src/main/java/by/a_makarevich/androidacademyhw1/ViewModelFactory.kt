package by.a_makarevich.androidacademyhw1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.a_makarevich.androidacademyhw1.viewmodels.ViewModelFragmentMovieDetails
import by.a_makarevich.androidacademyhw1.viewmodels.ViewModelFragmentMoviesList

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        ViewModelFragmentMoviesList::class.java -> ViewModelFragmentMoviesList()
        ViewModelFragmentMovieDetails::class.java -> ViewModelFragmentMovieDetails()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}