package by.a_makarevich.androidacademyhw1.ui.movies

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.R
import by.a_makarevich.androidacademyhw1.adapter.MovieAdapterPagination
import by.a_makarevich.androidacademyhw1.adapter.MovieListItemDecoration
import by.a_makarevich.androidacademyhw1.adapter.OnClickListenerDetailPagination
import by.a_makarevich.androidacademyhw1.utils.StatusResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentMoviesList : Fragment(R.layout.fragment_movies_list),
    OnClickListenerDetailPagination {

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    /*private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!*/

    //!!!!  private val viewModel by viewModels<FragmentMoviesListViewModel>()


    private val viewModel by viewModels<MoviesListViewModelPagination>()


    //private val viewModel: FragmentMoviesListViewModel by viewModels { ViewModelFactory() }

    //!!!! private val movieAdapter = MovieAdapter(this)

    private val movieAdapter = MovieAdapterPagination(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyLog", "FragmentMoviesList_onCreate")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  _binding = FragmentMoviesListBinding.bind(view)
        initViews(view)
        setUpMovieAdapter()
        setMovieAdapterStateListener()
        fetchMovies()
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            viewModel.fetchMovies().distinctUntilChanged().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onDestroyView() {
        clearCashViews()
        super.onDestroyView()
    }

    private fun clearCashViews() {
        recyclerView = null
        progressBar = null
    }

    //!!!!!!
    /* private fun setUpObservers(){
         viewModel.movieList.observe(this.viewLifecycleOwner) {
             updateMovieAdapter(it)
         }
         viewModel.status.observe(this.viewLifecycleOwner) {
             setProgressBar(it)
         }
     }*/
    //!!!!!!!

    private fun setUpMovieAdapter() {
        Log.d("MyLog", "FragmentMovieList_setUpMovieAdapter")
        val layoutManager = recyclerView?.layoutManager as GridLayoutManager
        recyclerView?.apply {
            setHasFixedSize(true)
            addItemDecoration(MovieListItemDecoration(50, numberSpans(layoutManager)))
            adapter = movieAdapter

        }
    }

    private fun setMovieAdapterStateListener() {
        movieAdapter.addLoadStateListener { loadState ->
            progressBar?.isVisible = loadState.source.refresh is LoadState.Loading
            recyclerView?.isVisible = loadState.source.refresh is LoadState.NotLoading
        }

    }

    //!!!!!!
    /*  private fun updateMovieAdapter(movies: List<Movie>) {
          Log.d("MyLog", "FragmentMovieList_updateMovieAdapter")
          movieAdapter.setData(movies)
      }*/
    //!!!!!!

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
        findNavController().apply {
            navigate(R.id.action_fragmentMoviesList_to_fragmentMovieDetails, bundle)
        }
    }

    companion object {
        const val MOVIEID = "movieId"
    }

}
