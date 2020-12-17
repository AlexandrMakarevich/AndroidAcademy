package by.a_makarevich.androidacademyhw1

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.a_makarevich.androidacademyhw1.adapter.MovieAdapter
import by.a_makarevich.androidacademyhw1.adapter.OnClickListenerDetail
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class FragmentMoviesList : Fragment(), OnClickListenerDetail {

    private var recyclerView: RecyclerView? = null
    private val movieAdapter = MovieAdapter(this)
    private var movieList: List<Movie>? = null
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyLog", "FragmentMoviesList_onCreateView")
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        initViews(view)

        CoroutineScope(Dispatchers.Main).launch {
            job = CoroutineScope(Dispatchers.IO).launch {
                movieList = loadMovies(requireContext())
                movieAdapter.setData(movieList!!)
            }
        }

        val layoutManager = recyclerView?.layoutManager as GridLayoutManager
        recyclerView?.addItemDecoration(MovieListItemDecoration(50, numberSpans(layoutManager)))
        recyclerView.apply {
            this?.adapter = movieAdapter
        }
        return view
    }


    override fun onDestroyView() {
        clearCashViews()
        job?.cancel()

        super.onDestroyView()
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

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    private fun clearCashViews() {
        recyclerView = null
    }

    override fun onItemClick(movie: Movie) {
        //Log.d("MyLog", movie.overview)
        val bundle = Bundle()
        bundle.putParcelable(Movie::class.java.toString(), movie)
        findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMovieDetails, bundle)
    }
}
