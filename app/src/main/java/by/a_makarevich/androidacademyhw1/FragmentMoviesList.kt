package by.a_makarevich.androidacademyhw1

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.adapter.MovieAdapter
import by.a_makarevich.androidacademyhw1.adapter.OnClickListenerDetail
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import by.a_makarevich.androidacademyhw1.viewmodels.ViewModelFragmentMoviesList
import kotlinx.serialization.ExperimentalSerializationApi


class FragmentMoviesList : Fragment(), OnClickListenerDetail {

    private val viewModel: ViewModelFragmentMoviesList by viewModels { ViewModelFactory() }

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private val movieAdapter = MovieAdapter(this)

    @ExperimentalSerializationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyLog", "FragmentMoviesList_onCreateView")
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        viewModel.movieList.observe(this.viewLifecycleOwner) {
            updateMovieAdapter(it)
        }

        viewModel.status.observe(this.viewLifecycleOwner) {
            setProgressBar(it)
        }

        initViews(view)
        setUpMovieAdapter()
        viewModel.getMoviesRetrofit()

        return view
    }

    override fun onDestroyView() {
        clearCashViews()
        super.onDestroyView()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    private fun clearCashViews() {
        recyclerView = null
        progressBar = null
    }

    private fun setUpMovieAdapter() {
        val layoutManager = recyclerView?.layoutManager as GridLayoutManager
        recyclerView.apply {
            this?.addItemDecoration(MovieListItemDecoration(50, numberSpans(layoutManager)))
            this?.adapter = movieAdapter
        }
    }

    private fun updateMovieAdapter(movies: List<Movie>) {
        Log.d("MyLog", "FragmentMovieList_updateMovieAdapter")
        movieAdapter.setData(movies)
    }


    private fun numberSpans(layoutManager: GridLayoutManager): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.spanCount = 2
            2
        } else {
            layoutManager.spanCount = 4
            4
        }
    }


    private fun setProgressBar(status: StatusResult) {
        when (status) {
            StatusResult.Loading -> progressBar?.visibility = VISIBLE
            StatusResult.Success -> progressBar?.visibility = INVISIBLE
            StatusResult.Error -> progressBar?.visibility = INVISIBLE
        }
    }

    override fun onItemClick(movie_id: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIEID, movie_id)
        findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMovieDetails, bundle)
    }

    companion object {
        const val MOVIEID = "movieId"
    }

}
